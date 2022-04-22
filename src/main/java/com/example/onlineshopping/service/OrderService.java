package com.example.onlineshopping.service;


import com.example.onlineshopping.model.Items;
import com.example.onlineshopping.model.Order;
import com.example.onlineshopping.repository.ItemsRepository;
import com.example.onlineshopping.repository.OrderRepository;
import com.example.onlineshopping.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("orderService")
@NoArgsConstructor
public class OrderService {

    ItemsRepository itemsRepository;

    OrderRepository orderRepository;


    @Autowired
    public OrderService(ItemsRepository itemsRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.itemsRepository = itemsRepository;
        this.orderRepository = orderRepository;
    }
    @Transactional(readOnly = true)
    public List<Items> getAllItems() {
       return itemsRepository.findAll();
   }
    @Transactional(readOnly = true)
    public Optional<Items> getItemById(int id) {
       return itemsRepository.findById(id);

    }
    @Transactional( propagation = Propagation.SUPPORTS,rollbackFor = SQLException.class)
    public Items saveItem(Items item) {
        return itemsRepository.saveAndFlush(item);
    }
    @Transactional( propagation = Propagation.SUPPORTS,rollbackFor = SQLException.class)
    public boolean deleteItemById(int id) {
        try {
            itemsRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Error while deleting with id  {}",id);
            e.printStackTrace();
        }
        return false;
    }
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return  orderRepository.findAll();
   }
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
   }
    @Transactional( propagation = Propagation.SUPPORTS,rollbackFor = SQLException.class)
    public Order saveOrder(Order order) {
        return orderRepository.saveAndFlush(order);
   }
    @Transactional( propagation = Propagation.SUPPORTS,rollbackFor = SQLException.class)
    public boolean deleteOrderById(int id){
        try {
            orderRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Error while deleting Order with id  {}",id);
            e.printStackTrace();
        }
        return false;
    }
    @Transactional( propagation = Propagation.SUPPORTS,readOnly = true )
    public String orderStatus(Order order) {
//        Order order= orderRepository.getById(id);
        if(order.getIsPaid() && order.getIsDelivered()) {
            return "Your order was delivered!!";
        }
        else if(!order.getIsDelivered()){
            return "Please wait delivery!";
        }
        else
            return "Please, pay first for order!!";
    }

    public boolean payForOrder(Order order){

        if(order.getTotalPrice()>order.getUser().getBalance())
            return false;
        else {
            order.setIsPaid(true);
            order.getUser().setBalance(order.getUser().getBalance()-order.getTotalPrice());
            orderRepository.save(order);
            //            userRepository.save(order.getUser());
            return true;
        }
    }


}
