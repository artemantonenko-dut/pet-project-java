package ua.edu.planday;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScheduleManagerTest {
    @Test
    void rejectsEmptyTitle() {
        ScheduleManager manager = manager();

        assertThrows(IllegalArgumentException.class, () -> manager.addItem(" ",
                LocalDate.of(2026, 11, 14), LocalTime.of(10, 0),
                60, Category.STUDY, ""));
    }

    @Test
    void rejectsZeroDuration() {
        ScheduleManager manager = manager();

        assertThrows(IllegalArgumentException.class, () -> manager.addItem("Java",
                LocalDate.of(2026, 11, 14), LocalTime.of(10, 0),
                0, Category.STUDY, ""));
    }

    private ScheduleManager manager() {
        return new ScheduleManager((ScheduleStorage) null, new TimeConflictChecker());
    }
}
