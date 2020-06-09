package com.demo.exceptions;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class DataNotFoundException extends RuntimeException {



    public DataNotFoundException(String message) {
        super(message);
    }
}
