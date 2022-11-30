package ca.unb.mobiledev.todolistapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DBHelper(application)
    var id = 0
    // TODO
    //  Add mapping calls between the UI and Database
    fun addTask(task: Task): Int{ //insert
        dbHelper.addToTable1(task.name, task.notes, task.hashTag, task.elapsedTime.toInt(), task.dueDate)

        return dbHelper.selectLastInsertedId()
    }

    fun getAllTasks():ArrayList<Task>?{
        return dbHelper.selectAllFromTable1()
    }

    fun getTask(id: Int): Task? {
        return dbHelper.selectFromTable1(id)
    }

    fun deleteAllTasks() {
        dbHelper.deleteAllTable1()
    }

    fun deleteTask(id: Int) {
        dbHelper.deleteFromTable1(id)
    }

    fun editTask(newTask: Task, oldTask: Task) {
        dbHelper.changeNameTable1(newTask.name, oldTask.name)
        dbHelper.changeNotesTable1(newTask.notes, oldTask.name)
        dbHelper.changeTagTable1(newTask.hashTag, oldTask.name)
        dbHelper.changeWorkTimeTable1(newTask.elapsedTime.toInt(), oldTask.name)
        dbHelper.changeDueDateTable1(newTask.dueDate, oldTask.name)
    }
}