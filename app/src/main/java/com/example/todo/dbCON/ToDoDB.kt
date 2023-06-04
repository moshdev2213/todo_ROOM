package com.example.todo.dbCON

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.dao.TodoDAO
import com.example.todo.modal.Todo

@Database(
    entities = [Todo::class],
    version =1,
    exportSchema = false
)
abstract class ToDoDB:RoomDatabase() {
    abstract fun TodoDAO():TodoDAO

    companion object{

        @Volatile
        private var INSTANCE:ToDoDB?=null

        fun getInstance(context: Context):ToDoDB{
            synchronized(this){
                var instan = INSTANCE
                if(instan==null){
                    instan = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoDB::class.java,
                        "ToDo"
                    ).build()
                }
                return instan
            }
        }
    }
}