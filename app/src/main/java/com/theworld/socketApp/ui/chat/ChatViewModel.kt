package com.theworld.socketApp.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theworld.socketApp.data.message.Message
import com.theworld.socketApp.network.Api
import com.theworld.socketApp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatRepo,
    private val api: Api
) : ViewModel() {

    private val _messages: MutableLiveData<Resource<List<Message>>> = MutableLiveData()
    val messages: LiveData<Resource<List<Message>>> = _messages


    /*------------------------------------ Fetch Users ------------------------------------*/

    fun fetchChat(
        senderId: Int,
        receiverId: Int,
    ) = viewModelScope.launch {
        _messages.value = repo.fetchChat( senderId, receiverId)
    }


}