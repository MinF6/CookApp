package com.zongmin.cook.login

import android.app.Activity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentLoginBinding
import com.zongmin.cook.ext.getVmFactory


open class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }

    private lateinit var googleSignInClient: GoogleSignInClient

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                viewModel.handleSignInResult(task)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("710335151502-96voej6nc0600b6ad5js1n0onb0fi82f.apps.googleusercontent.com")
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
        binding.textLoginPrivacy.movementMethod = LinkMovementMethod.getInstance()

        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "登入成功", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "登入失敗", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.existedUser.observe(viewLifecycleOwner) {
            UserManager.user = it
        }


        binding.buttonLogin.setOnClickListener {
            signInGoogle()
        }

        viewModel.navigateToRecipes.observe(viewLifecycleOwner) {
            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
        }

        return binding.root
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }


}