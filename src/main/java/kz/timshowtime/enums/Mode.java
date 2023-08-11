package kz.timshowtime.enums;

public enum Mode {
    DEFAULT("Стандартный"),
    TRUMPSLESS("Безкозырка"),
    MISERE("Мизер"),
    DARK("Темная"),
    GOLD("Золотая");

    private final String title;

    Mode(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
