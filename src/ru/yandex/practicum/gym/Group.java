package ru.yandex.practicum.gym;

public class Group {
    //название группы
    private final String title;
    //тип (взрослая или детская)
    final Age age;
    //длительность (в минутах)
    final int duration;

    public Group(String title, Age age, int duration) {
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }
}
