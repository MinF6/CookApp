package com.zongmin.cook.login


import android.util.Log
import android.widget.Toast
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


    val user = User()

    private var _loginSuccess = MutableLiveData<Boolean>()

    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess


    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            googleSignInAccount = completedTask.getResult(ApiException::class.java)
//            val googleId = googleSignInAccount.id ?: ""
//            Log.d("hank1", "Google ID = $googleId")
//            val googleFirstName = googleSignInAccount.givenName ?: ""
//            Log.d("hank1", "Google First Name = $googleFirstName")
//            val googleLastName = googleSignInAccount.familyName ?: ""
//            Log.d("hank1", "Google Last Name = $googleLastName")
//            val googleEmail = googleSignInAccount.email ?: ""
//            Log.d("hank1", "Google Email = $googleEmail")
//            val googleProfilePicURL = googleSignInAccount.photoUrl.toString()
//            Log.d("hank1", "Google Profile Pic URL = $googleProfilePicURL")
//            val googleIdToken = googleSignInAccount.idToken ?: ""
//            Log.d("hank1", "Google ID Token = $googleIdToken")
//            val googleIsExpired = googleSignInAccount.isExpired
//            Log.d("hank1", "Google isExpired = $googleIsExpired")

            googleSignInAccount.idToken?.let { firebaseAuthWithGoogle(it) }

            user.name = googleSignInAccount.givenName + "  "
            if(googleSignInAccount.familyName != null){
                user.name += googleSignInAccount.familyName
            }
//            user.id = googleSignInAccount.id.toString()

            Log.d("hank1", "user.name = ${user.name}")
            user.email = googleSignInAccount.email.toString()
            Log.d("hank1", "user.email = ${user.email}")
            user.headShot = googleSignInAccount.photoUrl.toString()
            Log.d("hank1", "user.pictureUri = ${user.headShot}")


            _loginSuccess.value = true

        } catch (e: ApiException) {
            // Sign in was unsuccessful
//            Log.d("hank1", "Google log in failed code = ${e.statusCode}")
            _loginSuccess.value = false
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth = Firebase.auth

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("hank1", "signInWithCredential:success")

                    val firebaseCurrentUser = firebaseAuth.currentUser
                    val firebaseTokenResult = firebaseCurrentUser?.getIdToken(false)?.result

                    user.id = firebaseCurrentUser?.uid.toString()
//                    user.firebaseToken = firebaseTokenResult?.token.toString()
//                    Log.d(
//                        "hank1",
//                        "~~~~~~開始~~~~~~firebaseTokenResult?.token.toString() = ${firebaseTokenResult?.token.toString()}"
//                    )
                    UserManager.user = user
//                    Log.d("hank1","檢查user有無值 -> $user")
                    _navigateToRecipes.value = true
//                    val firebaseDate = firebaseTokenResult?.expirationTimestamp?.let { Date(it) }
//                    Log.d("hank1", "firebaseDate = $firebaseDate")
//
//                    if (firebaseDate != null) {
//                        user.firebaseTokenExpiration = Timestamp(firebaseDate)
//                        Log.d(
//                            "hank1",
//                            "user.firebaseTokenExpiration = ${user.firebaseTokenExpiration}"
//                        )
//                    }

//                    user.signInProvider = firebaseTokenResult?.signInProvider.toString()
//                    Log.d("hank1", "firebaseDate = $firebaseDate")

//                    UserManager.userToken = firebaseTokenResult?.token.toString()
//                    Log.d(
//                        "hank1",
//                        "firebaseTokenResult?.token.toString() = ${firebaseTokenResult?.token.toString()}"
//                    )
//                    Log.d("hank1", "UserManager.userToken = ${UserManager.userToken}")
//                    UserManager.user.value = user

//                    Log.d("hank1", "UserManager.user.value = ${UserManager.user.value}")
//                    liveUser.value = user
//                    Log.d("hank1", "Login user = $user")

                    if (task.result.additionalUserInfo?.isNewUser == true) {
                        UserManager.user = user
//                        Log.d("hank1","檢查user有無值 -> $user")
                        _navigateToRecipes.value = true
//                        Log.d("hank1", "task.result.additionalUserInfo?.isNewUser == true")
//
////                        val firebaseCurrentUser = firebaseAuth.currentUser
//                        Log.d(
//                            "hank1",
//                            "signInWithCredential user.providerId = ${firebaseCurrentUser?.providerId}"
//                        )
//                        Log.d(
//                            "hank1",
//                            "signInWithCredential user.uid = ${firebaseCurrentUser?.uid}"
//                        )
//
////                        user.id = firebaseCurrentUser?.uid.toString()
//                        Log.d("hank1", "user.id = ${user.id}")
//
////                        val firebaseTokenResult = firebaseCurrentUser?.getIdToken(false)?.result
//                        Log.d(
//                            "hank1",
//                            "signInWithCredential user.getIdToken.result.token = ${firebaseTokenResult?.token}"
//                        )
//                        Log.d(
//                            "hank1",
//                            "signInWithCredential user.getIdToken.result.expirationTimestamp = ${firebaseTokenResult?.expirationTimestamp}"
//                        )
//                        Log.d(
//                            "hank1",
//                            "signInWithCredential user.getIdToken.result.signInProvider = ${firebaseTokenResult?.signInProvider}"
//                        )

//                        user.firebaseToken = firebaseTokenResult?.token.toString()
//                        Log.d("hank1", "user.firebaseToken = ${user.firebaseToken}")

//                        val firebaseDate = firebaseTokenResult?.expirationTimestamp?.let { Date(it) }
//
//                        if (firebaseDate != null) {
//                            user.firebaseTokenExpiration = Timestamp(firebaseDate)
//                            Logger.i("user.firebaseTokenExpiration = ${user.firebaseTokenExpiration}")
//                        }

//                        user.signInProvider = firebaseTokenResult?.signInProvider.toString()
//                        Log.d("hank1", "user.signInProvider = ${user.signInProvider}")

//                        UserManager.userToken = firebaseTokenResult?.token.toString()
//                        UserManager.user.value = user
//                        liveUser.value = user

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

//            _status.value = LoadApiStatus.LOADING

            //登入的方法
            cookRepository.userSignIn(user)


//            when (val result = cookRepository.userSignIn(user)) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = MovieApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
        }
    }


//    fun onDetailNavigated() {
//        _navigateToRecipes.value = null
//    }


}