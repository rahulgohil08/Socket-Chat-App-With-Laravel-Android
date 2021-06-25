package com.theworld.socketApp.ui.dashboard

import com.theworld.socketApp.utils.SafeApiCall
import com.theworld.socketApp.network.Api
import javax.inject.Inject


class DashboardRepo @Inject constructor(private val api: Api) : SafeApiCall() {


    /*------------------------------------ Fetch Users ------------------------------------*/

    suspend fun fetchUsers() = safeApiCall { api.fetchUsers() }

}