package ua.edu.planday;

import java.util.List;

public class TimeConflictChecker implements ConflictChecker {
    public boolean hasConflict(ScheduleItem newItem, List<ScheduleItem> items, long ignoredId) {
        for (ScheduleItem oldItem : items) {
            boolean sameItem = oldItem.getId() == ignoredId;
            boolean overlap = newItem.getStartTime().isBefore(oldItem.getEndTime())
                    && oldItem.getStartTime().isBefore(newItem.getEndTime());
            if (!sameItem && overlap) {
                return true;
            }
        }
        return false;
    }
}
