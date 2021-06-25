package com.theworld.socketApp.data.message


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("receiver_id")
    val receiverId: Int,

    @SerializedName("sender_id")
    val senderId: Int,

    @SerializedName("updated_at")
    val updatedAt: String
)

//{
//
//    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//    //            val date = dateFormat.parse("2017-04-26T20:55:00.000Z") //You will get date object relative to server/client timezone wherever it is parsed
//    val date =
//        dateFormat.parse(rawDate) //You will get date object relative to server/client timezone wherever it is parsed
//
//    val formatter = SimpleDateFormat(
//        "dd-MM-yyyy",
//        Locale.getDefault()
//    ) //If you need time just put specific format for time like 'HH:mm:ss'
//
//    return formatter.format(date ?: "Raw Date")
//}