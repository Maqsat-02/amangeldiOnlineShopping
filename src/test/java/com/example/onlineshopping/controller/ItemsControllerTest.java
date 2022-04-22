package com.example.onlineshopping.controller;

import com.example.onlineshopping.service.OrderService;
import com.example.onlineshopping.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ItemsControllerTest {
    final  String USERNAME="admin";
    final  String PASSWORD="admin1234";

    String token;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrderService itemsService;
    @Autowired
    UserService userService;
    @InjectMocks
    ItemsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        token = userService.signin(USERNAME,PASSWORD);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/items/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    void testGetById() throws Exception{
        mockMvc.perform(get("/items/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    void testDeleteById() throws Exception{
        mockMvc.perform(get("/items/delete/"+1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
