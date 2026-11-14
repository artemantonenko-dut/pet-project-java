package ua.edu.planday;

import java.util.List;

public interface ConflictChecker {
    boolean hasConflict(ScheduleItem newItem, List<ScheduleItem> items, long ignoredId);
}
