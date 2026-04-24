package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, TrainingSession> newTrainingSession;
        newTrainingSession = timetable.getOrDefault(trainingSession.getDayOfWeek(), new TreeMap<>());
        newTrainingSession.put(trainingSession.getTimeOfDay(), trainingSession);
        timetable.put(trainingSession.getDayOfWeek(), newTrainingSession);
        System.out.println("Тренировка " + trainingSession + " успешно внесена в расписание на "
                + trainingSession.getDayOfWeek());
    }

    //public /* непонятно, что возвращать */ getTrainingSessionsForDay(DayOfWeek dayOfWeek, Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable) {
    public TreeMap<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, null);
    }

    //public /* непонятно, что возвращать */ getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay, Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable) {
    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public Map<Coach, Integer> getCountByCoaches () {
        if (timetable.isEmpty()) { return null; }
        Map<Coach, Integer> sessionByCoach = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay,TrainingSession> trainingSessionByDay = timetable.getOrDefault(day, null);
            if (!trainingSessionByDay.isEmpty()) {
                for (TrainingSession t : trainingSessionByDay.values()) {
                    Coach coach = t.getCoach();
                    int numberOfSession = sessionByCoach.getOrDefault(coach, 0);
                    numberOfSession++;
                    sessionByCoach.put(coach, numberOfSession);
                }
            }
        }
        return sessionByCoach;
    }
}
