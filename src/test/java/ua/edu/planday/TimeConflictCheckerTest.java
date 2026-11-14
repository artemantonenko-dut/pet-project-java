package ua.edu.planday;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TimeConflictCheckerTest {
    private final ConflictChecker checker = new TimeConflictChecker();
    @Test
    void doesNotFindConflictWhenItemsTouch() {
        ScheduleItem oldItem = item(1, 10, 0, 60);
        ScheduleItem newItem = item(0, 11, 0, 30);
        assertFalse(checker.hasConflict(newItem, List.of(oldItem), 0));}
    @Test
    void findsConflictWhenItemsOverlap() {
        ScheduleItem oldItem = item(1, 10, 0, 60);
        ScheduleItem newItem = item(0, 10, 30, 30);

        assertTrue(checker.hasConflict(newItem, List.of(oldItem), 0));}
    private ScheduleItem item(long id, int hour, int minute, int duration) {
        return new ScheduleItem(id, "Task", LocalDate.of(2026, 11, 13),
                LocalTime.of(hour, minute), duration, Category.STUDY, "");
}}