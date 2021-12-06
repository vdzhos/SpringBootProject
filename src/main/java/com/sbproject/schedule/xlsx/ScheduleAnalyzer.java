package com.sbproject.schedule.xlsx;

import com.sbproject.schedule.exceptions.schedule.ScheduleException;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Room;
import com.sbproject.schedule.models.SubjectType;
import com.sbproject.schedule.utils.Utils;
import com.sbproject.schedule.utils.UtilsImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ScheduleAnalyzer {


    protected static String LECTURE_STRING = "lecture";

    protected enum ScheduleFormat {STD_F, OF}

    public static class LessonInfo {
        DayOfWeek day;
        Lesson.Time time;
        String subject;
        String teacherFirstName;
        String teacherLastName;
        String teacherSurname;
        SubjectType group;
        String weeks;
        Room room;

        public LessonInfo(DayOfWeek day,
                          Lesson.Time time,
                          String subject,
                          String teacherFirstName,
                          String teacherLastName,
                          String teacherSurname,
                          SubjectType group,
                          String weeks,
                          Room room) {
            this.day = day;
            this.time = time;
            this.subject = subject;
            this.teacherFirstName = teacherFirstName;
            this.teacherLastName = teacherLastName;
            this.teacherSurname = teacherSurname;
            this.group = group;
            this.weeks = weeks;
            this.room = room;
        }

        public DayOfWeek getDay() {
            return day;
        }

        public Lesson.Time getTime() {
            return time;
        }

        public String getSubject() {
            return subject;
        }

        public String getTeacherFirstName() {
            return teacherFirstName;
        }

        public String getTeacherLastName() {
            return teacherLastName;
        }

        public String getTeacherSurname() {
            return teacherSurname;
        }

        public SubjectType getGroup() {
            return group;
        }

        public String getWeeks() {
            return weeks;
        }

        public Room getRoom() {
            return room;
        }

        @Override
        public String toString() {
            return "LessonInfo{" +
                    "day=" + day +
                    ", time=" + time +
                    ", subject='" + subject + '\'' +
                    ", teacherFirstName='" + teacherFirstName + '\'' +
                    ", teacherLastName='" + teacherLastName + '\'' +
                    ", teacherSurname='" + teacherSurname + '\'' +
                    ", group=" + group +
                    ", weeks='" + weeks + '\'' +
                    ", room=" + room +
                    '}';
        }
    }

    protected Utils processor;

    protected static ScheduleFormat FORMAT;

    protected static String REMOTELY;
    protected static int COLS_NUMBER;

    protected static int DAY_COL;
    protected static int TIME_COL;
    protected static int SUBJECT_TEACHER_COL;
    protected static int TEACHER_COL;
    protected static int GROUP_COL;
    protected static int WEEKS_COL;
    protected static int ROOM_COL;

    protected static int SHEET_START;
    protected static int ROW_START;
    protected static int COL_START;

    protected DayOfWeek dayOfWeek;
    protected Lesson.Time time;

    protected InputStream inputStream;

    protected List<LessonInfo> lessons;

    protected static final Map<String, DayOfWeek> STRING_DAY_OF_WEEK_MAP = new HashMap<>();
    protected static final Map<String, Lesson.Time> STRING_TIME_MAP = new HashMap<>();


    public ScheduleAnalyzer(InputStream inputStream) {
        this.inputStream = inputStream;
        processor = new UtilsImpl();
        lessons = new ArrayList<>();

        dayOfWeek = null;
        time = null;
        init();
    }

    protected void init() {
        STRING_TIME_MAP.put(Lesson.Time.TIME1.getTime(), Lesson.Time.TIME1);
        STRING_TIME_MAP.put(Lesson.Time.TIME2.getTime(), Lesson.Time.TIME2);
        STRING_TIME_MAP.put(Lesson.Time.TIME3.getTime(), Lesson.Time.TIME3);
        STRING_TIME_MAP.put(Lesson.Time.TIME4.getTime(), Lesson.Time.TIME4);
        STRING_TIME_MAP.put(Lesson.Time.TIME5.getTime(), Lesson.Time.TIME5);
        STRING_TIME_MAP.put(Lesson.Time.TIME6.getTime(), Lesson.Time.TIME6);
        STRING_TIME_MAP.put(Lesson.Time.TIME7.getTime(), Lesson.Time.TIME7);
    }


    public void analyze() {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(SHEET_START);
//            Row row = sheet.getRow(ROW_START);
            for(int i = ROW_START; ; i++) {
                Row row = sheet.getRow(i);
//                if (row == null) {
//                    throw new ScheduleException("Incorrect standard format!");
//                }
                if (isEmptyRow(row)){
                    break;
                }
                if (isWindow(row)){
                    tryGetDay(row);
                    continue;
                }
                processRow(row);
            }
            workbook.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    protected abstract void processRow(Row row);

    protected boolean isEmptyRow(Row row) {
        if (row == null) {
            if(FORMAT == ScheduleFormat.STD_F) {
                throw new ScheduleException("Incorrect standard format!");
            } else return true;
        }
        for (int i = COL_START; i < COL_START + COLS_NUMBER; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK){
                return false;
            }
        }
        return true;
    }

    protected DayOfWeek deriveDayOfWeek(String d) {
        d = processor.processName(d).toLowerCase();
        DayOfWeek dayOfWeek = STRING_DAY_OF_WEEK_MAP.get(d);
        if(dayOfWeek == null) {
            throw new ScheduleException("Invalid day of the week: "+d);
        }
        return dayOfWeek;
    }

    protected boolean isWindow(Row row) {
        String subj = row.getCell(SUBJECT_TEACHER_COL).getStringCellValue();
        subj = subj.replaceAll("\\s+", "");
        return subj.isBlank();
    }

//    protected SubjectType getGroup(Row row) {
//        Cell cell = row.getCell(GROUP_COL);
//        int group = 0;
//        if (cell.getCellType() == CellType.NUMERIC) {
//            group = (int)Math.round(cell.getNumericCellValue());
//        }
//        return new SubjectType(group);
//    }



    protected SubjectType getGroup(Row row) {
        System.out.println("ANALYZER TYPE: "+FORMAT);
        System.out.println("ANALYZER TYPE: "+LECTURE_STRING);
        Cell cell = row.getCell(GROUP_COL);
        if (cell.getCellType() == CellType.NUMERIC) {
            return new SubjectType((int)Math.round(cell.getNumericCellValue()));
        }
        if (cell.getCellType() == CellType.STRING) {
            String groupString = processor.processName(cell.getStringCellValue()).toLowerCase();
            if (groupString.equals(LECTURE_STRING)) {
                return new SubjectType(0);
            } else {
                return new SubjectType(tryGetInt(groupString));
            }
        }
        throw new ScheduleException("Incorrect group format: "+cell.getCellType());
    }

    protected int tryGetInt(String groupString) {
        try {
            return Integer.parseInt(processor.processName(groupString));
        } catch (NumberFormatException e) {
            throw new ScheduleException("Incorrect group format: "+groupString);
        }
    }



    protected String getWeeks(Row row) {
        Cell cell = row.getCell(WEEKS_COL);
        if (cell.getCellType() == CellType.NUMERIC) {
            return Integer.toString((int)Math.round(cell.getNumericCellValue()));
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().replaceAll("\\s*", "");
        }
        return row.getCell(WEEKS_COL).toString();
    }

    protected Room getRoom(Row row) {
        Cell cell = row.getCell(ROOM_COL);
        if (cell.getCellType() == CellType.NUMERIC) {
            return new Room(Long.toString(Math.round(cell.getNumericCellValue())));
        }
        if (cell.getCellType() == CellType.STRING) {
            String roomString = row.getCell(ROOM_COL).getStringCellValue();
            roomString = processor.processName(roomString).toLowerCase();
            if (roomString.equals(REMOTELY)) {
                return new Room();
            } else {
                return new Room(cell.toString());
            }
        }
        throw new ScheduleException("Room is invalid: "+cell.toString());
    }

    protected Lesson.Time tryGetTime(Row row) {
        String time = row.getCell(TIME_COL).getStringCellValue();
        time = time.replaceAll("\\s+", "");
        if (time.isBlank()){
            return this.time;
        }
        Lesson.Time t = STRING_TIME_MAP.get(time);
        if (t == null){
            throw new ScheduleException("Invalid time: "+time);
        }
        return this.time = t;
    }

    protected DayOfWeek tryGetDay(Row row) {
        String d = row.getCell(DAY_COL).getStringCellValue();
        if (d.isBlank()) return dayOfWeek;
        return dayOfWeek = deriveDayOfWeek(d);
    }


    public List<ScheduleAnalyzer.LessonInfo> getLessons() {
        return lessons;
    }

    protected static class SubjectTeacher {

        String firstName;
        String lastName;
        String surname;
        String subject;


        public SubjectTeacher(String firstName, String lastName, String surname, String subject) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.surname = surname;
            this.subject = subject;
        }
        static SubjectTeacher getFrom(Row row) {
            String subjString = row.getCell(SUBJECT_TEACHER_COL).getStringCellValue();
            String[] strings = subjString.split("\\s*,\\s*");
            if (strings.length != 2) {
                throw new ScheduleException("Subject string is incorrect: "+subjString);
            }
            String subject = strings[0];
            String teacher = strings[1].replaceFirst("([a-zа-яїіє]+\\.\\s*)+","").replaceAll("\\s+","");
            String[] teacherName = teacher.split("\\s*\\.\\s*");
            if (teacherName.length != 3) {
                throw new ScheduleException("Teacher's name is incorrect: "+teacher);
            }
            String firstName = teacherName[0];
            String lastName = teacherName[1];
            String surname = teacherName[2];
            return new SubjectTeacher(firstName,lastName,surname,subject);
        }


        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getSurname() {
            return surname;
        }

        public String getSubject() {
            return subject;
        }
    }

}
