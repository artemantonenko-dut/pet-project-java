package ua.edu.planday;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
public class ConsoleMenu {
    private final ScheduleManager scheduleManager;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public ConsoleMenu(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }
    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            switch (scanner.nextLine().trim()) {
                case "1" -> addItem();
                case "2" -> showItemsForDay();
                case "3" -> showItemsForWeek();
                case "4" -> updateItem();
                case "5" -> deleteItem();
                case "0" -> isRunning = false;
                default -> System.out.println("Оберіть пункт від 0 до 5.");
            }}
        System.out.println("До побачення!");}
    private void printMenu() {
        System.out.println("\n--- PlanDay ---");
        System.out.println("1 - Додати запис");
        System.out.println("2 - Показати розклад на день");
        System.out.println("3 - Показати розклад на тиждень");
        System.out.println("4 - Змінити запис");
        System.out.println("5 - Видалити запис");
        System.out.println("0 - Вийти");
        System.out.print("Ваш вибір: ");}
    private void addItem() {
        try {
            ScheduleItem item = readItemData();
            ScheduleItem savedItem = scheduleManager.addItem(item.getTitle(), item.getDate(), item.getStartTime(),
                    item.getDurationMinutes(), item.getCategory(), item.getNote());
            System.out.println("Запис додано. Його id: " + savedItem.getId());
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка: " + exception.getMessage());
        } catch (SQLException exception) {
            showDatabaseError(exception);
        }
    }
    private void showItemsForDay() {
        try {
            LocalDate date = readDate();
            printItems(scheduleManager.getItemsForDate(date));
        } catch (SQLException exception) {
            showDatabaseError(exception);
        }
    }
    private void showItemsForWeek() {
        try {
            LocalDate date = readDate();
            printItems(scheduleManager.getItemsForWeek(date));
        } catch (SQLException exception) {
            showDatabaseError(exception);
        }
    }
    private void updateItem() {
        try {
            long id = readLong("Введіть id запису: ");
            if (scheduleManager.getItemById(id) == null) {
                System.out.println("Запис із таким id не знайдено.");
                return;
            }
            System.out.println("Введіть нові дані запису:");
            ScheduleItem item = readItemData();
            boolean updated = scheduleManager.updateItem(id, item.getTitle(), item.getDate(), item.getStartTime(),
                    item.getDurationMinutes(), item.getCategory(), item.getNote());
            System.out.println(updated ? "Запис змінено." : "Не вдалося змінити запис.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка: " + exception.getMessage());
        } catch (SQLException exception) {
            showDatabaseError(exception);
        }
    }
    private void deleteItem() {
        try {
            long id = readLong("Введіть id запису для видалення: ");
            System.out.println(scheduleManager.deleteItem(id)
                    ? "Запис видалено."
                    : "Запис із таким id не знайдено.");
        } catch (SQLException exception) {
            showDatabaseError(exception);}}
    private ScheduleItem readItemData() {
        System.out.print("Назва: ");
        String title = scanner.nextLine();
        LocalDate date = readDate();
        LocalTime time = readTime();
        int duration = readPositiveInt("Тривалість у хвилинах: ");
        Category category = readCategory();
        System.out.print("Примітка (можна залишити порожньою): ");
        String note = scanner.nextLine();
        return new ScheduleItem(title, date, time, duration, category, note);
    }
    private LocalDate readDate() {
        while (true) {
            System.out.print("Дата (yyyy-MM-dd): ");
            try {
                return LocalDate.parse(scanner.nextLine().trim(), dateFormatter);
            } catch (DateTimeParseException exception) {
                System.out.println("Неправильна дата. Приклад: 2026-10-26.");
            }}}
    private LocalTime readTime() {
        while (true) {
            System.out.print("Час початку (HH:mm): ");
            try {
                return LocalTime.parse(scanner.nextLine().trim(), timeFormatter);
            } catch (DateTimeParseException exception) {
                System.out.println("Неправильний час. Приклад: 10:30.");
            }
        }}
    private int readPositiveInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Введіть ціле число, більше за 0.");}}
    private long readLong(String message) {
        while (true) { try {
                System.out.print(message);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Введіть ціле число.");}}}
    private Category readCategory() {
        Category[] categories = Category.values();
        System.out.println("Категорія:");
        for (int index = 0; index <categories.length; index++) {
            System.out.println((index + 1) + " - " +categories[index].getDisplayName());}
        while (true) {
            try {
                System.out.print("Ваш вибір: ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= categories.length) {
                    return categories[choice - 1];
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Оберіть номер категорії зі списку.");
        }}
    private void printItems(List<ScheduleItem> items) {
        if (items.isEmpty()) {
            System.out.println("Записів не знайдено.");
            return;
        }
        System.out.println("\nID | Дата | Час | Назва | Категорія");
        for (ScheduleItem item : items) {
            System.out.printf("%d | %s | %s-%s | %s | %s%n",
                    item.getId(), item.getDate(), item.getStartTime(), item.getEndTime(),
                    item.getTitle(), item.getCategory().getDisplayName());
        }}
    private void showDatabaseError(SQLException exception) {
        System.out.println("Помилка бази даних: " + exception.getMessage());
    }}