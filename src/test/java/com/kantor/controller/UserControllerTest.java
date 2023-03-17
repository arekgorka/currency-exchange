package com.kantor.controller;

import com.google.gson.Gson;
import com.kantor.domain.dto.UserDto;
import com.kantor.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String URL_USER = "/v1/currency_exchange/users";
    private final UserDto userDto = new UserDto(1L,"Michael","Jackson","mj2010","Mjmj","mj2010@gmail.com");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(URL_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUser() throws Exception {
        //Given
        when(userService.getUser(1L)).thenReturn(userDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URL_USER + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.id", Matchers.is(1)),
                        jsonPath("$.firstname",Matchers.is("Michael")),
                        jsonPath("$.lastname",Matchers.is("Jackson")),
                        jsonPath("$.login",Matchers.is("mj2010")),
                        jsonPath("$.password",Matchers.is("Mjmj")),
                        jsonPath("$.mail",Matchers.is("mj2010@gmail.com")));
    }
}
