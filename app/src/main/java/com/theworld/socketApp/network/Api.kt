package com.theworld.socketApp.network

import com.theworld.socketApp.data.message.Message
import com.theworld.socketApp.data.user.User
import okhttp3.ResponseBody
import retrofit2.http.*

interface Api {

    /*------------------------------------ Login ------------------------------------*/

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("mobile_no") mobileNo: String,
        @Field("password") password: String
    ): ResponseBody


    /*------------------------------------ Login ------------------------------------*/

    @FormUrlEncoded
    @POST("social_login")
    suspend fun socialLogin(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("person_id") personId: String,
        @Field("profile_image") profileImage: String,
    ): ResponseBody


    /*------------------------------------ Register ------------------------------------*/

    @FormUrlEncoded
    @POST("registration")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile_no") mobile_no: String,
        @Field("password") password: String
    ): ResponseBody


    /*------------------------------------ Reset Password ------------------------------------*/

    @FormUrlEncoded
    @POST("user/reset/password")
    suspend fun resetPassword(
        @Field("mobile_no") mobile_no: String,
        @Field("password") password: String,
    ): ResponseBody


    /*------------------------------------ Change Password ------------------------------------*/

    @FormUrlEncoded
    @POST("changepwd")
    suspend fun changePassword(
        @Field("user_id") userId: Int,
        @Field("old_pwd") currentPassword: String,
        @Field("new_pwd") password: String,
    ): ResponseBody


    /*------------------------------------ Fetch Users ------------------------------------*/

    @GET("user/fetch")
    suspend fun fetchUsers(): List<User>


    /*------------------------------------ Fetch My Messages ------------------------------------*/

    @GET("chat/fetch")
    suspend fun fetchChat(
        @Query("sender_id") senderId: Int,
        @Query("receiver_id") receiverId: Int,
    ): List<Message>


    /*------------------------------------ Send Message ------------------------------------*/

    @FormUrlEncoded
    @POST("chat/store")
    suspend fun sendMessage(
        @Field("sender_id") senderId: Int,
        @Field("receiver_id") receiverId: Int,
        @Field("message") password: String,
    ): Message


}
