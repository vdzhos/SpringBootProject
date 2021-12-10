package com.sbproject.schedule.subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SubjectSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        System.out.println(mockMvc);
    }

    // ------------------- update subject ------------------------------------
    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSubjectsURI_whenRoleAdmin_updateSubject_thenResponseOK() throws Exception {
        String cnt = "{\"name\": \"Upd subj\", \"quantOfGroups\":2, \"specialties\":[1, 2]}";
        mockMvc.perform(put("/subjects/update/4")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Upd subj"))
                .andExpect(jsonPath("$.quantOfGroups").value(2));
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSubjectsURI_whenRoleAdmin_updateSubject_incorrectBody_thenResponseBadRequest() throws Exception {
        String cnt = "{\"name\": \"Upd subj\", \"quantOfGroups\":70, \"specialties\":[1, 2]}";
        mockMvc.perform(put("/subjects/update/4")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenSubjectsURI_whenRoleRegularOrAnonymous_updateSubject_thenResponseForbidden() throws Exception {
        String cnt = "{\"name\": \"Upd subj\", \"quantOfGroups\":2, \"specialties\":[1, 2]}";
        mockMvc.perform(put("/subjects/update/4")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // ------------------- add subject ------------------------------------

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSubjectsURI_whenRoleAdmin_addSubject_thenResponseOK() throws Exception {
        String cnt = "{\"name\": \"New subj\", \"quantOfGroups\":2, \"specialties\":[1, 2]}";
        mockMvc.perform(post("/subjects/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New subj"))
                .andExpect(jsonPath("$.quantOfGroups").value(2));
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSubjectsURI_whenRoleAdmin_and_incorrectBody_addSubject_thenResponseBadRequest() throws Exception {
        String cnt = "{\"name\": \"New subj\", \"specialties\":[1, 2]}";
        mockMvc.perform(post("/subjects/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSubjectsURI_whenRoleAdmin_and_incorrectSpecialties_addSubject_thenResponseBadRequest() throws Exception {
        String cnt = "{\"name\": \"New subj\", \"quantOfGroups\":2, \"specialties\":[100, 200]}";
        mockMvc.perform(post("/subjects/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenSubjectsURI_whenRoleRegularOrAnonymous_addSubject_thenResponseForbidden() throws Exception {
        String cnt = "{\"name\": \"New subj\", \"quantOfGroups\":2, \"specialties\":[1, 2]}";
        mockMvc.perform(post("/subjects/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // ------------------- get subject by id ------------------------------
    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR", "ADMIN"})
    @Test
    public void givenSubjectsURI_whenRoleAdmin_getSubject_thenResponseOK() throws Exception {
        mockMvc.perform(get("/subjects/get/4")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ADMIN"})
    @Test
    public void givenSubjectsURI_whenRoleAdminOrRegular_getIncorrectSubject_thenResponseNotFound() throws Exception {
        mockMvc.perform(get("/subjects/get/300")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ANONYMOUS")
    @Test
    public void givenSubjectsURI_whenRoleAnonymous_getIncorrectSubject_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/subjects/get/4")).andDo(print())
                .andExpect(status().isForbidden());
    }

    // ------------------- delete subject by id ---------------------------
    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSubjectsURI_whenRoleAdmin_deleteSubject_thenResponseOK() throws Exception {
        mockMvc.perform(delete("/subjects/delete/4")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenSubjectsURI_whenRoleAdmin_deleteIncorrectSubject_thenResponseNotFound() throws Exception {
        mockMvc.perform(delete("/subjects/delete/300")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenSubjectsURI_whenRoleRegularOrAnonymous_deleteSubject_thenResponseForbidden() throws Exception {
        mockMvc.perform(delete("/subjects/delete/4")).andDo(print())
                .andExpect(status().isForbidden());
    }

    // ------------------- get all subjects ----------------------
    @WithMockUser(username = "usr", password = "1111", roles = {"ADMIN", "REGULAR"})
    @Test
    public void givenSubjectsURI_whenRoleAdmin_thenResponseOK() throws Exception {
        mockMvc.perform(get("/subjects/get")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ANONYMOUS")
    @Test
    public void givenSubjectsURI_whenRoleAnonymous_thenResponseForbidden() throws Exception {
        mockMvc.perform(get("/subjects/get")).andDo(print())
                .andExpect(status().isForbidden());

    }

}
