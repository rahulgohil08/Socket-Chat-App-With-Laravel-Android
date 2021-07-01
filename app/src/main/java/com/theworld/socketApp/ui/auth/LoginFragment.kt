package com.theworld.socketApp.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hrsports.cricketstreaming.utils.*
import com.theworld.socketApp.R
import com.theworld.socketApp.data.user.User
import com.theworld.socketApp.databinding.FragmentLoginBinding
import com.theworld.socketApp.utils.CustomValidation
import com.theworld.socketApp.utils.Resource
import com.theworld.socketApp.utils.redirectToDestination
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {


    companion object {
        const val RC_SIGN_IN = 1001
    }


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private lateinit var password: String
    private lateinit var email: String

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager


    /*----------------------------------------- On ViewCreated -------------------------------*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        init()
        navigateUser()
        clickListeners()

    }

    /*----------------------------------------- Init -------------------------------*/


    private fun init() {


    }


    /*----------------------------------------- Navigate User -------------------------------*/

    private fun navigateUser() {
        if (requireContext().isLogin()) {
            findNavController().redirectToDestination(R.id.dashboardFragment)
        }

    }


    /*----------------------------------------- Click Listeners -------------------------------*/

    private fun clickListeners() {

        binding.apply {

            tvSignUp.setOnClickListener {

                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(action)
            }


            btnLogin.setOnClickListener {
                if (!binding.edtEmail.customValidation(
                        CustomValidation(
                            isLengthRequired = true,
                            length = 10,
                        )
                    )
                    or

                    !binding.edtPassword.customValidation(
                        CustomValidation()
                    )
                ) {
                    return@setOnClickListener
                }


                email = binding.edtEmail.normalText()
                password = binding.edtPassword.normalText()

                doLogin(email, password)
            }


//            btnGoogleLogin.setOnClickListener {
////                initGoogleLogin()
//            }

        }


    }


    /*----------------------------------------- Do Login -----------------------------------------*/

    private fun doLogin(email: String, password: String) {


        viewModel.login(email, password).observe(viewLifecycleOwner) { resource ->

            isLoading(resource is Resource.Loading)

            when (resource) {
                is Resource.Success -> {

                    storeData(resource.value)

                }
                is Resource.Failure -> {
                    handleApiError(resource)
                }
            }

        }
    }


//    private fun doSocialLogin(email: String, name: String, personId: String, profileImage: String) {
//
//
//
//        viewModel.socialLogin(email, name, personId, profileImage).observe(viewLifecycleOwner) {
//
//            isLoading(resource is Resource.Loading)
//
//
//            it?.let { resource ->
//
//                when (resource) {
//                    is Resource.Success -> {
//
////                        storeData(resource.value)
//
//                    }
//                    is Resource.Failure -> {
//                        handleApiError(resource)
//                    }
//                }
//            }
//        }
//    }
//


    private fun isLoading(isLoading: Boolean = true) {
        binding.loadingSpinner.isVisible = isLoading
    }


    /*----------------------------------------- Store Data -------------------------------*/

    private fun storeData(user: User) {

        sharedPrefManager.setInt("user_id", (user.id))
        sharedPrefManager.setString("name", user.name)
        sharedPrefManager.setBoolean("is_login", true)
        navigateUser()

    }


    /*-------------------------------- Google Login Flow -------------------------------------*/

//    private fun initGoogleLogin() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestProfile()
//            .requestEmail()
//            .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//        val signInIntent = mGoogleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//            return
//        }
//    }
//
//
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            onGoogleSuccess(account)
//
//
//        } catch (apiException: ApiException) {
//            requireContext().toast(apiException.toString())
//            Log.e("exception", apiException.message.toString())
//
//        } catch (exception: Exception) {
//            Log.e("Error", exception.message.toString())
//            requireContext().toast(exception.toString())
//
//        }
//    }
//
//
//    private fun onGoogleSuccess(account: GoogleSignInAccount?) {
//        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
//
//        requireContext().toast("Login Success")
//        /*val googleUserModel = GoogleUserModel()
//        googleUserModel.displayName = account!!.displayName
//        googleUserModel.givenName = account.givenName
//        googleUserModel.familyName = account.familyName
//        googleUserModel.email = account.email
//        googleUserModel.id = account.id
//        googleUserModel.photoUrl = account.photoUrl*/
//
//
//        val googleLoginData = SocialLoginData(
//            firstName = account?.displayName
//                ?: "Name not Found in Google",
//
//            email = account?.email
//                ?: "test@gmail.com",
//
//            avatar = account?.photoUrl.toString(),
//
//            id = account?.id
//                ?: "",
//        )
//
//        doSocialLogin(
//            googleLoginData.email,
//            googleLoginData.firstName,
//            googleLoginData.id,
//            googleLoginData.avatar,
//        )
//
//        Log.e("Login Data", "" + Gson().toJson(googleLoginData))
//    }


    /*----------------------------------------- On DestroyView -------------------------------*/

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}