package com.sbproject.schedule.controllers.rest;

import com.sbproject.schedule.exceptions.lesson.NoLessonWithSuchIdFound;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @PreAuthorize("hasAnyRole('ADMIN','REGULAR')")
    public List<Lesson> getAllLessons() {
        return (List<Lesson>) lessonService.getAll();
    }

    @Operation(summary = "Get a lesson by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the lesson",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Lesson.class)) }),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content) })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','REGULAR')")
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
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Lesson updateLesson(@PathVariable(value = "id") Long id, @Valid @RequestBody Lesson lesson) throws NoLessonWithSuchIdFound {
        lesson.setId(id);
        return lessonService.updateLesson(lesson);
    }

    @Operation(summary = "Delete the lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Map<String, Boolean> deleteLesson(@PathVariable(value = "id") Long id) throws NoLessonWithSuchIdFound {
        lessonService.deleteLesson(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted",true);
        return result;
    }

    @ExceptionHandler(NoLessonWithSuchIdFound.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Map<String, String> handleException(NoLessonWithSuchIdFound ex){
        Map<String, String> result = new HashMap<>();
        result.put(ex.getAction(), "false");
        result.put("error", ex.getMessage());
        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(MethodArgumentNotValidException ex){
        Map<String, String> result = new HashMap<>();
        result.put("success", "false");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            result.put(fieldName, errorMessage);
        });
        return result;
    }

}
