package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.UserResponseDTO;
import com.example.onlineshopping.model.Order;
import com.example.onlineshopping.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(value = "",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${OrderController.save}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public Order save(@RequestBody Order order, HttpServletRequest request){
        return orderService.saveOrder(order);
    }

    @GetMapping(value = "payOrder/{id}",
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${OrderController.save}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String payOrder(@PathVariable int id,HttpServletRequest request){
        Order order= orderService.getOrderById(id).get();
        if(orderService.payForOrder(order))
            return "Your order has been successfully paid for";
        return "Error!";

    }
    @GetMapping(value = "status/{id}",
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "${OrderController.save}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String orderStatus(@PathVariable int id,HttpServletRequest request){
        Order order= orderService.getOrderById(id).get();
        return orderService.orderStatus(order);
    }
    @GetMapping(value = "",
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${OrderController.getAll}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public List<Order> getAll(HttpServletRequest request){
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/{id}",
            produces = {"application/json", "application/xml"})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${OrderController.getById}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public Order getById( int id,HttpServletRequest request){
        return orderService.getOrderById(id).get();
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${OrderController.deletById}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public boolean deleteById(@PathVariable int id, HttpServletRequest request ){
        return orderService.deleteOrderById(id);
    }

}
