package com.sbproject.schedule.controllers.rest;

import com.sbproject.schedule.exceptions.specialty.IncorrectRequestBodyException;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/specialties")
class SpecialtyRestController {

    private SpecialtyService specialtyService;

    @Autowired
    public SpecialtyRestController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public Iterable<Specialty> getAllSpecialties(){
        return specialtyService.getAll();
    }


    @GetMapping("/{id}")
    public Specialty getSpecialty(@Min(1) @PathVariable(name = "id") Long id){
        return specialtyService.getSpecialty(id);
    }

    @PostMapping
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

    @PutMapping("/{id}")
    public Specialty updateSpecialty(@Valid @RequestBody Specialty newSpecialty, @Min(1) @PathVariable(name = "id") Long id) {
        return specialtyService.updateSpecialty(id,newSpecialty.getName(),newSpecialty.getYear());
    }

    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable(name = "id") @Min(1) Long id){
        specialtyService.deleteSpecialty(id);
    }

    @DeleteMapping
    public void deleteAll(){
        specialtyService.deleteAll();
    }

}
