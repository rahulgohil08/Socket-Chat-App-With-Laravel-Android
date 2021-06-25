package com.theworld.socketApp.ui.dashboard

import androidx.lifecycle.*
import com.theworld.socketApp.data.user.User
import com.theworld.socketApp.network.Api
import com.theworld.socketApp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DashboardRepo,
    private val api: Api
) : ViewModel() {

    private val _users: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val users: LiveData<Resource<List<User>>> = _users


    /*------------------------------------ Fetch Users ------------------------------------*/

    fun fetchUsers() = viewModelScope.launch {
        _users.value = repo.fetchUsers()
    }


}