package com.theworld.socketApp.ui.auth

import com.theworld.socketApp.utils.SafeApiCall
import com.theworld.socketApp.network.Api
import javax.inject.Inject


class AuthRepo @Inject constructor(private val api: Api) : SafeApiCall() {


    /*-------------------- Login ---------------------*/

    suspend fun login(
        email: String,
        password: String
    ) = api.login(email, password)



    /*-------------------- Social Login ---------------------*/

    suspend fun socialLogin(
        email: String,
        name: String,
        personId: String,
        profileImage: String
    ) = api.socialLogin(email, name, personId, profileImage)


    /*-------------------- Register ---------------------*/


    suspend fun register(
        name: String,
        email: String,
        mobile_no: String,
        password: String
    ) = api.register(name, email, mobile_no, password)

    /*------------------------------------ Reset Password ------------------------------------*/

    suspend fun resetPassword(
        mobile_no: String,
        password: String,
    ) = api.resetPassword(mobile_no, password)


    /*------------------------------------ Change Password ------------------------------------*/


    suspend fun changePassword(
        userId: Int,
        currentPassword: String,
        password: String,
    ) = api.changePassword(userId, currentPassword, password)

}