package ua.edu.planday;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DatabaseStorage implements ScheduleStorage {
    private final ScheduleItemDao itemDao;

    public DatabaseStorage(ScheduleItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public ScheduleItem save(ScheduleItem item) throws SQLException {
        return itemDao.save(item);
    }

    public List<ScheduleItem> findByDate(LocalDate date) throws SQLException {
        return itemDao.findByDate(date);
    }

    public ScheduleItem findById(long id) throws SQLException {
        return itemDao.findById(id);
    }

    public boolean update(long id, ScheduleItem item) throws SQLException {
        return itemDao.update(id, item);
    }

    public boolean delete(long id) throws SQLException {
        return itemDao.delete(id);
    }
}
