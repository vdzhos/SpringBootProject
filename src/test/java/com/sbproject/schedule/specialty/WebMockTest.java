//package com.sbproject.schedule.specialty;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.sbproject.schedule.controllers.rest.SpecialtyRestController;
//import com.sbproject.schedule.models.Specialty;
//import com.sbproject.schedule.services.interfaces.SpecialtyService;
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//
//@WebMvcTest(SpecialtyRestController.class)
//public class WebMockTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SpecialtyService service;
//
//    @Test
//    public void greetingShouldReturnMessageFromService() throws Exception {
//        System.out.println("Hello");
////        when(service.getAll()).thenReturn(Arrays.asList(new Specialty("Spec",2), new Specialty("Spec1",3)));
////        this.mockMvc.perform(get("/specialties")).andDo(print()).andExpect(status().isOk())
////                .andExpect(content().string(containsString("Hello, Mock")));
//    }
//}