package com.sbproject.schedule.xlsx;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Room;
import com.sbproject.schedule.models.SubjectType;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.DayOfWeek;

public class ScheduleAnalyzerStd extends ScheduleAnalyzer {



    public ScheduleAnalyzerStd(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    protected void init() {
        super.init();
        STRING_DAY_OF_WEEK_MAP.put("понеділок",DayOfWeek.MONDAY);
        STRING_DAY_OF_WEEK_MAP.put("вівторок",DayOfWeek.TUESDAY);
        STRING_DAY_OF_WEEK_MAP.put("середа",DayOfWeek.WEDNESDAY);
        STRING_DAY_OF_WEEK_MAP.put("четвер",DayOfWeek.THURSDAY);
        STRING_DAY_OF_WEEK_MAP.put("п`ятниця",DayOfWeek.FRIDAY);
        STRING_DAY_OF_WEEK_MAP.put("субота",DayOfWeek.SATURDAY);

        FORMAT = ScheduleFormat.STD_F;

        LECTURE_STRING = "лекція";
        REMOTELY = "дистанційно";
        COLS_NUMBER = 6;

        DAY_COL = 0;
        TIME_COL = 1;
        SUBJECT_TEACHER_COL = 2;
        GROUP_COL = 3;
        WEEKS_COL = 4;
        ROOM_COL = 5;

        SHEET_START = 0;
        ROW_START = 10;
        COL_START = 0;
    }

    @Override
    protected void processRow(Row row) {
        DayOfWeek day = tryGetDay(row);
        Lesson.Time time = tryGetTime(row);
        SubjectTeacher st = SubjectTeacher.getFrom(row);
        String subject = st.getSubject();
        String teacherFirstName = st.getFirstName();
        String teacherLastName = st.getLastName();
        String teacherSurname = st.getSurname();

        SubjectType group = getGroup(row);
        String weeks = getWeeks(row);
        Room room = getRoom(row);

        lessons.add(new LessonInfo(day,time,subject,teacherFirstName,teacherLastName,teacherSurname,group,weeks,room));
        System.out.println(new LessonInfo(day,time,subject,teacherFirstName,teacherLastName,teacherSurname,group,weeks,room));
    }



    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(DayOfWeek.TUESDAY.ordinal());
    }

}