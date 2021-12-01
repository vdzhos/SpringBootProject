package com.sbproject.schedule.teacher;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration
@AutoConfigureMockMvc
//@WebMvcTest(SpecialtyRestController.class)
public class TeacherSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
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
}