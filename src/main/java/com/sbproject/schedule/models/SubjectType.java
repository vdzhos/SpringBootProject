package com.sbproject.schedule.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(value={ "type" }, allowGetters=true)
public class SubjectType implements Serializable {

    private String group;
    private SubjectTypeEnum type;

    public SubjectType(int group) {
        setGroup(group);
        if(group<=0) this.type = SubjectTypeEnum.LECTURE;
        else this.type = SubjectTypeEnum.PRACTICE;
    }

    public String getGroup() {
        return group;
    }

    public SubjectType setGroup(int group) {
        if (group <= 0) this.group = "lecture";
        else this.group = String.valueOf(group);
        return this;
    }

    public SubjectTypeEnum getType() {
        return type;
    }

    @Override
    public String toString() {
        return group;
    }

    public enum SubjectTypeEnum{
        LECTURE, PRACTICE;
    }
}
