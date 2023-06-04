package com.example.todo.repository

import com.example.todo.dao.TodoDAO
import com.example.todo.modal.Todo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoRepo(
    private val dao:TodoDAO,
    private val ioDispatcher: CoroutineDispatcher
    ){
    private val todoScope = CoroutineScope(ioDispatcher)

    fun getAllToDo():List<Todo>{
        return dao.getAllTodo()
    }

    suspend fun InsertToDo(todo:Todo){
        todoScope.launch {
            dao.InsertTODO(todo)
        }
    }

    suspend fun UpdateTODO(todo: Todo){
        todoScope.launch {
            dao.updateTODO(todo)
        }
    }

    suspend fun deleteToDo(todo: Todo){
        todoScope.launch {
            dao.deleteTODO(todo)
        }
    }
    suspend fun getToDoById(id:Int):Todo{
        var getToDo :Todo
        withContext(ioDispatcher){
            getToDo = dao.getToDoById(id)
        }
        return getToDo
    }
}