package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleStatusServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_OK);
            
            PrintWriter writer = response.getWriter();
            writer.write("Servlet Alive");
            writer.flush();
        } catch (IOException e) {
            throw new IOException("Servlet writer failed", e);
        }
    }
}