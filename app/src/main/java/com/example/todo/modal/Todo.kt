package com.example.todo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var note:String
):java.io.Serializable
