package com.zongmin.cook.login


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.User
import com.zongmin.cook.data.source.CookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class LoginViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {
    private lateinit var googleSignInAccount: GoogleSignInAccount
    private lateinit var firebaseAuth: FirebaseAuth

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToRecipes = MutableLiveData<Boolean>()

    val navigateToRecipes: LiveData<Boolean>
        get() = _navigateToRecipes

    private var _existedUser = MutableLiveData<User>()

    val existedUser: LiveData<User>
        get() = _existedUser

    val user = User()

    private var _loginSuccess = MutableLiveData<Boolean>()

    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess




    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            googleSignInAccount = completedTask.getResult(ApiException::class.java)
            googleSignInAccount.idToken?.let { firebaseAuthWithGoogle(it) }
            user.name = googleSignInAccount.givenName.toString()
            if(googleSignInAccount.familyName != null){
                user.name += " ${googleSignInAccount.familyName}"
            }
            user.email = googleSignInAccount.email.toString()
            user.headShot = googleSignInAccount.photoUrl.toString()

            _loginSuccess.value = true

        } catch (e: ApiException) {
            _loginSuccess.value = false
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth = Firebase.auth

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseCurrentUser = firebaseAuth.currentUser
                    val firebaseTokenResult = firebaseCurrentUser?.getIdToken(false)?.result

                    user.id = firebaseCurrentUser?.uid.toString()
                    getUserResult(user.id)
                    if (task.result.additionalUserInfo?.isNewUser == true) {
                        UserManager.user = user
                        _navigateToRecipes.value = true

                        userSignIn(user)
                    } else {
                        Log.d("hank1", "task.result.additionalUserInfo?.isNewUser == false")
                    }
                } else {
                    Log.d("hank1", "signInWithCredential:failure e = ${task.exception}")
                }
            }
    }

    private fun userSignIn(user: User) {
        coroutineScope.launch {
            cookRepository.userSignIn(user)

        }
    }


    private fun getUserResult(id: String) {
        coroutineScope.launch {
            val result = cookRepository.getUser(id)

            _existedUser.value = when (result) {
                is Result.Success -> {
                    result.data
                }
                is Result.Fail -> {
                    null
                }
                is Result.Error -> {

                    null
                }
                else -> {

                    null
                }
            }
            UserManager.user = existedUser.value!!
            _navigateToRecipes.value = true
        }}
}