package com.example.onlineshopping.service;

import com.example.onlineshopping.model.AppUser;
import com.example.onlineshopping.model.AppUserRole;
import com.example.onlineshopping.model.Items;
import com.example.onlineshopping.model.Order;
import com.example.onlineshopping.repository.ItemsRepository;
import com.example.onlineshopping.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//@SpringJUnitConfig(AOPconfig.class)
class OrderServiceTest {
    @Mock
    ItemsRepository itemsRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    Logger log;
    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetOrderById(){
        Order expected = Order.builder().id(1).build();
        when(orderService.getOrderById(1)).thenReturn(Optional.of(expected));
        Order actual = orderService.getOrderById(1).get();
        Assertions.assertEquals(actual, expected);
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    public void testGetItemById()  {
        Items expected = Items.builder().id(1).build();
        when(orderService.getItemById(1)).thenReturn(Optional.of(expected));
        Items actual = orderService.getItemById(1).get();
        Assertions.assertEquals(actual, expected);
        verify(itemsRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllOrder(){
        Order order1= Order.builder().id(1).build();
        Order order2 = Order.builder().id(2).build();
        given(orderService.getAllOrders()).willReturn(asList(order1, order2));

        //When
        List<Order> allActual = orderService.getAllOrders();

        //Then
        assertThat(allActual).containsExactly(order1, order2);
    }
    @Test
    void testGetAllItems(){
        Items items1= Items.builder().id(1).build();
        Items items2 = Items.builder().id(2).build();
        given(orderService.getAllItems()).willReturn(asList(items1, items2));

        //When
        List<Items> allActual = orderService.getAllItems();

        //Then
        assertThat(allActual).containsExactly(items1, items2);
    }

    @Test
    void testDeleteOrderById() {
        Order expected = Order.builder().id(1).build();
        when(orderService.getOrderById(1)).thenReturn(Optional.of(expected));
        Order actual = orderService.getOrderById(1).get();

        Assertions.assertTrue(orderService.deleteOrderById(1));

        verify(orderRepository, times(1)).deleteById(1);
    }
    @Test
    void testDeleteItemById() {

        Assertions.assertTrue(orderService.deleteItemById(1));

        verify(itemsRepository, times(1)).deleteById(1);
    }

    @Test
    void testSaveItem(){
        Items item = new Items(123, "Samsung Galaxy M33 5G", 789L, "Smartphones",null);
        when(orderService.saveItem(Mockito.any(Items.class))).thenReturn(item);
        orderService.saveItem(item);
    }
    @Test
    void testSaveOrder(){
        Order order = new Order(123, LocalDateTime.now(),8L,true,false,null,null);
        when(orderService.saveOrder(Mockito.any(Order.class))).thenReturn(order);
        orderService.saveOrder(order);
    }
    @Test
    void testOrderStatus(){
        AppUser user = new AppUser("Dr Jane Doe", 42L,"janee","789465321", Arrays.asList(AppUserRole.ROLE_ADMIN));
        Order order = new Order(478, LocalDateTime.now(),8L,true,false,user,null);
//        when(orderService.orderStatus(order)).thenReturn("Please wait delivery!");
//        orderService.orderStatus(order);
        Assertions.assertEquals( orderService.orderStatus(order),"Please wait delivery!");
    }
//    @Test
//    void testPayForOrder(){
//        User user = new User(123, "Dr Jane Doe", "42 Main St", 42L,null);
//        Order order = new Order(478, LocalDateTime.now(),8L,true,false,user,null);
//        user.setOrder(order);
//        when(orderService.payForOrder(order)).thenReturn(true);
//        orderService.payForOrder(order);
//        Assertions.assertTrue(orderService.saveOrder(order));
//        Assertions.assertTrue(orderService.payForOrder(order));
//    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme