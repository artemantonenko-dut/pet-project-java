package ua.edu.planday;
import java.time.LocalDate;
import java.time.LocalTime;
public class ScheduleItemBuilder {
    private String title;
    private LocalDate date;
    private LocalTime startTime;
    private int durationMinutes;
    private Category category;
    private String note;
    public ScheduleItemBuilder title(String title) {
        this.title = title;
        return this;}
    public ScheduleItemBuilder date(LocalDate date) {
        this.date = date;
        return this;}
    public ScheduleItemBuilder startTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }
    public ScheduleItemBuilder durationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public ScheduleItemBuilder category(Category category) {
        this.category = category;
        return this;
} }
    public ScheduleItemBuilder note(String note) {
        this.note = note;
        return this;
}}
    public ScheduleItem build() {
        return new ScheduleItem(title, date, startTime, durationMinutes, category, note);
}}