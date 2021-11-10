package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdToDelete;
import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdToUpdate;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.services.interfaces.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/REST/lessons")
public class LessonControllerREST {

    private LessonService lessonService;

    @Autowired
    public LessonControllerREST(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Operation(summary = "Get all lessons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found lessons",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Lesson.class))) })})
    @GetMapping("/all")
    public List<Lesson> getAllLessons() {
        return (List<Lesson>) lessonService.getAll();
    }

    @Operation(summary = "Get a lesson by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the lesson",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Lesson.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public Lesson getLessonById(@PathVariable(value = "id") Long id) throws Exception {
        return lessonService.getLessonById(id);
    }

    @Operation(summary = "Add new lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Lesson.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid lesson object passed",
                    content = @Content) })
    @PostMapping("/add")
    public Lesson addLesson(@Valid @RequestBody Lesson lesson){
        return lessonService.addLesson(lesson);
    }

    @Operation(summary = "Update the lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Lesson.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid lesson object passed",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable(value = "id") Long id, @Valid @RequestBody Lesson lesson) throws NoLessonWithSuchIdToUpdate {
        lesson.setId(id);
        return lessonService.updateLesson(lesson);
    }

    @Operation(summary = "Delete the lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteLesson(@PathVariable(value = "id") Long id) throws NoLessonWithSuchIdToDelete {
        lessonService.deleteLesson(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted",true);
        return result;
    }

    @ExceptionHandler(NoLessonWithSuchIdToDelete.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(NoLessonWithSuchIdToDelete ex){
        Map<String, String> result = new HashMap<>();
        result.put("deleted", "false");
        result.put("error", ex.getMessage());
        return result;
    }

    @ExceptionHandler(NoLessonWithSuchIdToUpdate.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(NoLessonWithSuchIdToUpdate ex){
        Map<String, String> result = new HashMap<>();
        result.put("updated", "false");
        result.put("error", ex.getMessage());
        return result;
    }

}
