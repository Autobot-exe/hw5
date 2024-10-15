package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void check() {
        assertEquals(-1.0, homeWork.calculate("1 + 2 * ( 3 - 4 )"), 0.001);
        assertEquals(14.29, homeWork.calculate("sqr(3) + pow(2.3)"), 0.001);
        assertEquals(0.03893341233723022, homeWork.calculate("cos(61.3)"), 0.001);
    }

}