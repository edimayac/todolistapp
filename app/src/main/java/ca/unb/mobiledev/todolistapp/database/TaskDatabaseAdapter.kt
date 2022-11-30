package ca.unb.mobiledev.todolistapp.database

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import ca.unb.mobiledev.todolistapp.R

class TaskDatabaseAdapter(context: Context, items: List<Task>) : ArrayAdapter<Task>(
    context, 0, items) {
    private class ViewHolder {
        lateinit var tvName: TextView
        lateinit var tvNotes: TextView
        lateinit var tvDueDate: TextView
        lateinit var cbCheckBox: CheckBox
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
        viewHolder.tvName = currView!!.findViewById<TextView>(R.id.taskName_textview)
        viewHolder.tvNotes = currView!!.findViewById<TextView>(R.id.notes_textview)
        viewHolder.tvDueDate = currView!!.findViewById<TextView>(R.id.dueDate_textview)
        viewHolder.cbCheckBox = currView!!.findViewById<CheckBox>(R.id.checkbox)

        viewHolder.tvName.text = task?.name
        viewHolder.tvNotes.text = task?.notes
        viewHolder.tvDueDate.text = task?.dueDate
        currView.tag = viewHolder
        viewHolder.cbCheckBox.setOnCheckedChangeListener { button, b ->
            button.isChecked = b
            task.isChecked = b
        }

        // Return the completed view to render on screen
        return currView
    }
}