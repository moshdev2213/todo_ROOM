package com.example.todo.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.dbCON.ToDoDB
import com.example.todo.modal.Todo
import com.example.todo.repository.ToDoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTODOAdapter(
    var context: Context,
    private val cardCilked:(Todo)->Unit
):RecyclerView.Adapter<AddTODOHolder>() {

    private lateinit var toDoDB:ToDoDB
    private val TodoList = ArrayList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTODOHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =layoutInflater.inflate(R.layout.todo_view,parent,false)
        return AddTODOHolder(listItem)
    }

    override fun getItemCount(): Int {
        return TodoList.size
    }

    override fun onBindViewHolder(holder: AddTODOHolder, position: Int) {
        holder.bind(TodoList[position],cardCilked)
        holder.itemView.findViewById<ImageView>(R.id.imgDlt).setOnClickListener {
            showAlert(position,TodoList[position])
        }
    }

    fun showAlert(position: Int,todo: Todo){
        val alert =  AlertDialog.Builder(context)
        alert.setMessage("Are You Sure")
        alert.setTitle("Delete!...")
        alert.setCancelable(false)
        alert.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            GlobalScope.launch(Dispatchers.IO) {
                toDoDB =ToDoDB.getInstance(context)
                val todoDAO = toDoDB.TodoDAO()
                val toDoRepo =ToDoRepo(todoDAO,Dispatchers.IO)
                toDoRepo.deleteToDo(todo)
            }
            deleteItem(position)
        }
        alert.setNegativeButton("No",){dialogInterface:DialogInterface,i:Int->
            dialogInterface.cancel()
        }
    }
    fun deleteItem(position: Int){
        TodoList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }
    fun setList(todo: List<Todo>){
        TodoList.clear()
        TodoList.addAll(todo)
        notifyDataSetChanged()
    }
}

class AddTODOHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(todo: Todo,cardCilked:(Todo)->Unit){
        val imgDlt =view.findViewById<ImageView>(R.id.imgDlt)
        val cvToDoView =view.findViewById<CardView>(R.id.cvToDoView)
        val tvText =view.findViewById<TextView>(R.id.tvText)
        val tvId =view.findViewById<TextView>(R.id.tvId)

        tvText.text = todo.note
        tvId.text = "Id :${todo.id}"
        cvToDoView.setOnClickListener {
            cardCilked(todo)
        }
    }
}