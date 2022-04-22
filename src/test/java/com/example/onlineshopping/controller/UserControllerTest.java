package com.example.onlineshopping.controller;

import com.example.onlineshopping.controller.UserController;
import com.example.onlineshopping.dto.UserDataDTO;
import com.example.onlineshopping.dto.UserResponseDTO;
import com.example.onlineshopping.model.AppUserRole;
import com.example.onlineshopping.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class UserControllerTest {
    final  String USERNAME="admin";
    final  String PASSWORD="admin1234";
    String token;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;
    @InjectMocks
    UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        token = userService.signin(USERNAME,PASSWORD);
    }

    @Test
    void testMe() throws Exception {
        Assertions.assertNotNull(token);
        log.info("jwt token {}",token);
        mockMvc.perform(get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testSignup() throws Exception {
        Assertions.assertNotNull(token);
        log.info("jwt token {}",token);
        UserDataDTO user = new UserDataDTO("client","clien@mail.ru","78912345", Arrays.asList(AppUserRole.ROLE_CLIENT));
        mockMvc.perform(post("/users/signup").content(new Gson().toJson(user))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        Assertions.assertNotNull(token);
        log.info("jwt token {}",token);
        mockMvc.perform(delete("/users/delete/client")
//                        .content("client")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    void testSearch() throws Exception {
        Assertions.assertNotNull(token);
        log.info("jwt token {}",token);
        MvcResult result=mockMvc.perform(get("/users/"+USERNAME)
                        .header("Authorization", "Bearer " + token))
                .andReturn();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getResponse().getContentAsString().contains(USERNAME));
    }

    @Test
    void testWhoami() throws Exception {
        HttpServletRequest request=mock(HttpServletRequest.class);
        Assertions.assertNotNull(token);
        log.info("jwt token {}",token);
        MvcResult result=mockMvc.perform(get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andReturn();
        Assertions.assertNotNull(result);
//        verify(userService,times(1)).whoami(request);
    }

    @Test
    void testRefresh() throws Exception {
        mockMvc.perform(get("/users/refresh")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/users/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    void testGetById() throws Exception{
        mockMvc.perform(get("/users/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme