package ca.unb.mobiledev.todolistapp.database

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import ca.unb.mobiledev.todolistapp.R

class TaskDatabaseAdapter(context: Context, items: List<Task>) : ArrayAdapter<Task>(
    context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        val task = getItem(position)

        // Check if an existing view is being reused, otherwise inflate the view\
        var currView = convertView
        if (currView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false)
        }

        // Lookup view for data population
        val tvName = currView!!.findViewById<TextView>(R.id.taskName_textview)
        val tvNotes = currView!!.findViewById<TextView>(R.id.notes_textview)
        val tvDueDate = currView!!.findViewById<TextView>(R.id.dueDate_textview)
        val checkBox = currView!!.findViewById<CheckBox>(R.id.checkbox)

        checkBox.setOnClickListener { v ->
            val cb = v as CheckBox
            val task: Task = cb.tag as Task
            task.isChecked = cb.isChecked
        }

        tvName.text = task?.name
        tvNotes.text = task?.notes
        tvDueDate.text = task?.dueDate
        checkBox.isChecked = task?.isChecked!!
        checkBox.tag = task;

        // Return the completed view to render on screen
        return currView
    }
}