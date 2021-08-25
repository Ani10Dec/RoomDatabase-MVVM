package com.example.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SubscriberEntity::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase() {
    abstract val getSubscriberDao: SubscriberDao

    companion object {
        @Volatile // To visible throughout the application
        private var INSTANCE: SubscriberDatabase? = null
        fun getDatabaseInstance(context: Context): SubscriberDatabase {
            synchronized(this) {   //TO initiate only once
                var instances = INSTANCE
                if (instances == null) {
                    instances = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDatabase::class.java,
                        "Subscriber_db"
                    ).build()
                }
                return instances
            }
        }

    }
}
