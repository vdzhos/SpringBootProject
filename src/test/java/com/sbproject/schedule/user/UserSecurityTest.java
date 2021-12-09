package com.sbproject.schedule.user;

import com.sbproject.schedule.repositories.UserRepository;
import com.sbproject.schedule.utils.Role;
import org.junit.jupiter.api.Assertions;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.junit.Before;
//import org.junit.runner.RunWith;


//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserSecurityTest {

	
 	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository repo;
    
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        System.out.println(mockMvc);
    }
    
    @WithMockUser(username = "vovan", password = "1234", roles = "ADMIN")
    @Test
    public void givenGETLoginURI_whenLoggedUser_thenResponseIsLoggedUser() throws Throwable 
    {
    	mockMvc.perform(get("/restLogin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("vovan"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }
    
    
    @Test
    public void givenPOSTLoginURI_whenCorrectUserData_thenResponseIsOk() throws Throwable
    {
    	String cnt = "{\"login\": \"gleb\", \"password\": \"0000\", \"matchingPassword\" : \"0000\", \"roleCode\": \"devadmincode\"}";
    	mockMvc.perform(post("/restLogin/newuser")
    			.content(cnt)
    			.contentType(MediaType.APPLICATION_JSON))
    			.andDo(print())
    			.andExpect(status().isCreated());
    	assertThat(repo.findById("gleb").get())
    	.hasFieldOrPropertyWithValue("login", "gleb")
    	.hasFieldOrPropertyWithValue("password", "0000")
    	.hasFieldOrPropertyWithValue("role", Role.ADMIN);
    }
    
    @Test
    public void givenPOSTLoginURI_whenInvalidPassword_thenBadRequest() throws Throwable
    {
    	String cnt = "{\"login\": \"gleb\", \"password\": \"000\", \"matchingPassword\" : \"0000\", \"roleCode\": \"devadmincode\"}";
    	mockMvc.perform(post("/restLogin/newuser")
    			.content(cnt)
    			.contentType(MediaType.APPLICATION_JSON))
    			.andDo(print())
                .andExpect(status().isBadRequest());
    }
    
    @WithMockUser(username = "ilya", password = "4321", roles = "REGULAR")
    @Test
    public void givenDELETELoginURI_whenLoggedUser_thenLoggedUserDeleted() throws Throwable
    {
    	mockMvc.perform(delete("/restLogin"))
    	.andDo(print())
        .andExpect(status().isOk());
    	Assertions.assertTrue(this.repo.findById("ilya").isEmpty());
    }
    
    @WithMockUser(username = "vovan", password = "1234", roles = "ADMIN")
    @Test
    public void givenPUTLoginURI_whenLoggedUserAndValidNewPassword_thanPasswordChanged() throws Throwable
    {
    	  mockMvc.perform(put("/restLogin/passupdate")
    			  .param("newPassword", "1010"))
                  .andExpect(status().isOk());
    	  assertThat(repo.findById("vovan").get())
    	  .hasFieldOrPropertyWithValue("password", "1010");
    }
    
    @WithMockUser(username = "vovan", password = "1234", roles = "ADMIN")
    @Test
    public void givenPUTLoginURI_whenLoggedUserAndInvalidNewPassword_thanBadRequest() throws Throwable
    {
    	  mockMvc.perform(put("/restLogin/passupdate")
    			  .param("newPassword", "10"))
                  .andExpect(status().isBadRequest());
    }
    
}
