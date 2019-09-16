package bq_npc_integration.tasks;

import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.utils.ParticipantInfo;

public interface ITaskTickable extends ITask
{
    void tickTask(DBEntry<IQuest> quest, ParticipantInfo pInfo);
}
