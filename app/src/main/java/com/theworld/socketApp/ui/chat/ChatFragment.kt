package com.theworld.socketApp.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.hrsports.cricketstreaming.utils.*
import com.theworld.socketApp.R
import com.theworld.socketApp.data.message.Message
import com.theworld.socketApp.databinding.FragmentChatBinding
import com.theworld.socketApp.utils.CustomValidation
import com.theworld.socketApp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import javax.inject.Inject


@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat) {


    companion object {
        private const val TAG = "DashboardFragment"
        const val WEB_SOCKET_URL = "wss://ws-feed.pro.coinbase.com"

    }

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val fragmentArgs: ChatFragmentArgs by navArgs()

    private val viewModel: ChatViewModel by viewModels()

    private lateinit var chatAdapter: ChatAdapter


    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    private lateinit var socket: Socket

//    private lateinit var webSocketClient: WebSocketClient


    private val gson = Gson()

    /*----------------------------------------- On ViewCreated -------------------------------*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChatBinding.bind(view)


//        webSocket()


        init()
        clickListeners()

        fetchChat()
    }

    /*----------------------------------------- Init -------------------------------*/


    private fun init() {

        chatAdapter = ChatAdapter(requireContext())

        try {
            socket = IO.socket("http://192.168.0.140:3000")
            socket.connect()


            if (socket.connected()) {
                requireContext().toast("${socket.id()} connected successfully")
            }

            socket.on("chat-message", onNewMessage)
            socket.on(Socket.EVENT_CONNECT, onConnect)


            Log.d(TAG, "onViewCreated: ${socket.id()}")
        } catch (e: Exception) {
            Log.d(TAG, "init: ${e.message}")
            requireContext().toast(e.message.toString())
        }





        binding.apply {
            recyclerView.adapter = chatAdapter
//            recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
        }


    }

    var onConnect = Emitter.Listener {
        lifecycleScope.launchWhenCreated {
//            requireContext().toast("Connect Event Called")
        }
    }


    /*----------------------------------------- Click Listeners -------------------------------*/

    private fun clickListeners() {


        binding.apply {

            btnSend.setOnClickListener {
                if (!binding.edtMsg.customValidation(
                        CustomValidation()
                    )
                ) {
                    return@setOnClickListener
                }


                val msg = binding.edtMsg.normalText()

                val requestData = Message(
                    senderId = getUserId(),
                    receiverId = fragmentArgs.userId,
                    message = msg,
                )

                Log.d(TAG, "clickListeners: ${gson.toJson(requestData)}")

                socket.emit("chat-message", gson.toJson(requestData))

                sendMessage(requestData)
            }
        }

    }

    private val onNewMessage = Emitter.Listener { args ->

        Log.d(TAG, "onNewMessage: $args")

//        val data = JsonParser.parseString(args[0].toString()).asJsonObject
//        val data = gson.fromJson<Message>(args[0].toString)


        try {

            val data = gson.fromJson(args[0].toString(), Message::class.java)

            if ((data.senderId == getUserId() && data.receiverId == fragmentArgs.userId) || (data.receiverId == getUserId() && data.senderId == fragmentArgs.userId)) {

                val chatList = chatAdapter.currentList.toMutableList()
                chatList.add(data)
                chatAdapter.submitList(chatList)
                Log.d(TAG, "onNewMessage: $data")
                binding.recyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)

            }


        } catch (e: JSONException) {
            Log.d(TAG, "onNewMessage :::: ${e.message}")
        }


    }


    private fun sendMessage(requestData: Message) {


    }


    /*----------------------------- Fetch Data -------------------------------*/

    private fun fetchChat() {

        viewModel.fetchChat(getUserId(), fragmentArgs.userId)
        viewModel.messages.observe(viewLifecycleOwner) { resource ->

            isLoading(resource is Resource.Loading)

            when (resource) {

                is Resource.Success -> {

                    val data = resource.value

                    binding.notFound.isVisible = data.isEmpty()

                    chatAdapter.submitList(data)

                    binding.recyclerView.smoothScrollToPosition(
                        if (chatAdapter.itemCount <= 0) {
                            0
                        } else {
                            chatAdapter.itemCount - 1
                        }
                    )


                }
                is Resource.Failure -> {
                    handleApiError(resource)
                }
            }

        }
    }


    private fun isLoading(isLoading: Boolean = true) {
        binding.loadingSpinner.isVisible = isLoading
    }

    /*----------------------------------------- On DestroyView -------------------------------*/

    override fun onDestroy() {
        super.onDestroy()

        _binding = null

        socket.disconnect()
    }


    /*-------------------------------- Web socket Inner Class ------------------------------*/

//   private fun webSocket() {
//
//        webSocketClient = object : WebSocketClient(URI(WEB_SOCKET_URL)) {
//
//            override fun onOpen(handshakedata: ServerHandshake?) {
//
//                Log.d(TAG, "onOpen: ")
//                requireContext().toast("Connection Open")
//
//                webSocketClient.send(
//                    "{\n" +
//                            "    \"type\": \"subscribe\",\n" +
//                            "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-EUR\"] }]\n" +
//                            "}"
//                )
//            }
//
//            override fun onMessage(message: String?) {
//
//                Log.d(TAG, "onMessage: ${message.toString()}")
//            }
//
//            override fun onClose(code: Int, reason: String?, remote: Boolean) {
//
//                Log.d(TAG, "onClose: ${reason.toString()}")
//            }
//
//            override fun onError(ex: java.lang.Exception?) {
//
//                Log.d(TAG, "onError: ${ex.toString()}")
//                Log.d(TAG, "onError: ${ex?.message.toString()}")
//            }
//
//        }
//
//    }

}