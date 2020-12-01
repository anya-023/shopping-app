package com.shopping.app

import java.util.*

fun main(args: Array<String>) {
    println("Enter items to purchase separated by commas:")
    //read line from command line
    val items = Scanner(System.`in`).nextLine()
    calculatePrice(items)
}

fun calculatePrice(itemsStr: String): Double {
    val availableItems = arrayListOf(Item("orange", 0.25), Item("apple", 0.60))
    var total = 0.00
    if (itemsStr != "") {
        //convert to lowercase and split into individual items
        val items = itemsStr.toLowerCase().split(',').toList()
        //check if there are invalid items (other than apple and orange)
        if (!items.all(availableItems.map { it.name }::contains)) {
            println("There are invalid items in the order")
        } else {
            //get count of each item entered
            val itemCount = items.groupingBy { it }.eachCount()

            //get the total amount for entered items
            itemCount.forEach { (k, v) ->
                total += availableItems.first { item -> item.name == k }.price * (v)
            }

            println("$$total")
        }
    } else {
        println("No Items Entered. Unable to place Order")
    }

    return total
}


data class Item(val name: String, val price: Double)

