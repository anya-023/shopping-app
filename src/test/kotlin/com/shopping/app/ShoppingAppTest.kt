package com.shopping.app

import org.junit.jupiter.api.Assertions
import kotlin.test.Test

class ShoppingAppTest {

    @Test
    fun calculatePriceTest() {
        Assertions.assertEquals(2.05, calculatePrice("apple,apple,orange,apple"))
        Assertions.assertEquals(0.00, calculatePrice("apple,apple,stone,apple"))
        Assertions.assertEquals(0.00, calculatePrice(""))
        Assertions.assertEquals(2.05, calculatePrice("apple,apple,ORANGE,apple"))
    }

}