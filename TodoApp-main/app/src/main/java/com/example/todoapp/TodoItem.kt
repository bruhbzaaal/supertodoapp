import android.os.Parcel
import android.os.Parcelable
import java.util.Date
import com.example.todoapp.Importance

data class TodoItem(
    val id: String,
    var text: String,
    var importance: Importance,
    var deadline: Date?,
    var isCompleted: Boolean,
    val createdAt: Date,
    var updatedAt: Date?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        Importance.fromString(parcel.readString()!!),
        parcel.readSerializable() as? Date,
        parcel.readByte() != 0.toByte(),
        parcel.readSerializable() as Date,
        parcel.readSerializable() as? Date
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(text)
        parcel.writeString(importance.toString())
        parcel.writeSerializable(deadline)
        parcel.writeByte(if (isCompleted) 1 else 0)
        parcel.writeSerializable(createdAt)
        parcel.writeSerializable(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoItem> {
        override fun createFromParcel(parcel: Parcel): TodoItem {
            return TodoItem(parcel)
        }

        override fun newArray(size: Int): Array<TodoItem?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TodoItem

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}