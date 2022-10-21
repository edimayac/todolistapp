package ca.unb.mobiledev.todolistapp


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class Database(context: Context, name:String?,factory:  SQLiteDatabase.CursorFactory? , version: Int) : SQLiteOpenHelper(context, name ,factory ,version) {
    private val Table1Name = "`Task Table`"
    private val iDPrimaryKey = "`TASK_ID`"
    private val dueDate = "a"
    private val hashTag = "b"
    private val workTime = "c"

    private val Table2Name = "d"
    private val reminderTime = "e"
    private val interval = "f"
    private val endReminder = "g"
    private val idReminder = "h"
    private val taskName = "i"
    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery1 = ("CREATE TABLE "+ Table1Name +
                " ( "+ iDPrimaryKey +" `TASK_ID` INT NOT NULL,`TASK_NAME` VARCHAR(100),`HASH_TAG` VARCHAR(100), `TOTAL_TIME_WORKED` TIME,`DUE_DATE` DATE, PRIMARY KEY (`TASK_ID`)")


        db?.execSQL(createQuery1)
        val createQuery2 = ("CREATE TABLE " + Table2Name + " ("
                + idReminder + " INTEGER PRIMARY KEY, " +
                reminderTime + " DATE," +
                endReminder + " DATE," +
                interval + " TIME"+ ")"  )


        db?.execSQL(createQuery2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }


}