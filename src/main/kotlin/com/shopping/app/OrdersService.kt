package com.shopping.app

import org.greenrobot.eventbus.EventBus
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

fun main(args: Array<String>) {
    MailService.registerListeners()
    println("Enter items to purchase separated by commas:")
    //read line from command line
    val items = Scanner(System.`in`).nextLine()
    calculatePrice(items)
    MailService.deregisterListeners()
}

fun calculatePrice(itemsStr: String): Double {
    val availableItems = arrayListOf(Item("orange", 0.25, 2), Item("apple", 0.60, 3))
    var total = 0.00
    var allItemsInStock = true;
    if (itemsStr != "") {
        //convert to lowercase and split into individual items
        val items = itemsStr.toLowerCase().split(',').toList()
        //check if there are invalid items (other than apple and orange)
        if (!items.all(availableItems.map { it.name }::contains)) {
            //println("There are invalid items in the order")
            EventBus.getDefault()
                .post(OrderFailed("There are invalid items in the order.Unable to place order successfully."))
        } else {
            //get count of each item entered
            val itemCount = items.groupingBy { it }.eachCount()
            for (entry in itemCount) {
                if (availableItems.first { it.name == entry.key }.stockCount < entry.value) {
                    EventBus.getDefault()
                        .post(OrderFailed("Stock is limited for ${entry.key}.Unable to place order successfully."))
                    allItemsInStock = false;
                    break;
                }
            }
            if (allItemsInStock) {
                //apply offers - b1g1 for apple , b3 pay for 2 for orange
                val offerMap = applyOffer(itemCount)
                //get the total amount for entered items post offer
                offerMap.forEach { (k, v) ->
                    total += availableItems.first { item -> item.name == k }.price * (v)
                }

                //println("$$total")
                EventBus.getDefault().post(OrderSubmitted(Random().nextInt(100) + 1,
                    "Order Placed Successfully",
                    LocalDate.now().plus(2, ChronoUnit.DAYS),
                    total))
            }
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
    var offerCount = 0
    if (count != null) {
        offerCount = if (count < offer) {
            count
        } else {
            count - (count / offer)
        }
    }
    return offerCount
}


data class Item(val name: String, val price: Double, var stockCount: Int)

data class OrderSubmitted(val orderId: Int, val status: String, val expectedDeliveryDate: LocalDate, val total: Double)

data class OrderFailed(val status: String)