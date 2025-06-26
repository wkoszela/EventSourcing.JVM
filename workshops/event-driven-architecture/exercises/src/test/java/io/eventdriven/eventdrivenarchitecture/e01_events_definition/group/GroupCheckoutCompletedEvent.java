package io.eventdriven.eventdrivenarchitecture.e01_events_definition.group;

import java.time.Instant;
import java.util.UUID;

public class GroupCheckoutCompletedEvent {

    UUID groupId;

    UUID[] groupsIdCheckOut;

    Instant completedTime;

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public UUID[] getGroupsIdCheckOut() {
        return groupsIdCheckOut;
    }

    public void setGroupsIdCheckOut(UUID[] groupsIdCheckOut) {
        this.groupsIdCheckOut = groupsIdCheckOut;
    }

    public Instant getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Instant completedTime) {
        this.completedTime = completedTime;
    }
}
