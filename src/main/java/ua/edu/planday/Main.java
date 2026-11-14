package ua.edu.planday;
import java.sql.Connection;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            ScheduleItemDao itemDao = new ScheduleItemDao(connection);
            ScheduleManager scheduleManager = new ScheduleManager(itemDao);
            new ConsoleMenu(scheduleManager).start();
        } catch (SQLException exception) {
            System.out.println("Не вдалося підключитися до PostgreSQL.");
            System.out.println("Перевірте базу plan_day і дані в DatabaseConnection.java.");
        }}}