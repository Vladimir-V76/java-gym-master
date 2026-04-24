package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, TrainingSession> newTrainingSession;
        newTrainingSession = timetable.getOrDefault(trainingSession.getDayOfWeek(), new TreeMap<>());
        newTrainingSession.put(trainingSession.getTimeOfDay(), trainingSession);
        timetable.put(trainingSession.getDayOfWeek(), newTrainingSession);
    }

    public TreeMap<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, TrainingSession> sessionOnDate = timetable.getOrDefault(dayOfWeek, new TreeMap<>());
        if (sessionOnDate.isEmpty() || !sessionOnDate.containsKey(timeOfDay)) {
            Group group = new Group("", Age.CHILD, 0);
            Coach coach = new Coach("", "", "");
            return new TrainingSession(group, coach, dayOfWeek, timeOfDay);
        } else {
            return sessionOnDate.get(timeOfDay);
        }
    }

    public List<CountOfTrainings> getCountByCoaches() {
        if (timetable.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Coach, Integer> sessionsByCoach = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, TrainingSession> trainingSessionByDay = timetable.getOrDefault(day, new TreeMap<>());
            if (!trainingSessionByDay.isEmpty()) {
                for (TrainingSession t : trainingSessionByDay.values()) {
                    Coach coach = t.getCoach();
                    int numberOfSession = sessionsByCoach.getOrDefault(coach, 0);
                    numberOfSession++;
                    sessionsByCoach.put(coach, numberOfSession);
                }
            }
        }
        List<CountOfTrainings> countOfTrainings = new ArrayList<>();
        for (Coach coach : sessionsByCoach.keySet()) {
            countOfTrainings.add(new CountOfTrainings(coach.getSurname(), coach.getName(),
                    coach.getMiddleName(), sessionsByCoach.get(coach)));
        }
        Collections.sort(countOfTrainings);
        Collections.reverse(countOfTrainings);
        return countOfTrainings;
    }
}
