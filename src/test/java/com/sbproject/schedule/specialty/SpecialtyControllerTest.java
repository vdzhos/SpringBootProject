//package com.sbproject.schedule.specialty;
//
//import com.sbproject.schedule.controllers.rest.SpecialtyRestController;
//import com.sbproject.schedule.exceptions.handlers.SpecialtyRestControllerExceptionHandler;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
////@RunWith(SpringRunner.class)
//@WebAppConfiguration
//@SpringBootTest
//@AutoConfigureMockMvc
//@WebMvcTest(SpecialtyRestController.class)
//public class SpecialtyControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
////
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//
//    // ... other methods
//
////    @Before
////    public void setup() throws Exception {
////        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
////        System.out.println(mvc);
////    }
//
////
////    @WithMockUser(roles = "ADMIN")
////    @Test
////    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
////        mvc.perform(get("/private/hello").contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk());
////    }
//
////    @WithMockUser(roles = "ADMIN")
////    @Test
////    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
////        Assertions.assertNotNull(new Object());
//////        mvc.perform(get("/login")).andDo(print())
//////                .andExpect(view().name("login"));
////    }
//
//
//
//
//    @Test
//    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
//        System.out.println(mvc);
////        mvc.perform(get("/login")).andDo(print())
////                .andExpect(view().name("login"));
//    }
//
//}