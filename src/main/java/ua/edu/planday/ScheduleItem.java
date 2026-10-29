package ua.edu.planday;
import java.time.LocalDate;
import java.time.LocalTime;
public class ScheduleItem {
    private long id;
    private String title;
    private LocalDate date;
    private LocalTime startTime;
    private int durationMinutes;
    private Category category;
    private String note;
    public ScheduleItem(String title, LocalDate date, LocalTime startTime,
                        int durationMinutes, Category category, String note) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.category = category;
        this.note = note;}
    public ScheduleItem(long id, String title, LocalDate date, LocalTime startTime,
                        int durationMinutes, Category category, String note) {
        this(title, date, startTime, durationMinutes, category, note);
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public Category getCategory() {
        return category;
    }
    public String getNote() {
        return note;
    }
    public LocalTime getEndTime() {
        return startTime.plusMinutes(durationMinutes);
    }}