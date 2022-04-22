package com.example.onlineshopping;

import com.example.onlineshopping.model.AppUser;
import com.example.onlineshopping.model.AppUserRole;
import com.example.onlineshopping.model.Items;
import com.example.onlineshopping.model.Order;
import com.example.onlineshopping.service.OrderService;
import com.example.onlineshopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class AmangeldiOnlineShoppingApplication implements CommandLineRunner {
    final UserService userService;
    final OrderService orderService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    public static void main(String[] args) {
        SpringApplication.run(AmangeldiOnlineShoppingApplication.class, args);
    }
    @Override
    public void run(String... params) throws Exception {
        AppUser admin = new AppUser("Admin Adminbekuly",500000L,"admin","admin1234", List.of(AppUserRole.ROLE_ADMIN));
        userService.signup(admin);
        AppUser customer = new AppUser("Customer Userbekuly",400000L,"customer","customer123", List.of(AppUserRole.ROLE_ADMIN));
        userService.signup(customer);

        Order order1= Order.builder().id(1).orderDate(LocalDateTime.now()).isPaid(true).isDelivered(false).user(admin).totalPrice(349000L).build();
        Order order2= Order.builder().id(2).orderDate(LocalDateTime.now()).isPaid(true).isDelivered(true).user(customer).totalPrice(250000L).build();

        orderService.saveOrder(order1);
        orderService.saveOrder(order2);

        Items item1 = Items.builder().id(1).category("Smartphone").name("Iphone X 64GB").price(250000L).order(order2).build();
        Items item2 = Items.builder().id(2).category("Smartphone").name(" Samsung Galaxy Note 8 64GB").price(349000L).order(order1).build();
        orderService.saveItem(item1);
        orderService.saveItem(item2);

    }

}
