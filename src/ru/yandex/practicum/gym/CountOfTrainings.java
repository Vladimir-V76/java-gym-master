package ru.yandex.practicum.gym;

public class CountOfTrainings extends Coach implements Comparable<CountOfTrainings> {
    private final int count;

    public CountOfTrainings(String surname, String name, String middleName, int count) {
        super(surname, name, middleName);
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    @Override
    public int compareTo(CountOfTrainings o) {
        return this.count - o.count;
    }
}
