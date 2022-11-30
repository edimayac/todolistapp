package ca.unb.mobiledev.todolistapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast


class DBHelper(context: Context) : SQLiteOpenHelper(context, "Data.db" , null ,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery1 = ("CREATE TABLE "+ table1Name +
                " ( "+ iDPrimaryKey +"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                taskName +" VARCHAR(100), "+
                taskNotes +" VARCHAR(100), "+
                hashTag +" VARCHAR(100)," +
                workTime + "INT," +
                dueDate + " DATE" +
                ");")


        db?.execSQL(createQuery1)
        val createQuery2 = ("CREATE TABLE " + table2Name + " ("
                + idReminder + "int NOT NULL, " +
                reminderTime + " DATE, " +
                endReminder + " DATE, " +
                interval + " TIME, " +
                iDPrimaryKey + "INT, " +
                "PRIMARY KEY ("+idReminder+"), "+
                "FOREIGN KEY ("+iDPrimaryKey+") REFERENCES "+table1Name+"("+iDPrimaryKey+")"+");")

        db?.execSQL(createQuery2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addToTable1(taskNamel: String?, taskNotesl: String?, hashTagl: String?, workTimel:Int, dueDatel: String?) {
        val db = this.writableDatabase
        val addQuery =
            "INSERT INTO$table1Name($taskName,$taskNotes,$hashTag,$workTime,$dueDate)VALUES('$taskNamel', '$taskNotesl', '$hashTagl','$workTimel','$dueDatel');"
        db?.execSQL(addQuery)
    }

    fun selectLastInsertedId(): Int {
        val db = this.writableDatabase
        val selectLastId = "SELECT last_insert_rowid() as $iDPrimaryKey FROM $table1Name;"
        val cursor = db.rawQuery(selectLastId, null)
        cursor.moveToFirst()
        val id = cursor.getInt(0)
        cursor.close()

        return id
    }

    fun selectFromTable1(taskIdl: Int): Task? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $table1Name WHERE $iDPrimaryKey = '$taskIdl';"
        val cursor = db.rawQuery(selectQuery, null)
        var task: Task? = null

        if (cursor.moveToFirst()){
            task = Task.Builder()
                .id(cursor.getInt(0))
                .name(cursor.getString(1))
                .notes(cursor.getString(2))
                .hashTag(cursor.getString(3))
                .dueDate(cursor.getString(4))
                .elapsedTime(cursor.getInt(5)).build()
        }
        cursor.close()
        return task
    }

    fun selectAllFromTable1(): ArrayList<Task> {
        val arraylist = ArrayList<Task>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $table1Name;"
        val cursor = db.rawQuery(selectQuery, null)


        if (cursor.moveToFirst()){
            do {
                val task = Task.Builder()
                    .id(cursor.getInt(0))
                    .name(cursor.getString(1))
                    .notes(cursor.getString(2))
                    .hashTag(cursor.getString(3))
                    .dueDate(cursor.getString(4))
                    .elapsedTime(cursor.getInt(5)).build()
                arraylist.add(task)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return arraylist
    }

    fun listTags() : ArrayList<String>{
        val arraylist = ArrayList<String>()
        val db = this.readableDatabase
        val selectQuery = "SELECT DISTINCT $hashTag FROM $table1Name;"
        val cursor = db.rawQuery(selectQuery, null)


        if (cursor.moveToFirst()){
            do {
                val tag = cursor.getString(0)
                arraylist.add(tag)

            }while (cursor.moveToNext())
        }
        cursor.close()
        return arraylist
    }

    fun deleteFromTable1(taskIdl: Int){
        val db= this.writableDatabase
        val query= "DELETE FROM $table1Name WHERE $iDPrimaryKey = '$taskIdl';"

        db.execSQL(query)
    }

    fun deleteAllTable1(){
        val db= this.writableDatabase
        val query= "DELETE FROM $table1Name;"

        db.execSQL(query)
    }

    fun changeNameTable1(replaceWith: String?, locatingVal: String?){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $taskName = '$replaceWith' WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }

    fun changeNotesTable1(replaceWith: String?, locatingVal: String?){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $taskNotes = '$replaceWith' WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }

    fun changeTagTable1(replaceWith: String?, locatingVal: String?){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $hashTag = '$replaceWith' WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }

    fun changeDueDateTable1(replaceWith: String?, locatingVal: String?){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $dueDate = '$replaceWith' WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }

    fun changeWorkTimeTable1(replaceWith: Int, locatingVal: String?){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $workTime = $replaceWith WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }

    fun addToTable2( assinmetId:Int, time:String , endTime:String , intervall:String){
        val db= this.writableDatabase
        val addQuery =
            "INSERT INTO$table2Name($iDPrimaryKey,$reminderTime,$endReminder,$interval)VALUES('$assinmetId','$time','$endTime','$intervall');"
        db?.execSQL(addQuery)

    }

    fun selectFromTable2(taskNamel: String): ArrayList<Reminder> {
        val arraylist = ArrayList<Reminder>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $table2Name WHERE $iDPrimaryKey = (SELECT $iDPrimaryKey FROM $table1Name WHERE $taskName = $taskNamel) ;"
        val cursor = db.rawQuery(selectQuery, null)


        if (cursor.moveToFirst()){
            do {
                val reminder = Reminder.Builder().id(cursor.getString(0))
                    .forgerKey(cursor.getString(1))
                    .reminderTime(cursor.getString(2))
                    .endDate(cursor.getString(3))
                    .Interval(cursor.getInt(4)).build()
                arraylist.add(reminder)

            }while (cursor.moveToNext())
        }
        cursor.close()
        return arraylist
    }

    fun renewDatabase(context: Context){
        context.deleteDatabase("Data.db")
    }

    companion object {
        const val table1Name = " Task_Table "
        const val iDPrimaryKey = " TASK_ID "
        const val taskName = " TASK_NAME "
        const val taskNotes = " TASK_NOTES "
        const val dueDate = " DUE_DATE "
        const val hashTag = " HASH_TAG "
        const val workTime = " TOTAL_TIME_WORKED "

        const val table2Name = " REMINDER_TABLE "
        const val reminderTime = " REMINDER_TIME "
        const val interval = " I_INTERVAL "
        const val endReminder = " END_REMINDER "
        const val idReminder = " REMINDER_ID "
    }
}