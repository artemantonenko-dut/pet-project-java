package ua.edu.planday;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleManager {
    private final ScheduleItemDao itemDao;
    private final ConflictChecker conflictChecker;

    public ScheduleManager(ScheduleItemDao itemDao) {
        this(itemDao, new TimeConflictChecker());
    }

    public ScheduleManager(ScheduleItemDao itemDao, ConflictChecker conflictChecker) {
        this.itemDao = itemDao;
        this.conflictChecker = conflictChecker;
    }

    public ScheduleItem addItem(String title, LocalDate date, LocalTime startTime, int duration,
                                Category category, String note) throws SQLException {
        validate(title, duration);
        ScheduleItem item = new ScheduleItemBuilder()
                .title(title.trim())
                .date(date)
                .startTime(startTime)
                .durationMinutes(duration)
                .category(category)
                .note(note)
                .build();
        if (hasTimeConflict(item, 0)) {
            throw new IllegalArgumentException("На цей час уже є інший запис.");
        }
        return itemDao.save(item);
    }

    public boolean updateItem(long id, String title, LocalDate date, LocalTime startTime, int duration,
                              Category category, String note) throws SQLException {
        if (itemDao.findById(id) == null) {
            return false;
        }
        validate(title, duration);
        ScheduleItem item = new ScheduleItemBuilder()
                .title(title.trim())
                .date(date)
                .startTime(startTime)
                .durationMinutes(duration)
                .category(category)
                .note(note)
                .build();
        if (hasTimeConflict(item, id)) {
            throw new IllegalArgumentException("На цей час уже є інший запис.");
        }
        return itemDao.update(id, item);
    }

    public List<ScheduleItem> getItemsForDate(LocalDate date) throws SQLException {
        return itemDao.findByDate(date);
    }

    public List<ScheduleItem> getItemsForWeek(LocalDate anyDayOfWeek) throws SQLException {
        LocalDate monday = anyDayOfWeek.minusDays(anyDayOfWeek.getDayOfWeek().getValue() - 1L);
        List<ScheduleItem> result = new ArrayList<>();
        for (int day = 0; day < 7; day++) {
            result.addAll(itemDao.findByDate(monday.plusDays(day)));
        }
        return result;
    }

    public boolean deleteItem(long id) throws SQLException {
        return itemDao.delete(id);
    }

    public ScheduleItem getItemById(long id) throws SQLException {
        return itemDao.findById(id);
    }

    private boolean hasTimeConflict(ScheduleItem newItem, long ignoredId) throws SQLException {
        return conflictChecker.hasConflict(newItem, itemDao.findByDate(newItem.getDate()), ignoredId);
    }

    private void validate(String title, int duration) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Назва не може бути порожньою.");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Тривалість має бути більшою за 0.");
        }
    }
}
