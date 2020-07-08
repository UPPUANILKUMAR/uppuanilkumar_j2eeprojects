package com.junit;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;		

public class SuiteTest1 {				

    public String message = "Saurabh";							

    JUnitMessage junitMessage = new JUnitMessage(message);							

    @Before
    public void pblic() {
    	System.out.println("Before Each");	
		// TODO Auto-generated method stub

	}
    
    @Test//(expected = ArithmeticException.class)					
    public void testJUnitMessage() {					

        System.out.println("Junit Message is printing ");					
        junitMessage.printMessage();			

    }		

    @Test		
    public void testJUnitHiMessage() {					
        message = "Hi!" + message;							
        System.out.println("Junit Hi Message is printing ");					
        assertEquals(message, junitMessage.printHiMessage());					
        System.out.println("Suite Test 2 is successful " + message);							
    }		
}		
