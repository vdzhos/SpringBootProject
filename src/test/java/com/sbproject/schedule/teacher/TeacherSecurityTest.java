package com.sbproject.schedule.teacher;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        System.out.println(mockMvc);
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenTeachersURI_whenRoleAdmin_thenResponseOK() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/teachers/all")).andDo(print())
                .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "REGULAR")
    @Test
    public void givenTeachersURI_whenRoleRegular_thenResponseOK() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/teachers/all")).andDo(print())
                .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ANONYMOUS")
    @Test
    public void givenSubjectsURI_whenRoleAnonymous_thenResponseForbidden() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/teachers/all")).andDo(print())
                .andExpect(status().is(403)).andReturn();

        Assertions.assertEquals(403, result.getResponse().getStatus());
    }

    //Get teacher by Id

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR", "ADMIN"})
    @Test
    public void givenTeachersURI_whenRoleAdminOrRegular_getTeacher_thenResponseOK() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/teachers/7")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ANONYMOUS")
    @Test
    public void givenTeachersURI_whenRoleAnonymous_getTeacher_thenResponseForbidden() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/teachers/7")).andDo(print())
                .andExpect(status().isForbidden()).andReturn();
        Assertions.assertEquals(403, result.getResponse().getStatus());
    }

    //Add teacher

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenTeacherURI_whenRoleAdmin_addTeacher_thenResponseOK() throws Exception {
        String cnt = "{\n" +
                "    \"name\": \"Бублик A A\",\n" +
                "    \"subjects\": [4]}";
        mockMvc.perform(post("/REST/teachers/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Бублик A A"))
                .andExpect(jsonPath("$.subjects[0]").value(4));
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenTeacherURI_whenRoleAdmin_and_incorrectBody_addTeacher_thenResponseBadRequest() throws Exception {
        String cnt = "{\n" +
                "    \"name\": \"Бублик В В\",\n" +
                "    \"subjects\": []}";
        mockMvc.perform(post("/REST/teachers/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenTeacherURI_whenRoleRegularOrAnonymous_addTeacher_thenResponseForbidden() throws Exception {
        String cnt = "{\n" +
                "    \"name\": \"Бублик В В\",\n" +
                "    \"subjects\": [4]}";
        mockMvc.perform(post("/REST/teachers/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    //Update teacher

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenTeacherURI_whenRoleAdmin_updateTeacher_thenResponseOK() throws Exception {
        String cnt = "{\n" +
                "    \"name\": \"Бублик В В\",\n" +
                "    \"subjects\": [4]}";
        mockMvc.perform(put("/REST/teachers/7")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Бублик В В"))
                .andExpect(jsonPath("$.subjects[0]").value(4));
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenTeacherURI_whenRoleAdmin_updateTeacher_incorrectBody_thenResponseBadRequest() throws Exception {
        String cnt = "{\n" +
                "    \"time\": \"TIME6\",\n" +
                "    \"subject\": 6,\n" +
                "    \"group\": 5,\n" +
                "    \"weeks\": \"1-12,14\",\n" +
                "    \"room\": \"224\",\n" +
                "}";
        mockMvc.perform(put("/REST/teachers/7")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenTeacherURI_whenRoleRegularOrAnonymous_updateTeacher_thenResponseForbidden() throws Exception {
        String cnt = "{\n" +
                "    \"name\": \"Бублик В В\",\n" +
                "    \"subjects\": [4]}";
        mockMvc.perform(put("/REST/teachers/10")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    //Delete teacher by Id

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenTeacherURI_whenRoleAdmin_deleteTeacher_thenResponseOK() throws Exception {
        mockMvc.perform(delete("/REST/teachers/7")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenTeacherURI_whenRoleAdmin_deleteIncorrectTeacher_thenResponseNotFound() throws Exception {
        mockMvc.perform(delete("/REST/teachers/100")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenTeacherURI_whenRoleRegularOrAnonymous_deleteTeacher_thenResponseForbidden() throws Exception {
        mockMvc.perform(delete("/REST/teachers/7")).andDo(print())
                .andExpect(status().isForbidden());
    }
}