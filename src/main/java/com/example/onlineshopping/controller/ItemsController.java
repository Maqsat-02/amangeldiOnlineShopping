package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.UserResponseDTO;
import com.example.onlineshopping.model.Items;
import com.example.onlineshopping.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    OrderService itemsService;

    @PostMapping(value = "",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${ItemsController.save}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public Items save(@RequestBody Items items){
        return itemsService.saveItem(items);
    }

    @GetMapping(value = "",
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ItemsController.getAll}", response = Items.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public List<Items> getAll(HttpServletRequest request){
        return itemsService.getAllItems();
    }

    @GetMapping(value = "/{id}",
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ItemsController.getById}", response = Items.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public Items getById(@PathVariable int id,HttpServletRequest request) {
        return itemsService.getItemById(id).get();
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${ItemsController.deleteById}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The url doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public boolean deleteById(@PathVariable int id,HttpServletRequest request){
        return itemsService.deleteItemById(id);
    }
}
