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
            //apply offers - b1g1 for apple , b3 pay for 2 for orange
            val offerMap = applyOffer(itemCount)
            //get the total amount for entered items post offer
            offerMap.forEach { (k, v) ->
                total += availableItems.first { item -> item.name == k }.price * (v)
            }

            println("$$total")
        }
    } else {
        println("No Items Entered. Unable to place Order")
    }

    return total
}

//apply offer for apple and orange
fun applyOffer(itemCount: Map<String, Int>): Map<String, Int> {

    return mapOf("apple" to calculateOffer(itemCount["apple"], 2),
        "orange" to calculateOffer(itemCount["orange"], 3))

}
//logic for calculating total items charged post offer
fun calculateOffer(count: Int?, offer: Int): Int {
    var offerCount: Int = 0
    if (count != null) {
        offerCount = if (count < offer) {
            count
        } else {
            count - (count / offer)
        }
    }
    return offerCount;
}


data class Item(val name: String, val price: Double)

