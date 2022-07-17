package com.zongmin.cook.login

import android.app.Activity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentLoginBinding
import com.zongmin.cook.ext.getVmFactory


open class LoginFragment : Fragment() {

    private val viewModel by viewModels< LoginViewModel> { getVmFactory() }

    private lateinit var googleSignInClient: GoogleSignInClient

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.handleSignInResult(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Google log in
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("")
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient =
            context?.let { GoogleSignIn.getClient(it, googleSignInOptions) }
                ?: throw NullPointerException("Expression 'context?.let { GoogleSignIn.getClient(it, gso) }' must not be null")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLoginBinding.inflate(inflater, container, false)

//        binding.buttonLogin.setOnClickListener {
//            signInGoogle()
//
////            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
//        }

//        binding.buttonLoginTest.setBackgroundColor(0xFFFFFF)
//        binding.buttonLoginTest.text = "使用Google登入"

        binding.textLoginPrivacy.movementMethod = LinkMovementMethod.getInstance();

        viewModel.loginSuccess.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(context, "登入成功", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "登入失敗", Toast.LENGTH_LONG).show()
            }

        }
        viewModel.existedUser.observe(viewLifecycleOwner){

            UserManager.user = it
//            Log.d("hank1","檢查user有無值 -> ${UserManager.user}")
        }

//        binding.signInButton.getChildAt(0)
//        setGooglePlusButtonText(binding.signInButton,"用 Google 登入")
//        binding.signInButton.setOnClickListener {
//            signInGoogle()
//        }

        binding.buttonLogin.setOnClickListener {
            signInGoogle()
        }

        viewModel.navigateToRecipes.observe(viewLifecycleOwner){
//            Log.d("hank1","導航到食譜頁面")
            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())

        }


        return binding.root

    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun setGooglePlusButtonText(signInButton: SignInButton, buttonText: String?) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (i in 0 until signInButton.childCount) {
            val v = signInButton.getChildAt(i)
            if (v is TextView) {
                v.text = buttonText
                return
            }
        }
    }


}