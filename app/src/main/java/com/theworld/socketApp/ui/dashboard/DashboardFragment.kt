package com.theworld.socketApp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.hrsports.cricketstreaming.utils.*
import com.theworld.socketApp.R
import com.theworld.socketApp.data.message.Message
import com.theworld.socketApp.databinding.FragmentDashboardBinding
import com.theworld.socketApp.utils.CustomValidation
import com.theworld.socketApp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {


    companion object {
        private const val TAG = "DashboardFragment"
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val userAdapter = UserAdapter()
    private val viewModel: DashboardViewModel by viewModels()

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    private lateinit var socket: Socket

    private val gson = Gson()

    /*----------------------------------------- On ViewCreated -------------------------------*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)




        init()
        clickListeners()

    }

    /*----------------------------------------- Init -------------------------------*/


    private fun init() {


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
            recyclerView.adapter = userAdapter
        }


    }

    var onConnect = Emitter.Listener {
        lifecycleScope.launchWhenCreated {
            requireContext().toast("Connect Event Called")
        }
    }


    private val onNewMessage = Emitter.Listener { args ->

        Log.d(TAG, "onNewMessage: $args")

//            lifecycleScope.launch {
        val data = args[0] as JSONObject

        Log.d(TAG, "onNewMessage: $data")
        try {

        } catch (e: JSONException) {
            Log.d(TAG, "onNewMessage :::: ${e.message}")
        }


//            }


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
                    message = msg
                )

                socket.emit("chat-message", gson.toJson(requestData))

                sendMessage(requestData)
            }
        }

    }


    private fun sendMessage(requestData: Message) {


    }


    /*----------------------------- Fetch Data -------------------------------*/

    private fun fetchData() {

        viewModel.fetchUsers()
        viewModel.users.observe(viewLifecycleOwner) { resource ->

            when (resource) {
                is Resource.Success -> {

                    val data = resource.value

                    binding.notFound.isVisible = data.isEmpty()

//                    userAdapter.submitList(data)

                }
                is Resource.Failure -> {
                    handleApiError(resource)
                }
            }

        }
    }

    /*----------------------------------------- On DestroyView -------------------------------*/

    override fun onDestroy() {
        super.onDestroy()

        _binding = null

        socket.disconnect()
    }


}