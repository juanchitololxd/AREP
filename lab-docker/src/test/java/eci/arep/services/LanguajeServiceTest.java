package eci.arep.services;

import junit.framework.TestCase;

public class LanguajeServiceTest extends TestCase {

    public void testIsPalindromo() {
        assertTrue(LanguajeService.isPalindromo("reconocer"));
        assertTrue(LanguajeService.isPalindromo("t"));
        assertTrue(LanguajeService.isPalindromo(""));
        assertFalse(LanguajeService.isPalindromo("xd"));
        assertFalse(LanguajeService.isPalindromo("monitor no mira esto"));
    }
}