package com.theworld.socketApp.data.message


import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class Message(
    @SerializedName("created_at")
    val createdAt: String = "",

    @SerializedName("id")
    val id: Int = 1,

    @SerializedName("message")
    val message: String,

    @SerializedName("receiver_id")
    val receiverId: Int = 1,

    @SerializedName("sender_id")
    val senderId: Int = 1,

    @SerializedName("updated_at")
    val updatedAt: String = ""
) {
    val dateFormatted: String?
        get() {

            return try {


                val dateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())


                val date =
                    dateFormat.parse(
                        if (createdAt.isNotEmpty()) {
                            createdAt
                        } else {
                            SimpleDateFormat(
                                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                Locale.getDefault()
                            ).format(Date())
                        }
                    ) //You will get date object relative to server/client timezone wherever it is parsed

                val formatter = SimpleDateFormat(
                    "dd-MM-yyyy (HH:mm aa)",
                    Locale.getDefault()
                ) //If you need time just put specific format for time like 'HH:mm:ss'
                formatter.format(date ?: "Raw Date")
            } catch (e: Exception) {
                ""
            }
        }


}