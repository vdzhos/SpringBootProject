package com.sbproject.schedule.controllers.rest;

import com.sbproject.schedule.exceptions.teacher.NoTeacherWithSuchIdException;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.interfaces.TeacherService;
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
@RequestMapping("/REST/teachers")
public class TeacherControllerREST {

    private TeacherService teacherService;

    @Autowired
    public TeacherControllerREST(TeacherService teacherService)
    {
        this.teacherService = teacherService;
    }

    @Operation(summary = "Get all teachers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the teachers.",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Teacher.class)))})})
    @GetMapping("/all")
    @PreAuthorize("hasRole('REGULAR') or hasRole('ADMIN')")
    public List<Teacher> getAllTeachers() {
        return (List<Teacher>) teacherService.getAll();
    }

    @Operation(summary = "Get teacher by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the teacher.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Teacher.class)) }),
            @ApiResponse(responseCode = "404", description = "Teacher not found.",
                    content = @Content) })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('REGULAR') or hasRole('ADMIN')")
    public Teacher getTeacherById(@PathVariable(value = "id") Long id) throws Exception {
        return teacherService.getTeacherById(id);
    }

    @Operation(summary = "Add teacher.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added the teacher.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Teacher.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid teacher object passed.",
                    content = @Content)})
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher addTeacher(@Valid @RequestBody Teacher teacher){
        return teacherService.addTeacher(teacher);
    }

    @Operation(summary = "Update teacher by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the teacher.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Teacher.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid teacher object passed.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Teacher not found.",
                    content = @Content) })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher updateTeacher(@PathVariable(value = "id") Long id, @Valid @RequestBody Teacher teacher) throws NoTeacherWithSuchIdException {
        teacher.setId(id);
        return teacherService.updateTeacher(teacher);
    }

    @Operation(summary = "Delete teacher by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the teacher.",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "Teacher not found.",
                    content = @Content) })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteTeacher(@PathVariable(value = "id") Long id) throws NoTeacherWithSuchIdException {
        teacherService.deleteTeacher(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted",true);
        return result;
    }

    @ExceptionHandler(NoTeacherWithSuchIdException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Map<String, String> handleException(NoTeacherWithSuchIdException ex){
        Map<String, String> result = new HashMap<>();
        result.put(ex.getDelOrUpdOrGet(), "false");
        result.put("error", ex.getMessage());
        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(MethodArgumentNotValidException ex){
        Map<String, String> result = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            result.put(fieldName, errorMessage);
        });
        return result;
    }
}
