package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.adapter.AddTODOAdapter
import com.example.todo.dao.TodoDAO
import com.example.todo.dbCON.ToDoDB
import com.example.todo.modal.Todo
import com.example.todo.repository.ToDoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var rvTODO:RecyclerView
    private lateinit var btnADDTODO :Button
    private lateinit var adapter: AddTODOAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnADDTODO = findViewById(R.id.btnADDTODO)
        btnADDTODO.setOnClickListener {
            startActivity(Intent(this@MainActivity,TodoView::class.java))

        }
        rvTODO = findViewById(R.id.rvTODO)
        initRecycerView()
    }
    private fun initRecycerView(){
        lifecycleScope.launch (Dispatchers.IO){
            val db = ToDoDB.getInstance(this@MainActivity)
            val todoDAO = db.TodoDAO()
            val todoRepo = ToDoRepo(todoDAO,Dispatchers.IO)
            rvTODO.layoutManager= LinearLayoutManager(this@MainActivity)
            adapter = AddTODOAdapter(this@MainActivity){
                    selectedItem:Todo->listItemClicked(selectedItem)
            }
            rvTODO.adapter = adapter

            adapter.setList(todoRepo.getAllToDo())
            lifecycleScope.launch(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun listItemClicked(selectedItem: Todo) {
        val bundle = Bundle().apply {
            putSerializable("todo", selectedItem)
        }
        val intent = Intent(this@MainActivity,TodoView::class.java)
        intent.putExtras( bundle)
        startActivity(intent)
    }
}