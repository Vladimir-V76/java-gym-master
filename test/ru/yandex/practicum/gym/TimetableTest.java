package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {
    Timetable timetable = new Timetable();

    @Test
    void shouldBe0IsEmptyTimetableForGetTrainingSessionsForDay() {
        int numberSession = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();
        Assertions.assertEquals(0, numberSession);
    }

    @Test
    void shouldBe0IsEmptyTimetableForGetTrainingSessionsForDayAndTime() {
        int numberSession = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14,0)).size();
        Assertions.assertEquals(0, numberSession);
    }

    @Test
    void shouldBe0IsNoTrainingAtTimeOfDayForGetTrainingSessionsForDayAndTime() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession trainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(trainingSession);

        int numberSession = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14,0)).size();
        Assertions.assertEquals(0, numberSession);
    }


    @Test
    void testGetTrainingSessionsForDaySingleSession() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        //Проверить, что за понедельник вернулось одно занятие
        int numberSessionOfMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        Assertions.assertEquals(1, numberSessionOfMonday);
        //Проверить, что за вторник не вернулось занятий
        int numberSessionOfTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();
        Assertions.assertEquals(0, numberSessionOfTuesday);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        int numberSessionOfMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        Assertions.assertEquals(1, numberSessionOfMonday);

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        int numberSessionOfThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size();
        Assertions.assertEquals(2, numberSessionOfThursday);
        List<TrainingSession> trainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(new TimeOfDay(13,0),trainingSessions.get(0).getTimeOfDay());
        Assertions.assertEquals(new TimeOfDay(20,0),trainingSessions.get(1).getTimeOfDay());

        // Проверить, что за вторник не вернулось занятий
        int numberSessionOfTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();
        Assertions.assertEquals(0, numberSessionOfTuesday);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        int numberSessionOfMondayFrom13_00 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13,0)).size();
        Assertions.assertEquals(1, numberSessionOfMondayFrom13_00);

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        int numberSessionOfMondayFrom14_00 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14,0)).size();
        Assertions.assertEquals(0, numberSessionOfMondayFrom14_00);
    }
    @Test
    void shouldBe0IsEmptyTimeTableForCountByCoaches () {
        List<CountOfTrainings> countOfTrainingsList = timetable.getCountByCoaches();
        Assertions.assertEquals(0, countOfTrainingsList.size());
    }

    @Test
    void shouldBe1IsSingleCoachInTimetableAnd2IsCountOfTrainingIsThisCoachForCountByCoaches () {
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession trainingSession1 = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession trainingSession2 = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        List<CountOfTrainings> countOfTrainingsList = timetable.getCountByCoaches();

        Assertions.assertEquals(1, countOfTrainingsList.size());
        Assertions.assertEquals(2, countOfTrainingsList.getFirst().getCount());
    }

    @Test
    void shouldBe3TrainingsIsFistCoach2IsTrainingSecondCoach1TrainingIsThirdCoachForCountByCoaches () {

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Николаев", "Сергей", "Васильевич");
        Coach coach3 = new Coach("Сергеев", "Василий", "Николаевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession trainingSession1 = new TrainingSession(groupAdult, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession trainingSession2 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        TrainingSession trainingSession3 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        TrainingSession trainingSession4 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession5 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession trainingSession6 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);
        timetable.addNewTrainingSession(trainingSession6);
        List<CountOfTrainings> countOfTrainingsList = timetable.getCountByCoaches();
        Assertions.assertEquals(3, countOfTrainingsList.getFirst().getCount());
        Assertions.assertEquals(2, countOfTrainingsList.get(1).getCount());
        Assertions.assertEquals(1, countOfTrainingsList.get(2).getCount());
    }
}
