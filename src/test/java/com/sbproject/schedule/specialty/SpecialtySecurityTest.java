package com.sbproject.schedule.specialty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration
@AutoConfigureMockMvc
//@WebMvcTest(SpecialtyRestController.class)
public class SpecialtySecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        System.out.println(mockMvc);
    }

    @Test
    public void test1() throws Exception {
        mockMvc.perform(get("/login")).andDo(print())
                .andExpect(view().name("login"));
    }

    // ------------------- update specialty ------------------------------------
    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_updateSpecialty_thenResponseOK() throws Exception {
        String cnt = "{\"name\": \"New spec7\", \"year\":2}";
        mockMvc.perform(put("/specialties/1")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New spec7"))
                .andExpect(jsonPath("$.year").value(2));
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_updateSpecialty_incorrectBody_thenResponseOK() throws Exception {
        String cnt = "{\"name\": \"New spec\", \"year\":12}";
        mockMvc.perform(put("/specialties/1")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenSpecialtiesURI_whenRoleRegularOrAnonymous_updateSpecialty_thenResponseOK() throws Exception {
        String cnt = "{\"name\": \"New spec\", \"year\":2}";
        mockMvc.perform(put("/specialties/1")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    // ------------------- add specialty ------------------------------------

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_addSpecialty_thenResponseOK() throws Exception {
        String cnt = "{\"name\": \"New spec\", \"year\":2, \"subjects\": []}";
        mockMvc.perform(post("/specialties")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New spec"))
                .andExpect(jsonPath("$.year").value(2))
                .andExpect(jsonPath("$.subjects").isEmpty());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_and_incorrectBody_addSpecialty_thenResponseBadRequest() throws Exception {
        String cnt = "{\"name\": \"New spec\", \"subjects\": []}";
        mockMvc.perform(post("/specialties")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_and_incorrectYear_addSpecialty_thenResponseBadRequest() throws Exception {
        String cnt = "{\"name\": \"New spec\", \"year\":20, \"subjects\": []}";
        mockMvc.perform(post("/specialties")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_and_incorrectSubjects_addSpecialty_thenResponseBadRequest() throws Exception {
        String cnt = "{\"name\": \"New spec\", \"year\":2, \"subjects\": [123,456]}";
        mockMvc.perform(post("/specialties")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenSpecialtiesURI_whenRoleRegularOrAnonymous_addSpecialty_thenResponseOK() throws Exception {
        String cnt = "{\"name\": \"New spec\", \"year\":2, \"subjects\": []}";
        mockMvc.perform(post("/specialties")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    // ------------------- get specialty by id ------------------------------
    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR", "ADMIN"})
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_getSpecialty_thenResponseOK() throws Exception {
        mockMvc.perform(get("/specialties/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ADMIN"})
    @Test
    public void givenSpecialtiesURI_whenRoleAdminOrRegular_getIncorrectSpecialty_thenResponseOK() throws Exception {
        mockMvc.perform(get("/specialties/100")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ANONYMOUS")
    @Test
    public void givenSpecialtiesURI_whenRoleAnonymous_getIncorrectSpecialty_thenResponseOK() throws Exception {
        mockMvc.perform(get("/specialties/1")).andDo(print())
                .andExpect(status().isForbidden());
    }



    // ------------------- delete specialty by id ---------------------------
    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_deleteSpecialty_thenResponseOK() throws Exception {
        mockMvc.perform(delete("/specialties/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_deleteIncorrectSpecialty_thenResponseOK() throws Exception {
        mockMvc.perform(delete("/specialties/100")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenSpecialtiesURI_whenRoleRegularOrAnonymous_deleteSpecialty_thenResponseForbidden() throws Exception {
        mockMvc.perform(delete("/specialties/1")).andDo(print())
                .andExpect(status().isForbidden());
    }

    // ------------------- delete all specialties ---------------------------
    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_deleteAllSpecialties_thenResponseOK() throws Exception {
        mockMvc.perform(delete("/specialties")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenSpecialtiesURI_whenRoleRegularOrAnonymous_deleteAllSpecialties_thenResponseForbidden() throws Exception {
        mockMvc.perform(delete("/specialties")).andDo(print())
                .andExpect(status().isForbidden());
    }






    // ------------------- get all specialties ----------------------
    @WithMockUser(username = "usr", password = "1111", roles = {"ADMIN", "REGULAR"})
    @Test
    public void givenSpecialtiesURI_whenRoleAdmin_thenResponseOK() throws Exception {
        mockMvc.perform(get("/specialties")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ANONYMOUS")
    @Test
    public void givenSpecialtiesURI_whenRoleAnonymous_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/specialties")).andDo(print())
                .andExpect(status().isForbidden());

    }



}