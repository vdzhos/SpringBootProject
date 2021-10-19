package com.sbproject.schedule.utils;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Markers {

    public static final Marker IMPORTANT_MARKER = MarkerManager.getMarker("IMPORTANT");
    public static final Marker ALTERING_LESSON_TABLE_MARKER = MarkerManager.getMarker("ALTERING_LESSON_TABLE")
            .setParents(IMPORTANT_MARKER);
    public static final Marker DELETE_LESSON_MARKER = MarkerManager.getMarker("DELETE_LESSON")
            .setParents(ALTERING_LESSON_TABLE_MARKER);
    public static final Marker USER_MARKER = MarkerManager.getMarker("USER_ACT")
    		.setParents(IMPORTANT_MARKER);
}
