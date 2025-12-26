package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DemoSystemTest {
    
    @Test
    void contextLoads() {
        // Basic test to verify Spring context loads
        assertTrue(true);
    }
    
    @Test
    void basicArithmeticTest() {
        // Simple test to verify JUnit works
        assertEquals(4, 2 + 2, "2 + 2 should equal 4");
        assertNotEquals(5, 2 + 2, "2 + 2 should not equal 5");
    }
    
    @Test
    void stringTest() {
        String message = "Hello, World!";
        assertNotNull(message);
        assertEquals("Hello, World!", message);
    }
}