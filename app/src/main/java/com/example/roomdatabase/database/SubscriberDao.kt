package com.example.roomdatabase.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriberEntity: SubscriberEntity): Long

    @Delete
    suspend fun deleteSubscriber(subscriberEntity: SubscriberEntity)

    @Update
    suspend fun updateSubscriber(subscriberEntity: SubscriberEntity)

    @Query(value = "DELETE FROM subscriber_dataTable")
    suspend fun deleteAllSubscriber()

    @Query(value = "SELECT * FROM subscriber_dataTable")
    fun getAllSubscriber(): LiveData<List<SubscriberEntity>>
}