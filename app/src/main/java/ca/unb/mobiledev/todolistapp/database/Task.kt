package ca.unb.mobiledev.todolistapp.database

class Task private constructor(
    private var taskId: Int?,
    private var taskName: String?,
    private var taskNotes: String?,
    private var taskHashTag: String?,
    private var taskDueDate: String?,
    private var taskElapsedTime: Int?,
    private var taskIsChecked: Boolean?
) {
    // Only need to include getters
    var id: Int?
        get() = taskId
        set(value) { taskId = value}

    var name: String?
        get() = taskName
        set(value) { taskName = value }

    var notes: String?
        get() = taskNotes
        set(value) { taskNotes = value }

    var hashTag: String?
        get() = taskHashTag
        set(value) { taskHashTag = value }

    var dueDate: String?
        get() = taskDueDate
        set(value) { taskDueDate = value }

    var elapsedTime: String
        get() = "$taskElapsedTime"
        set(value) { taskElapsedTime = value.toInt() }

    var isChecked: Boolean?
        get() = taskIsChecked
        set(value) { taskIsChecked = value }


    data class Builder(
        var id: Int? = null,
        var name: String? = null,
        var notes: String? = null,
        var hashTag: String? = null,
        var dueDate: String? = null,
        var elapsedTime: Int? = 0,
        var isChecked: Boolean? = false
    ) {

        /*
        How to create object for reference:
        1. Create a variable tasks
        private lateinit var tasks: ArrayList<Tasks>

        2. Use Task.Builder() to add values to it. For Example:
        val task = Task.Builder().name(taskName)
        OR
        val task = Task.Builder().name(taskName).dueDate("03/16/2000")

        *Note:  This is great since it gives the user the freedom if they want to add
                additional information in without forcing to add data to it*

        3. Lastly build it with this:
        tasks.add(task.build())


        Ref: Lab 5
         */

        fun id(id: Int) = apply { this.id = id }
        fun name(name: String) = apply { this.name = name }
        fun notes(notes: String?) = apply { this.notes = notes }
        fun hashTag(hashTag: String?) = apply { this.hashTag = hashTag }
        fun dueDate(dueDate: String?) = apply { this.dueDate = dueDate }
        fun elapsedTime(elapsedTime: Int?) = apply { this.elapsedTime = elapsedTime }
        fun isChecked(isChecked: Boolean?) = apply { this.isChecked = isChecked }
        fun build() = Task(id, name, notes, hashTag, dueDate, elapsedTime, isChecked)

    }
}
