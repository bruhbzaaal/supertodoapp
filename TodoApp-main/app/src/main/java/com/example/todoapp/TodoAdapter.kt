import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class TodoAdapter(
    private var items: List<TodoItem>,
    private val onItemClick: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateTodoItems(newItems: List<TodoItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewCompletionFlag: View = itemView.findViewById(R.id.viewCompletionFlag)
        private val txtTaskText: TextView = itemView.findViewById(R.id.txtTaskText)
        private val txtTaskPriority: TextView = itemView.findViewById(R.id.txtTaskPriority)

        fun bind(item: TodoItem) {
            val flagColor = if (item.isCompleted) R.color.green else R.color.red
            viewCompletionFlag.setBackgroundColor(ContextCompat.getColor(itemView.context, flagColor))

            txtTaskText.text = item.text
            txtTaskPriority.text = item.importance.toString()
        }
    }
}