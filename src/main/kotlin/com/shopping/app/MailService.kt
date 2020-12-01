package com.shopping.app

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*


object MailService {

    fun registerListeners() {
        EventBus.getDefault().register(this)
    }

    fun deregisterListeners() {
        EventBus.getDefault().unregister(this)
    }


    @Subscribe
    fun orderSubmittedListener(event: OrderSubmitted) {
        println("Your ${event.status} with order Id ${event.orderId}. Total amount to be paid is $${event.total} . Expected delivery date is ${event.expectedDeliveryDate} .")
        publishToKafka(event, "tpc-order-submitted")
    }

    @Subscribe
    fun orderFailedListener(event: OrderFailed) {
        println(event.status)
        publishToKafka(event, "tpc-order-failed")

    }

    private fun publishToKafka(event: Any, topic: String) {
        val producer = createProducer()
        producer.use { producer ->
            val record: ProducerRecord<String, String> = ProducerRecord<String, String>(topic, event.toString())
            producer.send(record)
        }

    }

    private fun createProducer(): KafkaProducer<String, String> {
        val props = Properties()
        //assuming kafka is running locally for the use case
        props["bootstrap.servers"] = "localhost:9092"
        props["acks"] = "all"
        props["retries"] = 0
        props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        props["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        return KafkaProducer<String, String>(props)
    }
}






