package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, ArrayList<TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        ArrayList<TrainingSession> newTrainingSession;
        newTrainingSession = timetable.getOrDefault(trainingSession.getDayOfWeek(), new ArrayList<>());
        newTrainingSession.add(trainingSession);
        Collections.sort(newTrainingSession);
        timetable.put(trainingSession.getDayOfWeek(), newTrainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new ArrayList<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        List<TrainingSession> resultList = new ArrayList<>();
        List<TrainingSession> sessionOnDate = timetable.getOrDefault(dayOfWeek, new ArrayList<>());
        if (!sessionOnDate.isEmpty()) {
            Group group = new Group("", Age.CHILD, 0);
            Coach coach = new Coach("", "", "");
            TrainingSession searchTraining =  new TrainingSession(group, coach, dayOfWeek, timeOfDay);
            int itemSearchIndex = 0;
            while (itemSearchIndex >= 0) {
                itemSearchIndex = Collections.binarySearch(sessionOnDate, searchTraining);
                if (itemSearchIndex >= 0) {
                    resultList.add(sessionOnDate.get(itemSearchIndex));
                    sessionOnDate.remove(itemSearchIndex);
                }
            }
            Collections.sort(resultList);
        }
        return resultList;
    }

    public List<CountOfTrainings> getCountByCoaches() {
        if (timetable.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Coach, Integer> sessionsByCoach = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            List<TrainingSession> trainingSessionByDay = timetable.getOrDefault(day, new ArrayList<>());
            if (!trainingSessionByDay.isEmpty()) {
                for (TrainingSession t : trainingSessionByDay) {
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
