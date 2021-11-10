package com.sbproject.schedule.controllers;

import com.sbproject.schedule.exceptions.handlers.ErrorMessage;
import com.sbproject.schedule.exceptions.subject.NoSubjectWithSuchIdToDelete;
import com.sbproject.schedule.exceptions.subject.NoSubjectWithSuchIdToUpdate;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import com.sbproject.schedule.utils.Markers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/subjects")
public class SubjectRestController {

    @Autowired
    private SubjectServiceImpl subjectService;
    private static Logger logger = LogManager.getLogger(SubjectRestController.class);

    @Operation(summary = "Get all subjects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found subjects",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Subject.class))) })})
    @GetMapping("/get")
    public Iterable<Subject> getSubjects(){
        return subjectService.getAll();
    }

    @Operation(summary = "Get a subject by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the subject by name",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subject.class))}),
            @ApiResponse(responseCode = "404", description = "Subject with the name not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @GetMapping("/getByName/{name}")
    public Subject getSubject(@PathVariable("name") String name){
        return subjectService.getSubjectByName(name);
    }

    @Operation(summary = "Get a subject by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the subject by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subject.class))}),
            @ApiResponse(responseCode = "404", description = "Subject with the id not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @GetMapping("/get/{id}")
    public Subject getSubject(@PathVariable("id") Long id) {
        return subjectService.getSubjectById(id);
    }

    @Operation(summary = "Delete a subject by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subject.class))}),
            @ApiResponse(responseCode = "404", description = "Subject with the id not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @DeleteMapping("/delete/{id}")
    public void deleteSubject(@PathVariable("id") Long id) {
        subjectService.deleteSubject(id);
        logger.info(Markers.DELETE_SUBJECT_MARKER,"Subject has been successfully deleted!");
    }

    @Operation(summary = "Add new subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New subject added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subject.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid subject object passed",
                    content = @Content) })
    @PostMapping("/add")
    public Subject addSubject(@Valid @RequestBody Subject subject){
        Subject s = subjectService.addSubject(subject);
        logger.info(Markers.ALTERING_SUBJECT_TABLE_MARKER,"Subject {} with {} groups has been successfully added!", subject.getName(), subject.getQuantOfGroups());
        return s;
    }

    @Operation(summary = "Update the subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subject.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect subject object passed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Subject with the id not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})

    @PutMapping("/update/{id}")
    public Subject updateSubject(@PathVariable("id") Long id, @Valid @RequestBody Subject subject) {
        subject.setId(id);
        Subject s = subjectService.updateSubject(subject);
        logger.info(Markers.UPDATE_SUBJECT_MARKER,"Subject has been successfully deleted!");
        return s;
    }

    @ExceptionHandler(NoSubjectWithSuchIdToDelete.class)
    public ResponseEntity<Map<String,String>> handleException(NoSubjectWithSuchIdToDelete ex){
        Map<String, String> result = new HashMap<>();
        result.put("deleted", "false");
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSubjectWithSuchIdToUpdate.class)
    public ResponseEntity<Map<String,String>> handleException(NoSubjectWithSuchIdToUpdate ex){
        Map<String, String> result = new HashMap<>();
        result.put("updated", "false");
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

}
