package bq_npc_integration.tasks;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.utils.ParticipantInfo;
import bq_npc_integration.client.gui.tasks.PanelTaskFaction;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.factory.FactoryTaskFaction;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.data.PlayerData;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.*;

public class TaskNpcFaction implements ITaskTickable
{
	private final Set<UUID> completeUsers = new TreeSet<>();
	private final HashMap<UUID, Integer> userProgress = new HashMap<>();
	
	public int factionID = 0;
	public int target = 10;
	public PointOperation operation = PointOperation.MORE_OR_EQUAL;
	
	@Override
	public ResourceLocation getFactoryID()
	{
		return FactoryTaskFaction.INSTANCE.getRegistryName();
	}
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".task.faction";
	}
	
	@Override
	public boolean isComplete(UUID uuid)
	{
		return completeUsers.contains(uuid);
	}
	
	@Override
	public void setComplete(UUID uuid)
	{
		completeUsers.add(uuid);
	}

	@Override
	public void resetUser(@Nullable UUID uuid)
	{
	    if(uuid == null)
        {
            completeUsers.clear();
            userProgress.clear();
        } else
        {
            completeUsers.remove(uuid);
            userProgress.remove(uuid);
        }
	}
	
	@Override
	public void tickTask(DBEntry<IQuest> quest, ParticipantInfo pInfo)
	{
		if(pInfo.PLAYER.ticksExisted%60 != 0 || QuestingAPI.getAPI(ApiReference.SETTINGS).getProperty(NativeProps.EDIT_MODE) || pInfo.PLAYER.getServer() == null)
		{
			return;
		}
		
		detect(pInfo, quest);
		
		PlayerData pData = PlayerDataController.instance.getDataFromUsername(pInfo.PLAYER.getServer(), pInfo.PLAYER.getGameProfile().getName());
		
		if(pData == null || !pData.factionData.factionData.containsKey(factionID))
		{
			return;
		}
		
		int cur = pData.factionData.getFactionPoints(pInfo.PLAYER, factionID);
		
		if(getUsersProgress(pInfo.UUID) != cur)
		{
			setUserProgress(pInfo.UUID, pData.factionData.getFactionPoints(pInfo.PLAYER, factionID));
		}
	}
	
	@Override
	public void detect(ParticipantInfo pInfo, DBEntry<IQuest> quest)
	{
		if(isComplete(pInfo.UUID) || pInfo.PLAYER.getServer() == null)
		{
			return;
		}
		
		PlayerData pData = PlayerDataController.instance.getDataFromUsername(pInfo.PLAYER.getServer(), pInfo.PLAYER.getGameProfile().getName());
		
		if(pData == null || !pData.factionData.factionData.containsKey(factionID))
		{
			return;
		}
		
		int points = pData.factionData.getFactionPoints(pInfo.PLAYER, factionID);
		
		boolean flag = operation.checkValues(points, target);
		
		if(flag)
		{
			setComplete(pInfo.UUID);
			pInfo.markDirty(Collections.singletonList(quest.getID()));
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound json)
	{
		factionID = !json.hasKey("factionID", 99) ? 0 : json.getInteger("factionID");
		target = !json.hasKey("target", 99) ? 1 : json.getInteger("target");
		operation = PointOperation.valueOf(!json.hasKey("operation", 8) ? "MORE_OR_EQUAL" : json.getString("operation"));
	}
	
	@Override
    public void readProgressFromNBT(NBTTagCompound nbt, boolean merge)
	{
		if(!merge)
        {
            completeUsers.clear();
            userProgress.clear();
        }
		
		NBTTagList cList = nbt.getTagList("completeUsers", 8);
		for(int i = 0; i < cList.tagCount(); i++)
		{
			try
			{
				completeUsers.add(UUID.fromString(cList.getStringTagAt(i)));
			} catch(Exception e)
			{
				BQ_NPCs.logger.log(Level.ERROR, "Unable to load UUID for task", e);
			}
		}
		
		NBTTagList pList = nbt.getTagList("userProgress", 10);
		for(int n = 0; n < pList.tagCount(); n++)
		{
			try
			{
                NBTTagCompound pTag = pList.getCompoundTagAt(n);
                UUID uuid = UUID.fromString(pTag.getString("uuid"));
                userProgress.put(uuid, pTag.getInteger("value"));
			} catch(Exception e)
			{
				BQ_NPCs.logger.log(Level.ERROR, "Unable to load user progress for task", e);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound json)
	{
		json.setInteger("factionID", factionID);
		json.setInteger("target", target);
		json.setString("operation", operation.name());
		
		return json;
	}
	
	@Override
	public NBTTagCompound writeProgressToNBT(NBTTagCompound nbt, @Nullable List<UUID> users)
	{
		NBTTagList jArray = new NBTTagList();
		NBTTagList progArray = new NBTTagList();
		
		if(users != null)
        {
            users.forEach((uuid) -> {
                if(completeUsers.contains(uuid)) jArray.appendTag(new NBTTagString(uuid.toString()));
                
                Integer data = userProgress.get(uuid);
                if(data != null)
                {
                    NBTTagCompound pJson = new NBTTagCompound();
                    pJson.setString("uuid", uuid.toString());
                    pJson.setInteger("value", data);
                    progArray.appendTag(pJson);
                }
            });
        } else
        {
            completeUsers.forEach((uuid) -> jArray.appendTag(new NBTTagString(uuid.toString())));
            
            userProgress.forEach((uuid, data) -> {
                NBTTagCompound pJson = new NBTTagCompound();
			    pJson.setString("uuid", uuid.toString());
                pJson.setInteger("value", data);
                progArray.appendTag(pJson);
            });
        }
		
		nbt.setTag("completeUsers", jArray);
		nbt.setTag("userProgress", progArray);
		
		return nbt;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public IGuiPanel getTaskGui(IGuiRect rect, DBEntry<IQuest> quest)
	{
		return new PanelTaskFaction(rect, this);
	}
	
	public enum PointOperation
	{
		EQUAL("="),
		LESS_THAN("<"),
		MORE_THAN(">"),
		LESS_OR_EQUAL("<="),
		MORE_OR_EQUAL(">="),
		NOT("=/=");
		
		final String text;
		
		PointOperation(String text)
		{
			this.text = text;
		}
		
		public String GetText()
		{
			return text;
		}
		
		public boolean checkValues(int n1, int n2)
		{
			switch(this)
			{
				case EQUAL:
					return n1 == n2;
				case LESS_THAN:
					return n1 < n2;
				case MORE_THAN:
					return n1 > n2;
				case LESS_OR_EQUAL:
					return n1 <= n2;
				case MORE_OR_EQUAL:
					return n1 >= n2;
				case NOT:
					return n1 != n2;
			}
			
			return false;
		}
	}

	@Override
    @SideOnly(Side.CLIENT)
	public GuiScreen getTaskEditor(GuiScreen parent, DBEntry<IQuest> quest)
	{
		return null;
	}
 
	private void setUserProgress(UUID uuid, Integer progress)
	{
		userProgress.put(uuid, progress);
	}
 
	public Integer getUsersProgress(UUID... users)
	{
		int i = 0;
		
		for(UUID uuid : users)
		{
			Integer n = userProgress.get(uuid);
			i += n == null? 0 : n;
		}
		
		return i;
	}
}
