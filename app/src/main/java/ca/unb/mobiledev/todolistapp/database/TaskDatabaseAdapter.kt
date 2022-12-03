package ca.unb.mobiledev.todolistapp.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import ca.unb.mobiledev.todolistapp.R

class TaskDatabaseAdapter(context: Context, items: List<Task>) : ArrayAdapter<Task>(
    context, 0, items) {
    class ViewHolder {
        lateinit var tvName: TextView
        lateinit var tvNotes: TextView
        lateinit var tvHashTag: TextView
        lateinit var tvDueDate: TextView
        lateinit var cbCheckBox: CheckBox
        lateinit var task: Task
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        val task = getItem(position) as Task

        // Check if an existing view is being reused, otherwise inflate the view\
        var currView = convertView
        if (currView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false)
        }

        // Lookup view for data population
        val viewHolder = ViewHolder()
        viewHolder.tvName = currView!!.findViewById(R.id.taskName_textview)
        viewHolder.tvNotes = currView.findViewById(R.id.notes_textview)
        viewHolder.tvHashTag = currView.findViewById(R.id.hashTag_textview)
        viewHolder.tvDueDate = currView.findViewById(R.id.dueDate_textview)
        viewHolder.cbCheckBox = currView.findViewById(R.id.checkbox)

        if (!task.name.isNullOrEmpty() && task.name != "null") {
            viewHolder.tvName.visibility = View.VISIBLE
            viewHolder.tvName.text = task.name
        } else {
            viewHolder.tvName.visibility = View.INVISIBLE
        }

        if (!task.notes.isNullOrEmpty() && task.notes != "null") {
            viewHolder.tvNotes.visibility = View.VISIBLE
            viewHolder.tvNotes.text = task.notes
        } else {
            viewHolder.tvNotes.visibility = View.INVISIBLE
        }

        if (!task.hashTag.isNullOrEmpty() && task.hashTag != "null") {
            viewHolder.tvHashTag.visibility = View.VISIBLE
            viewHolder.tvHashTag.text = task.hashTag
        } else {
            viewHolder.tvHashTag.visibility = View.INVISIBLE
        }

        if (!task.dueDate.isNullOrEmpty() && task.dueDate != "null") {
            viewHolder.tvDueDate.visibility = View.VISIBLE
            viewHolder.tvDueDate.text = task.dueDate
        } else {
            viewHolder.tvDueDate.visibility = View.INVISIBLE
        }


        viewHolder.cbCheckBox.isChecked = false
        viewHolder.cbCheckBox.setOnCheckedChangeListener { _, b ->
            task.isChecked = b
        }

        viewHolder.task = task
        currView.tag = viewHolder

        // Return the completed view to render on screen
        return currView
    }
}