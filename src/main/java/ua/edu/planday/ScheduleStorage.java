package ua.edu.planday;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleStorage {
    ScheduleItem save(ScheduleItem item) throws SQLException;
    List<ScheduleItem> findByDate(LocalDate date) throws SQLException;
    ScheduleItem findById(long id) throws SQLException;
    boolean update(long id, ScheduleItem item) throws SQLException;
    boolean delete(long id) throws SQLException;
}
