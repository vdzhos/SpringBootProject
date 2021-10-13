package com.sbproject.schedule.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    private Time time;
//    private Subject subject;
//    private Teacher teacher;
//    private SubjectType group;
//    private String weeks;
//    private Room room;
//    private DayOfWeek dayOfWeek;

    public Lesson() {
    }

    public Lesson(Long id, Time time, Subject subject, Teacher teacher, SubjectType group,
                  String weeks, Room room, DayOfWeek dayOfWeek) {
        this.id = id;
//        this.subject = subject;
//        this.time = time;
//        this.teacher = teacher;
//        this.group = group;
//        this.weeks = weeks;
//        this.room = room;
//        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lesson{")
                .append("id=").append(id).append(',');
//                .append("time=").append(time).append(',')
//                .append("subject=").append(subject).append(',')
//                .append("teacher=").append(teacher).append(',')
//                .append("group=").append(group).append(',')
//                .append("weeks=").append(weeks).append(',')
//                .append("room=").append(room).append(',')
//                .append("dayOfWeek=").append(dayOfWeek).append('}');
        return super.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Subject getSubject() {
//        return subject;
//    }

//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }

//    public Time getTime() {
//        return time;
//    }

//    public void setTime(Time time) {
//        this.time = time;
//    }

//    public Teacher getTeacher() {
//        return teacher;
//    }

//    public void setTeacher(Teacher teacher) {
//        this.teacher = teacher;
//    }

//    public SubjectType getGroup() {
//        return group;
//    }

//    public void setGroup(SubjectType group) {
//        this.group = group;
//    }

//    public String getWeeks() {
//        return weeks;
//    }

//    public void setWeeks(String weeks) {
//        this.weeks = weeks;
//    }

//    public Room getRoom() {
//        return room;
//    }

//    public void setRoom(Room room) {
//        this.room = room;
//    }

//    public DayOfWeek getDayOfWeek() {
//        return dayOfWeek;
//    }

//    public void setDayOfWeek(DayOfWeek dayOfWeek) {
//        this.dayOfWeek = dayOfWeek;
//    }

    public enum Room {

        REMOTELY("Remotely"), ROOM("Placeholder");

        private String room;

        Room(String room) {
            setRoom(room);
        }

        public String getRoom() {
            return room;
        }

        public Room setRoom(String room) {
            this.room = room;
            return this;
        }


        @Override
        public String toString() {
            return room;
        }
    }

    public enum SubjectType {

        LECTURE(0), PRACTICE(1);

        private String group;

        SubjectType(int group) {
            setGroup(group);
        }

        public String getGroup() {
            return group;
        }

        public SubjectType setGroup(int group) {
            if (group < 0) throw new IllegalArgumentException("Number of group can't be negative!");
            if (group == 0) this.group = "lecture";
            else this.group = String.valueOf(group);
            return this;
        }


        @Override
        public String toString() {
            return group;
        }
    }

    public enum Time {

        TIME1("8:30-9:50"), TIME2("10:00-11:20"), TIME3("11:40-13:00"), TIME4("13:30-14:50"),
        TIME5("15:00-16:20"), TIME6("16:30-17:50"), TIME7("18:00-19:20");

        private String time;

        Time(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }


        @Override
        public String toString() {
            return time;
        }
    }
}