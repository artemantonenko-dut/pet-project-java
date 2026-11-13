package ua.edu.planday;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleItemBuilderTest {
    @Test
    void buildsScheduleItemWithEnteredValues() {
        ScheduleItem item = new ScheduleItemBuilder()
                .title("Java practice")
                .date(LocalDate.of(2026, 11, 13))
                .startTime(LocalTime.of(10, 0))
                .durationMinutes(90)
                .category(Category.STUDY)
                .note("homework")
                .build();

        assertEquals("Java practice", item.getTitle());
        assertEquals(LocalDate.of(2026, 11, 13), item.getDate());
        assertEquals(LocalTime.of(10, 0), item.getStartTime());
        assertEquals(90, item.getDurationMinutes());
        assertEquals(Category.STUDY, item.getCategory());
        assertEquals("homework", item.getNote());
    }
}
