package com.shopping.app

import org.junit.jupiter.api.Assertions
import kotlin.test.Test

class ShoppingAppTest {

   /* @Test
    fun calculatePriceTest() {
        Assertions.assertEquals(2.05, calculatePrice("apple,apple,orange,apple"))
        Assertions.assertEquals(0.00, calculatePrice("apple,apple,stone,apple"))
        Assertions.assertEquals(0.00, calculatePrice(""))
        Assertions.assertEquals(2.05, calculatePrice("apple,apple,ORANGE,apple"))
    }*/


    @Test
    fun offerPriceTest() {
        Assertions.assertEquals(1.45, calculatePrice("apple,apple,orange,apple"))
        Assertions.assertEquals(0.00, calculatePrice("apple,apple,stone,apple"))
        Assertions.assertEquals(0.00, calculatePrice(""))
        Assertions.assertEquals(1.6, calculatePrice("apple,apple,ORANGE,orange,orange,orange,orange,orange"))
        Assertions.assertEquals(.85, calculatePrice("apple,orange"))
        Assertions.assertEquals(1.1, calculatePrice("apple,ORANGE,orange"))
        Assertions.assertEquals(6.75, calculatePrice("orange,orange,orange,orange,orange," +
                "orange,orange,orange,orange,orange,orange,orange," +
                "orange,orange,orange,orange,orange,orange,orange,orange,orange," +
                "orange,orange,orange,orange,orange,orange,orange,orange,orange,orange,orange," +
                "orange,orange,orange,orange,orange,orange,orange,orange"))
        Assertions.assertEquals(4.20, calculatePrice("apple,apple,apple,apple,apple,apple,apple,apple,apple,apple,apple,apple,apple"))
    }

}