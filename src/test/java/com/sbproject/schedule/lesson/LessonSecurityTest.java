package com.sbproject.schedule.lesson;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LessonSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        System.out.println(mockMvc);
    }

    //Get all lessons

    @WithMockUser(username = "usr", password = "1111", roles = {"ADMIN","REGULAR"})
    @Test
    public void givenLessonsURI_whenRoleAdmin_thenResponseOK() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/lessons/all")).andDo(print())
                .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ANONYMOUS")
    @Test
    public void givenLessonsURI_whenRoleAnonymous_thenResponseForbidden() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/lessons/all")).andDo(print())
                .andExpect(status().is(403)).andReturn();

        Assertions.assertEquals(403, result.getResponse().getStatus());
    }

    //Get lesson by Id

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR", "ADMIN"})
    @Test
    public void givenLessonsURI_whenRoleAdminOrRegular_getLesson_thenResponseOK() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/lessons/10")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ANONYMOUS")
    @Test
    public void givenLessonsURI_whenRoleAnonymous_getLesson_thenResponseForbidden() throws Exception {
        MvcResult result = mockMvc.perform(get("/REST/lessons/1")).andDo(print())
                .andExpect(status().isForbidden()).andReturn();
        Assertions.assertEquals(403, result.getResponse().getStatus());
    }

    //Add lesson

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenLessonURI_whenRoleAdmin_addLesson_thenResponseOK() throws Exception {
        String cnt = "{\n" +
                "        \"time\": \"TIME6\",\n" +
                "        \"subject\": 5,\n" +
                "        \"teacher\": 9,\n" +
                "        \"group\": 3,\n" +
                "        \"weeks\": \"1-12\",\n" +
                "        \"room\": \"0\",\n" +
                "        \"dayOfWeek\": \"MONDAY\"\n" +
                "}";
        mockMvc.perform(post("/REST/lessons/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").value("TIME6"))
                .andExpect(jsonPath("$.subject").value(5))
                .andExpect(jsonPath("$.teacher").value(9))
                .andExpect(jsonPath("$.group.group").value(3))
                .andExpect(jsonPath("$.group.type").value("PRACTICE"))
                .andExpect(jsonPath("$.group.value").value(3))
                .andExpect(jsonPath("$.weeks").value("1-12"))
                .andExpect(jsonPath("$.room.room").value("0"))
                .andExpect(jsonPath("$.room.type").value("ROOM"))
                .andExpect(jsonPath("$.room.typeOrName").value("0"))
                .andExpect(jsonPath("$.dayOfWeek").value("MONDAY"));
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenLessonURI_whenRoleAdmin_and_incorrectBody_addLesson_thenResponseBadRequest() throws Exception {
        String cnt = "{\n" +
                "        \"time\": \"TIME6\",\n" +
                "        \"subject\": 5,\n" +
                "        \"group\": 3,\n" +
                "        \"weeks\": \"1-12\",\n" +
                "        \"room\": \"0\",\n" +
                "}";
        mockMvc.perform(post("/REST/lessons/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenLessonURI_whenRoleAdmin_and_incorrectWeekPattern_addLesson_thenResponseBadRequest() throws Exception {
        String cnt = "{\n" +
                "        \"time\": \"TIME6\",\n" +
                "        \"subject\": 5,\n" +
                "        \"teacher\": 9,\n" +
                "        \"group\": 3,\n" +
                "        \"weeks\": \"12-12, 45\",\n" +
                "        \"room\": \"0\",\n" +
                "        \"dayOfWeek\": \"MONDAY\"\n" +
                "}";
        mockMvc.perform(post("/REST/lessons/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenLessonURI_whenRoleRegularOrAnonymous_addLesson_thenResponseForbidden() throws Exception {
        String cnt = "{\n" +
                "        \"time\": \"TIME6\",\n" +
                "        \"subject\": 5,\n" +
                "        \"teacher\": 9,\n" +
                "        \"group\": 3,\n" +
                "        \"weeks\": \"1-12\",\n" +
                "        \"room\": \"0\",\n" +
                "        \"dayOfWeek\": \"MONDAY\"\n" +
                "}";
        mockMvc.perform(post("/REST/lessons/add")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    //Update lesson

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenLessonURI_whenRoleAdmin_updateLesson_thenResponseOK() throws Exception {
        String cnt = "{\n" +
                "    \"time\": \"TIME6\",\n" +
                "    \"subject\": 6,\n" +
                "    \"teacher\": 8,\n" +
                "    \"group\": 5,\n" +
                "    \"weeks\": \"1-12,14\",\n" +
                "    \"room\": \"224\",\n" +
                "    \"dayOfWeek\": \"FRIDAY\"\n" +
                "}";
        mockMvc.perform(put("/REST/lessons/10")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").value("TIME6"))
                .andExpect(jsonPath("$.subject").value(6))
                .andExpect(jsonPath("$.teacher").value(8))
                .andExpect(jsonPath("$.group.group").value(5))
                .andExpect(jsonPath("$.group.type").value("PRACTICE"))
                .andExpect(jsonPath("$.group.value").value(5))
                .andExpect(jsonPath("$.weeks").value("1-12,14"))
                .andExpect(jsonPath("$.room.room").value("224"))
                .andExpect(jsonPath("$.room.type").value("ROOM"))
                .andExpect(jsonPath("$.room.typeOrName").value("224"))
                .andExpect(jsonPath("$.dayOfWeek").value("FRIDAY"));
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenLessonURI_whenRoleAdmin_updateLesson_incorrectBody_thenResponseBadRequest() throws Exception {
        String cnt = "{\n" +
                "    \"time\": \"TIME6\",\n" +
                "    \"subject\": 6,\n" +
                "    \"group\": 5,\n" +
                "    \"weeks\": \"1-12,14\",\n" +
                "    \"room\": \"224\",\n" +
                "}";
        mockMvc.perform(put("/REST/lessons/10")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenLessonURI_whenRoleRegularOrAnonymous_updateLesson_thenResponseForbidden() throws Exception {
        String cnt = "{\n" +
                "    \"time\": \"TIME6\",\n" +
                "    \"subject\": 6,\n" +
                "    \"teacher\": 8,\n" +
                "    \"group\": 5,\n" +
                "    \"weeks\": \"1-12,14\",\n" +
                "    \"room\": \"224\",\n" +
                "    \"dayOfWeek\": \"FRIDAY\"\n" +
                "}";
        mockMvc.perform(put("/REST/lessons/10")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"ADMIN"})
    @Test
    public void givenLessonURI_whenRoleRegularOrAnonymous_updateLesson_thenResponseNotFound() throws Exception {
        String cnt = "{\n" +
                "    \"time\": \"TIME6\",\n" +
                "    \"subject\": 6,\n" +
                "    \"teacher\": 8,\n" +
                "    \"group\": 5,\n" +
                "    \"weeks\": \"1-12,14\",\n" +
                "    \"room\": \"224\",\n" +
                "    \"dayOfWeek\": \"FRIDAY\"\n" +
                "}";
        mockMvc.perform(put("/REST/lessons/1000")
                .content(cnt)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    //Delete lesson by Id

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenLessonURI_whenRoleAdmin_deleteLesson_thenResponseOK() throws Exception {
        mockMvc.perform(delete("/REST/lessons/10")).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "usr", password = "1111", roles = "ADMIN")
    @Test
    public void givenLessonURI_whenRoleAdmin_deleteIncorrectLesson_thenResponseNotFound() throws Exception {
        mockMvc.perform(delete("/REST/lessons/100")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "usr", password = "1111", roles = {"REGULAR","ANONYMOUS"})
    @Test
    public void givenLessonURI_whenRoleRegularOrAnonymous_deleteLesson_thenResponseForbidden() throws Exception {
        mockMvc.perform(delete("/REST/lessons/10")).andDo(print())
                .andExpect(status().isForbidden());
    }



}