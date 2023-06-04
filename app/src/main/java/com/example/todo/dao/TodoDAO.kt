package com.example.todo.dao

import androidx.room.*
import com.example.todo.modal.Todo

@Dao
interface TodoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertTODO(todo: Todo)

    @Update
    suspend fun updateTODO(todo: Todo)

    @Delete
    suspend fun deleteTODO(todo: Todo)

    @Query("SELECT * FROM ToDo")
    fun getAllTodo():List<Todo>

    @Query("SELECT * FROM ToDo WHERE id=:id")
    fun getToDoById(id:Int):Todo

}