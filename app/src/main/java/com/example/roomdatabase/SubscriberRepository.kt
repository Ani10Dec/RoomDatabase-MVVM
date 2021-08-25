package com.example.roomdatabase

import com.example.roomdatabase.database.SubscriberDao
import com.example.roomdatabase.database.SubscriberEntity

class SubscriberRepository(private val dao: SubscriberDao) {

    val allSubscriber = dao.getAllSubscriber()

    suspend fun insertSubscriber(subscriberEntity: SubscriberEntity) {
        dao.insertSubscriber(subscriberEntity)
    }

    suspend fun updateSubscriber(subscriberEntity: SubscriberEntity) {
        dao.updateSubscriber(subscriberEntity)
    }

    suspend fun deleteSubscriber(subscriberEntity: SubscriberEntity) {
        dao.deleteSubscriber(subscriberEntity)
    }

    suspend fun deleteAllSubscriber() {
        dao.deleteAllSubscriber()
    }

}