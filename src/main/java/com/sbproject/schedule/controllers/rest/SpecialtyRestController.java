package com.sbproject.schedule.controllers.rest;

import com.sbproject.schedule.exceptions.handlers.ErrorMessage;
import com.sbproject.schedule.exceptions.specialty.IncorrectRequestBodyException;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/specialties")
public class SpecialtyRestController {

    private SpecialtyService specialtyService;

    @Autowired
    public SpecialtyRestController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Operation(summary = "Get all specialties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the specialties",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Specialty.class)) })})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    @GetMapping
    public Iterable<Specialty> getAllSpecialties(){
        return specialtyService.getAll();
    }

    @Operation(summary = "Get a specialty by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the specialty",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Specialty.class))}),
            @ApiResponse(responseCode = "404", description = "Specialty not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    @GetMapping("/{id}")
    public Specialty getSpecialty(@Parameter(description = "id of the specialty to be searched") @Min(1) @PathVariable(name = "id") Long id){
        return specialtyService.getSpecialty(id);
    }

    @Operation(summary = "Add new specialty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added the specialty",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Specialty.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect request body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Specialty addSpecialty(@RequestBody String json){
        try {
            JSONObject specialty = new JSONObject(json);
            return specialtyService.addSpecialty(specialty.getString("name"),specialty.getInt("year"),specialty.getJSONArray("subjects"));
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new IncorrectRequestBodyException(""+e.getMessage());
        }
    }
//    @PostMapping
//    public Specialty addSpecialty(@Valid @RequestBody Specialty specialty){
//        return specialtyService.addSpecialty(specialty.getName(),specialty.getYear());
//    }

    @Operation(summary = "Update a specialty by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the specialty",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Specialty.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect request body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public Specialty updateSpecialty(@Valid @RequestBody Specialty newSpecialty, @Parameter(description = "id of the specialty to be updated") @Min(1) @PathVariable(name = "id") Long id) {
        return specialtyService.updateSpecialty(id,newSpecialty.getName(),newSpecialty.getYear());
    }

    @Operation(summary = "Delete a specialty by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the specialty",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Specialty with the id not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteSpecialty(@Parameter(description = "id of the specialty to be deleted") @PathVariable(name = "id") @Min(1) Long id){
        specialtyService.deleteSpecialty(id);
    }

    @Operation(summary = "Delete all specialties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All specialties deleted",
                    content = @Content)})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public void deleteAll(){
        specialtyService.deleteAll();
    }

}