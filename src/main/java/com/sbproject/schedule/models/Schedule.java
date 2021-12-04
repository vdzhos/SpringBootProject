package com.sbproject.schedule.models;

import javax.swing.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Schedule {

    private List<Day> days;
    private static final int NUMBER_OF_DAYS = 7;

    public Schedule(List<Lesson> lessons) {
        buildDays(lessons);
    }

    private void buildDays(List<Lesson> lessons) {
        List<Lesson>[] dayLessons = new List[NUMBER_OF_DAYS];
        Schedule.init(dayLessons);
        lessons.forEach(l -> dayLessons[l.getDayOfWeek().ordinal()].add(l));
        days = Arrays.stream(dayLessons).map(Day::new).collect(Collectors.toList());
        for (int i = 0; i <NUMBER_OF_DAYS; i++){
            days.get(i).setDay(DayOfWeek.of(i+1));
        }
    }

//    public Day getDay(DayOfWeek day) {
//        return days.get(day.ordinal());
//    }

    public List<Day> getDays() {
        days.removeIf(d -> d.getLessonsNumber() == 0);
        return days;
    }

    public static void init(List<Lesson>[] lessons) {
        for (int i = 0; i < lessons.length; i++) {
            lessons[i] = new ArrayList<>();
        }
    }
}
