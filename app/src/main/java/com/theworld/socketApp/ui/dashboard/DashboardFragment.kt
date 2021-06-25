package com.theworld.socketApp.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hrsports.cricketstreaming.utils.SharedPrefManager
import com.hrsports.cricketstreaming.utils.handleApiError
import com.theworld.socketApp.R
import com.theworld.socketApp.databinding.FragmentDashboardBinding
import com.theworld.socketApp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
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


    /*----------------------------------------- On ViewCreated -------------------------------*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)

        init()
        clickListeners()

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


    /*----------------------------------------- Fetch Data -------------------------------*/

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

    }


}