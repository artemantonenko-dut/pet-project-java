package ua.edu.planday;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class ScheduleItemDao {
    private final Connection connection;
    public ScheduleItemDao(Connection connection) {
        this.connection = connection;
    }
    public ScheduleItem save(ScheduleItem item) throws SQLException {
        String sql = "INSERT INTO schedule_items "
                + "(title, planned_date, start_time, duration_minutes, category, note) "
                + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, item);
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                return new ScheduleItem(result.getLong("id"), item.getTitle(), item.getDate(),
                        item.getStartTime(), item.getDurationMinutes(), item.getCategory(), item.getNote());
            }}}
    public List<ScheduleItem> findByDate(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM schedule_items WHERE planned_date = ? ORDER BY start_time";
        List<ScheduleItem> items = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(date));
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    items.add(mapItem(result));
                }
            }
        }
        return items;}
    public ScheduleItem findById(long id) throws SQLException {
        String sql = "SELECT * FROM schedule_items WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapItem(result);}
                return null;
            }}}
    public boolean update(long id, ScheduleItem item) throws SQLException {
        String sql = "UPDATE schedule_items SET title = ?, planned_date = ?, start_time = ?, "
                + "duration_minutes = ?, category = ?, note = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, item);
            statement.setLong(7, id);
            return statement.executeUpdate() == 1;
        }
    }
    public boolean delete(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM schedule_items WHERE id = ?")) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        }}
    private void fillStatement(PreparedStatement statement, ScheduleItem item) throws SQLException {
        statement.setString(1, item.getTitle());
        statement.setDate(2, Date.valueOf(item.getDate()));
        statement.setTime(3, Time.valueOf(item.getStartTime()));
        statement.setInt(4, item.getDurationMinutes());
        statement.setString(5, item.getCategory().name());
        if (item.getNote() == null || item.getNote().isBlank()) {
            statement.setNull(6, Types.VARCHAR);
        } else { statement.setString(6, item.getNote());
        }}
    private ScheduleItem mapItem(ResultSet result) throws SQLException {
        return new ScheduleItem(
                result.getLong("id"),
                result.getString("title"),
                result.getDate("planned_date").toLocalDate(),
                result.getTime("start_time").toLocalTime(),
                result.getInt("duration_minutes"),
                Category.valueOf(result.getString("category")),
                result.getString("note")
        );}}