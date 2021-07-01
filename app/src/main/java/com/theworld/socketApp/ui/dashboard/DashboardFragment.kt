package com.theworld.socketApp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hrsports.cricketstreaming.utils.*
import com.theworld.socketApp.R
import com.theworld.socketApp.data.user.User
import com.theworld.socketApp.databinding.FragmentDashboardBinding
import com.theworld.socketApp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard), UserAdapter.UserInterface {


    companion object {
        private const val TAG = "DashboardFragment"
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val userAdapter = UserAdapter(this)
    private val viewModel: DashboardViewModel by viewModels()


    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    /*----------------------------------------- On ViewCreated -------------------------------*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)


        init()
        clickListeners()
        fetchUsers()
    }

    /*----------------------------------------- Init -------------------------------*/


    private fun init() {


        binding.apply {
            recyclerView.adapter = userAdapter
        }


    }


    /*----------------------------------------- Click Listeners -------------------------------*/

    private fun clickListeners() {


    }


    /*----------------------------- Fetch Data -------------------------------*/

    private fun fetchUsers() {

        viewModel.fetchUsers()
        viewModel.users.observe(viewLifecycleOwner) { resource ->

            when (resource) {
                is Resource.Success -> {

                    var data = resource.value

                    binding.notFound.isVisible = data.isEmpty()

                    if (data.isNotEmpty()) {
                        data = data.filter { it.id != getUserId() }
                    }

                    userAdapter.submitList(data)

                }
                is Resource.Failure -> {
                    handleApiError(resource)
                }
            }

        }
    }


    /*----------------------- --- Handle Interfaces -------------------------------*/

    override fun onClick(user: User) {

        Log.d(TAG, "Clicked User is ::: ${user.id}")

        val action =
            DashboardFragmentDirections.actionDashboardFragmentToChatFragment(userId = user.id)
        findNavController().navigate(action)

    }


    /*----------------------------------------- On DestroyView -------------------------------*/

    override fun onDestroy() {
        super.onDestroy()

        _binding = null

    }


}