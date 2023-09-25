package eci.arep.services;

import junit.framework.TestCase;

public class MathServiceTest extends TestCase {

    public void testSin() {
        assertEquals( 0, (int) MathService.sin(0));
        assertEquals( 0, (int) MathService.sin(1));
        assertNotSame( 0, MathService.sin(0.9));

    }

    public void testCos() {
        assertEquals( 1,(int) MathService.cos(0));
        assertEquals( 0,(int) MathService.cos(1));
        assertEquals( 0,(int) MathService.cos(2));
    }

    public void testMagnitud() {
        assertEquals(5, (int) MathService.magnitud(3, 4));
        assertEquals(5, (int) MathService.magnitud(4, 3));
        assertEquals(10, (int) MathService.magnitud(8, 6));
    }
}