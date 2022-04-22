package com.example.onlineshopping.controller;

import com.example.onlineshopping.model.Order;
import com.example.onlineshopping.service.OrderService;
import com.example.onlineshopping.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class OrderControllerTest {
    final  String USERNAME="admin";
    final  String PASSWORD="admin1234";

    String token;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrderService orderService;
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
        mockMvc.perform(get("/orders/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    void testGetById() throws Exception{
        mockMvc.perform(get("/orders/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    void testDeleteById() throws Exception{
        mockMvc.perform(get("/orders/delete/"+1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
    @Test
    void testSave() throws Exception{
        Order order = Order.builder().id(4).orderDate(LocalDateTime.now()).totalPrice(0L).isPaid(true).isDelivered(true).user(null).build();
        mockMvc.perform(get("/orders/")
                        .content(new Gson().toJson(order))
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testPayOrder() throws Exception {
        mockMvc.perform(get("/orders/payOrder/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testOrderStatus() throws Exception {
        mockMvc.perform(get("/orders/status/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}

