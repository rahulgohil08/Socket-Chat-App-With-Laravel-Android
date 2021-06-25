package com.theworld.socketApp.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hrsports.cricketstreaming.utils.customValidation
import com.theworld.socketApp.utils.Resource
import com.hrsports.cricketstreaming.utils.handleApiError
import com.hrsports.cricketstreaming.utils.normalText
import com.hrsports.cricketstreaming.utils.snackbar
import com.theworld.socketApp.R
import com.theworld.socketApp.databinding.FragmentRegisterBinding
import com.theworld.socketApp.utils.CustomValidation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    private lateinit var name: String
    private lateinit var email: String
    private lateinit var mobileNo: String
    private lateinit var password: String
    private lateinit var confirmPassword: String


    private val viewModel: AuthViewModel by viewModels()


    /*----------------------------------------- On ViewCreated -------------------------------*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRegisterBinding.bind(view)

        init()
        clickListeners()

    }

    /*----------------------------------------- Init -------------------------------*/


    private fun init() {

    }

    /*----------------------------------------- Click Listeners -------------------------------*/

    private fun clickListeners() {


        binding.apply {


            btnRegister.setOnClickListener {


                if (!binding.edtEmail.customValidation(
                        CustomValidation(
                            isEmail = true
                        )
                    )
                    or

                    !binding.edtPassword.customValidation(
                        CustomValidation()
                    )
                    or

                    !binding.edtConfirmPassword.customValidation(
                        CustomValidation()
                    )
                    or

                    !binding.edtMobileNo.customValidation(
                        CustomValidation(isLengthRequired = true, length = 10)
                    )
                ) {
                    return@setOnClickListener
                }


                name = edtName.normalText()
                email = edtEmail.normalText()
                mobileNo = edtMobileNo.normalText()
                password = edtPassword.normalText()
                confirmPassword = edtConfirmPassword.normalText()


                if (password != confirmPassword) {
                    edtConfirmPassword.error = "Password doesn't match"
                    return@setOnClickListener
                }

                doRegister(name, email, mobileNo, password)
            }


        }


    }

    /*----------------------------------------- Do Register -------------------------------*/


    private fun doRegister(name: String, email: String, mobileNo: String, password: String) {


        viewModel.register(name, email, mobileNo, password)
            .observe(viewLifecycleOwner) { resource ->

                isLoading(resource is Resource.Loading)

                when (resource) {
                    is Resource.Success -> {

                        requireView().snackbar("Registered Successfully")
                        findNavController().navigateUp()
                    }
                    is Resource.Failure -> {
                        handleApiError(resource)
                    }
                }
            }
    }


    private fun isLoading(isLoading: Boolean = true) {
        binding.loadingSpinner.isVisible = isLoading
        binding.btnRegister.isEnabled = !isLoading
    }


/*----------------------------------------- On DestroyView -------------------------------*/

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}