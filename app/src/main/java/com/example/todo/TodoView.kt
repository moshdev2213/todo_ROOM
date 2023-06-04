package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.todo.dbCON.ToDoDB
import com.example.todo.modal.Todo
import com.example.todo.repository.ToDoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoView : AppCompatActivity() {
    private lateinit var etToDo:EditText
    private lateinit var btnDLT:Button
    private lateinit var btnEdit:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_view)

        btnDLT = findViewById(R.id.btnDLT)
        btnEdit = findViewById(R.id.btnEdit)
        etToDo = findViewById(R.id.etToDo)

        val bundle = intent.extras
        val todo = bundle?.getSerializable("todo") as? Todo
        if(todo!=null){
            etToDo.setText(todo.note)

            btnDLT.setOnClickListener {
                startActivity(Intent(this@TodoView,MainActivity::class.java))
            }
            btnEdit.setOnClickListener {
                editNewTodo(Todo(
                    todo.id,
                    etToDo.text.toString()
                ))
            }
        }else{
            btnDLT.setText("Back")
            btnDLT.setOnClickListener {
                startActivity(Intent(this@TodoView,MainActivity::class.java))
            }
            btnEdit.setText("Add")
            btnEdit.setOnClickListener {
               if(etToDo.text.toString().isNotEmpty()){
                   addNewTodo(Todo(
                       0,
                       etToDo.text.toString()
                   ))
                   etToDo.setText("")
               }
            }
        }
    }

    private fun addNewTodo(todo: Todo) {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = ToDoDB.getInstance(this@TodoView)
            val todoDao = db.TodoDAO()
            val todoRepo = ToDoRepo(todoDao, Dispatchers.IO)

            todoRepo.InsertToDo(todo)
        }
    }
    private fun editNewTodo(todo: Todo) {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = ToDoDB.getInstance(this@TodoView)
            val todoDao = db.TodoDAO()
            val todoRepo = ToDoRepo(todoDao, Dispatchers.IO)

            todoRepo.UpdateTODO(todo)
        }
    }
}