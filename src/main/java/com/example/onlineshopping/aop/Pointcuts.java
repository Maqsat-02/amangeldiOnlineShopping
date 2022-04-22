package com.example.onlineshopping.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution (* com.example.onlineshopping.controller.*.getById(..))")
    public void allGetByIDMethods(){}

    @Pointcut("execution (* com.example.onlineshopping.controller.*.getAll*(..))")
    public void allGetAllMethods(){}

    @Pointcut("execution(* com.example.onlineshopping.controller.*.save*(..))")
    public void allSaveMethods(){};

    @Pointcut("execution (* com.example.onlineshopping.controller.*.getAll(..))")
    public void allGetControllerMethods(){}

}
