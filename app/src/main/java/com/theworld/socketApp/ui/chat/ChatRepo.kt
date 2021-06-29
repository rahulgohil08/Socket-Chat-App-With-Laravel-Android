package com.theworld.socketApp.ui.chat

import com.theworld.socketApp.utils.SafeApiCall
import com.theworld.socketApp.network.Api
import retrofit2.http.Query
import javax.inject.Inject


class ChatRepo @Inject constructor(private val api: Api) : SafeApiCall() {


    /*------------------------------------ Fetch Users ------------------------------------*/

    suspend fun fetchChat(
        senderId: Int,
        receiverId: Int,
    ) = safeApiCall {
        api.fetchChat(
            senderId, receiverId
        )
    }

}