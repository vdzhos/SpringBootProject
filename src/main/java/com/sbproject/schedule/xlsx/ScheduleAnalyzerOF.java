package com.sbproject.schedule.xlsx;

import com.sbproject.schedule.exceptions.schedule.ScheduleException;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Room;
import com.sbproject.schedule.models.SubjectType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.DayOfWeek;

public class ScheduleAnalyzerOF extends ScheduleAnalyzer {

    public ScheduleAnalyzerOF(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    protected void init() {
        super.init();
        STRING_DAY_OF_WEEK_MAP.put(DayOfWeek.MONDAY.toString().toLowerCase(), DayOfWeek.MONDAY);
        STRING_DAY_OF_WEEK_MAP.put(DayOfWeek.TUESDAY.toString().toLowerCase(),DayOfWeek.TUESDAY);
        STRING_DAY_OF_WEEK_MAP.put(DayOfWeek.WEDNESDAY.toString().toLowerCase(),DayOfWeek.WEDNESDAY);
        STRING_DAY_OF_WEEK_MAP.put(DayOfWeek.THURSDAY.toString().toLowerCase(),DayOfWeek.THURSDAY);
        STRING_DAY_OF_WEEK_MAP.put(DayOfWeek.FRIDAY.toString().toLowerCase(),DayOfWeek.FRIDAY);
        STRING_DAY_OF_WEEK_MAP.put(DayOfWeek.SATURDAY.toString().toLowerCase(),DayOfWeek.SATURDAY);
        STRING_DAY_OF_WEEK_MAP.put(DayOfWeek.SUNDAY.toString().toLowerCase(),DayOfWeek.SUNDAY);

        LECTURE_STRING = "lecture";
        REMOTELY = "REMOTELY";
        COLS_NUMBER = 7;

        FORMAT = ScheduleFormat.OF;
        DAY_COL = 0;
        TIME_COL = 1;
        SUBJECT_TEACHER_COL = 2;
        TEACHER_COL = 3;
        GROUP_COL = 4;
        WEEKS_COL = 5;
        ROOM_COL = 6;

        SHEET_START = 0;
        ROW_START = 1;
        COL_START = 0;
    }

    @Override
    protected void processRow(Row row) {
        DayOfWeek day = tryGetDay(row);
        Lesson.Time time = tryGetTime(row);

        String subject = getSubject(row);
        String[] teacherName = getTeacherName(row);
        String teacherFirstName = teacherName[1];
        String teacherLastName = teacherName[2];
        String teacherSurname = teacherName[0];

        SubjectType group = getGroup(row);
        String weeks = getWeeks(row);
        Room room = getRoom(row);

        lessons.add(new LessonInfo(day,time,subject,teacherFirstName,teacherLastName,teacherSurname,group,weeks,room));
        System.out.println(new LessonInfo(day,time,subject,teacherFirstName,teacherLastName,teacherSurname,group,weeks,room));
    }

    private String[] getTeacherName(Row row) {
        String name = row.getCell(TEACHER_COL).getStringCellValue();
        String[] n = name.split("\\s+");
        if (n.length < 3){
            throw new ScheduleException("Incorrect name: length < 3: "+name);
        }
        return n;
    }

    private String getSubject(Row row) {
        return processor.processName(row.getCell(SUBJECT_TEACHER_COL).getStringCellValue());
    }

    public static void main(String[] args) throws FileNotFoundException {
//        String s = "Hello";
//        System.out.println(s.toString());
//
//        Object o = s;
//        System.out.println(o.toString());

        System.out.println(DayOfWeek.MONDAY.toString());
        ScheduleAnalyzer analyzer = new ScheduleAnalyzerStd(new FileInputStream(new File("src/main/resources/download/Schedule_exampleOF.xlsx")));
        analyzer.analyze();
        System.out.println(analyzer.getLessons());
    }
}
