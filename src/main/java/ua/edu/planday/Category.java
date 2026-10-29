package ua.edu.planday;

public enum Category {
    STUDY("Навчання"),
    WORK("Робота"),
    SPORT("Спорт"),
    REST("Відпочинок"),
    OTHER("Інше");
    private final String displayName;
    Category(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }}