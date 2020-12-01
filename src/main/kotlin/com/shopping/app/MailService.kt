package com.shopping.app

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


object MailService{

    fun registerListeners(){
        EventBus.getDefault().register(this)
    }

    fun deregisterListeners(){
        EventBus.getDefault().unregister(this)
    }


    @Subscribe
    fun orderSubmittedListener(event: OrderSubmitted){
        println("Your ${event.status} with order Id ${event.orderId}. Total amount to be paid is $${event.total} . Expected delivery date is ${event.expectedDeliveryDate} .")
    }

    @Subscribe
    fun orderSubmittedListener(event: OrderFailed){
        println(event.status)
    }


}






