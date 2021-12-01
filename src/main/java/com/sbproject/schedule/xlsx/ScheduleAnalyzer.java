package com.sbproject.schedule.xlsx;

import com.sbproject.schedule.exceptions.schedule.ScheduleException;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Room;
import com.sbproject.schedule.models.SubjectType;
import com.sbproject.schedule.utils.Utils;
import com.sbproject.schedule.utils.UtilsImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleAnalyzer {

    private static final String LECTURE_STRING = "лекція";

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


    private Utils processor;

    private static final String REMOTELY = "дистанційно";
    private static final int COLS_NUMBER = 6;

    private static final int DAY_COL = 0;
    private static final int TIME_COL = 1;
    private static final int SUBJECT_TEACHER_COL = 2;
    private static final int GROUP_COL = 3;
    private static final int WEEKS_COL = 4;
    private static final int ROOM_COL = 5;

    private static final int SHEET_START = 0;
    private static final int ROW_START = 10;
    private static final int COL_START = 0;

    private DayOfWeek dayOfWeek;
    private Lesson.Time time;

    private InputStream inputStream;

    private List<LessonInfo> lessons;

    private static final Map<String, DayOfWeek> STRING_DAY_OF_WEEK_MAP;
    private static final Map<String, Lesson.Time> STRING_TIME_MAP;

    static {
        STRING_DAY_OF_WEEK_MAP = new HashMap<>();
        STRING_DAY_OF_WEEK_MAP.put("понеділок",DayOfWeek.MONDAY);
        STRING_DAY_OF_WEEK_MAP.put("вівторок",DayOfWeek.TUESDAY);
        STRING_DAY_OF_WEEK_MAP.put("середа",DayOfWeek.WEDNESDAY);
        STRING_DAY_OF_WEEK_MAP.put("четвер",DayOfWeek.THURSDAY);
        STRING_DAY_OF_WEEK_MAP.put("п'ятниця",DayOfWeek.FRIDAY);
        STRING_DAY_OF_WEEK_MAP.put("п`ятниця",DayOfWeek.FRIDAY);
        STRING_DAY_OF_WEEK_MAP.put("субота",DayOfWeek.SATURDAY);


        STRING_TIME_MAP = new HashMap<>();
        STRING_TIME_MAP.put(Lesson.Time.TIME1.getTime(), Lesson.Time.TIME1);
        STRING_TIME_MAP.put(Lesson.Time.TIME2.getTime(), Lesson.Time.TIME2);
        STRING_TIME_MAP.put(Lesson.Time.TIME3.getTime(), Lesson.Time.TIME3);
        STRING_TIME_MAP.put(Lesson.Time.TIME4.getTime(), Lesson.Time.TIME4);
        STRING_TIME_MAP.put(Lesson.Time.TIME5.getTime(), Lesson.Time.TIME5);
        STRING_TIME_MAP.put(Lesson.Time.TIME6.getTime(), Lesson.Time.TIME6);
        STRING_TIME_MAP.put(Lesson.Time.TIME7.getTime(), Lesson.Time.TIME7);
    }

    public ScheduleAnalyzer(InputStream inputStream) {
        this.inputStream = inputStream;
        processor = new UtilsImpl();
        lessons = new ArrayList<>();

        dayOfWeek = null;
        time = null;
    }
//    private void init(InputStream inputStream) {
//        this.inputStream = inputStream;
//        processor = new UtilsImpl();
//        lessons = new ArrayList<>();
//
//        dayOfWeek = null;
//        time = null;
//    }

    public void analyze() {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(SHEET_START);
            Row row = sheet.getRow(ROW_START);
            for(int i = ROW_START; !isEmptyRow(row); i++) {
                row = sheet.getRow(i);
                if (isWindow(row)){
                    tryGetDay(row);
                    continue;
                }
                processRow(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isWindow(Row row) {
        String subj = row.getCell(SUBJECT_TEACHER_COL).getStringCellValue();
        subj = subj.replaceAll("\\s+", "");
        return subj.isBlank();
    }

    private void processRow(Row row) {
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

    private Room getRoom(Row row) {
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


    private String getWeeks(Row row) {
        Cell cell = row.getCell(WEEKS_COL);
        if (cell.getCellType() == CellType.NUMERIC) {
            return Integer.toString((int)Math.round(cell.getNumericCellValue()));
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().replaceAll("\\s*", "");
        }
        return row.getCell(WEEKS_COL).toString();
    }

    private SubjectType getGroup(Row row) {
        Cell cell = row.getCell(GROUP_COL);
        int group = 0;
        if (cell.getCellType() == CellType.NUMERIC) {
            group = (int)Math.round(cell.getNumericCellValue());
        }
        return new SubjectType(group);
    }

//    private SubjectType getGroup(Row row) {
//        Cell cell = row.getCell(GROUP_COL);
//        if (cell.getCellType() == CellType.NUMERIC) {
//            return new SubjectType((int)Math.round(cell.getNumericCellValue()));
//        }
//        if (cell.getCellType() == CellType.STRING) {
//            String groupString = processor.processName(cell.getStringCellValue()).toLowerCase();
//            if (groupString.equals(LECTURE_STRING)) {
//                return new SubjectType(0);
//            }
//            else return new SubjectType(groupString);
//        }
//    }


    private Lesson.Time tryGetTime(Row row) {
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

    private DayOfWeek tryGetDay(Row row) {
        String d = row.getCell(DAY_COL).getStringCellValue();
        if (d.isBlank()) return dayOfWeek;
        return dayOfWeek = deriveDayOfWeek(d);
    }




    private DayOfWeek deriveDayOfWeek(String d) {
        d = processor.processName(d).toLowerCase();
        DayOfWeek dayOfWeek = STRING_DAY_OF_WEEK_MAP.get(d);
        if(dayOfWeek == null) {
            throw new ScheduleException("Invalid day of the week: "+d);
        }
        return dayOfWeek;
    }



    private boolean isEmptyRow(Row row) {
        for (int i = COL_START; i < COL_START + COLS_NUMBER; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK){
                return false;
            }
        }
        return true;
    }


    private static class SubjectTeacher {

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

    public List<LessonInfo> getLessons() {
        return lessons;
    }

    public static void main(String[] args) throws FileNotFoundException {
//        String s = "Hello";
//        System.out.println(s.toString());
//
//        Object o = s;
//        System.out.println(o.toString());

        ScheduleAnalyzer analyzer = new ScheduleAnalyzer(new FileInputStream(new File("src/main/resources/download/Інженерія_програмного_забезпечення_БП-2_Осінь_2021–2022.xlsx")));
        analyzer.analyze();
        System.out.println(analyzer.getLessons());
    }


    public void saveSchedule() {

    }

}
