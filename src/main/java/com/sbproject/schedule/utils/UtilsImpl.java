package com.sbproject.schedule.utils;

public class UtilsImpl  implements Utils {

    public String processName(String name){
        return name.replaceAll("\\s+"," ").trim();
    }



}
