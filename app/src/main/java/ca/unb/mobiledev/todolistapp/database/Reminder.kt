package ca.unb.mobiledev.todolistapp.database

class Reminder private constructor(
    private val id: String?,
    private val forgerKey: String?,
    private val reminderTime: String?,
    private val endDate: String?,
    private val Interval: Int?
) {

    data class Builder(
        var id: String? = null,
        var forgerKey: String? = null,
        var reminderTime: String? = null,
        var endDate: String? = null,
        var Interval: Int? = 0,
    ) {

        fun id(id: String) = apply { this.id = id }
        fun forgerKey(forgerKey: String) = apply { this.forgerKey = forgerKey }
        fun reminderTime(reminderTime: String?) = apply { this.reminderTime = reminderTime }
        fun endDate(endDate: String?) = apply { this.endDate = endDate }
        fun Interval(Interval: Int?) = apply { this.Interval = Interval }
        fun build() = Reminder(id, forgerKey, reminderTime, endDate, Interval)

    }
}