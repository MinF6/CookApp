package com.zongmin.cook.data.source.remote

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zongmin.cook.data.*
import com.zongmin.cook.data.source.CookDataSource
import com.zongmin.cook.login.UserManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object CookRemoteDataSource : CookDataSource {

    private const val RECIPES = " Recipes"
    private const val INGREDIENT = "ingredient"
    private const val STEP = "step"
    private const val PLAN = "Plan"
    private const val MANAGEMENT = "Management"
    private const val USER = "User"


    override suspend fun getRecipes(collect: List<String>): Result<List<Recipe>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereIn("id", collect)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()
                        for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                            val recipes = document.toObject(Recipe::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(INGREDIENT)
                                .get()
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        val list2 = mutableListOf<Ingredient>()
                                        for (document2 in task2.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Ingredient::class.java))
                                        }
                                        recipes.ingredient = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("message")
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Message>()
                                        for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Message::class.java))
                                        }
                                        recipes.message = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
//                            .collection("step")
                                .collection(STEP)
                                .orderBy("sequence", Query.Direction.ASCENDING)
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Step>()
                                        for (document2 in task3.result) {
                                            list2.add(document2.toObject(Step::class.java))
                                        }
                                        recipes.step = list2
                                        list.add(recipes)
                                        count--
                                        if (count == 0) {
                                            continuation.resume(Result.Success(list))
                                        }
                                    }
                                }
                        }
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
                    }
                }
        }


    override suspend fun getCategoryRecipes(
        collect: List<String>,
        type: String
    ): Result<List<Recipe>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereIn("id", collect)
                .whereEqualTo("category", type)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()
//                        Log.d("hank1", "檢查task.result.size -> ${task.result.size()}")
                        for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                            val recipes = document.toObject(Recipe::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(INGREDIENT)
                                .get()
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        val list2 = mutableListOf<Ingredient>()
                                        for (document2 in task2.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Ingredient::class.java))
                                        }
                                        recipes.ingredient = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("message")
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Message>()
                                        for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Message::class.java))
                                        }
                                        recipes.message = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(STEP)
                                .orderBy("sequence", Query.Direction.ASCENDING)
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Step>()
                                        for (document2 in task3.result) {
                                            list2.add(document2.toObject(Step::class.java))
                                        }
                                        recipes.step = list2
                                        list.add(recipes)
                                        count--
                                        if (count == 0) {
//                                            Log.d("hank1", "成功查到東西")
                                            continuation.resume(Result.Success(list))
                                        }
                                    }
                                }
                        }
                    } else {
                        task.exception?.let {
                            Log.d("hank1", "失敗")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
                    }
                }
        }

    override suspend fun getCompoundRecipes(
        collect: List<String>,
        type: String,
        key: String
    ): Result<List<Recipe>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereIn("id", collect)
                .whereEqualTo("category", type)
                .whereEqualTo("name", key)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        Log.d("hank1", "檢查task.result.size -> ${task.result.size()}")
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()

                        for (document in task.result!!) {
//                            Log.d("hank1", document.id + " => " + document.data)

                            val recipes = document.toObject(Recipe::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(INGREDIENT)
                                .get()
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        val list2 = mutableListOf<Ingredient>()
                                        for (document2 in task2.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Ingredient::class.java))
                                        }
                                        recipes.ingredient = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("message")
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Message>()
                                        for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Message::class.java))
                                        }
                                        recipes.message = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(STEP)
                                .orderBy("sequence", Query.Direction.ASCENDING)
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Step>()
                                        for (document2 in task3.result) {
                                            list2.add(document2.toObject(Step::class.java))
                                        }
                                        recipes.step = list2
                                        list.add(recipes)
                                        count--
                                        if (count == 0) {
                                            continuation.resume(Result.Success(list))
                                        }
                                    }
                                }
//                            list.add(recipes)
//                            count--
//                            if (count == 0) {
//                                continuation.resume(Result.Success(list))
//                            }
                        }
                    } else {
                        task.exception?.let {
//                            Log.d("hank1", "GGGGGG")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
                    }
                }
        }

    override suspend fun getKeywordRecipes(
        collect: List<String>,
        key: String
    ): Result<List<Recipe>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereIn("id", collect)
                .whereEqualTo("name", key)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()
                        for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                            val recipes = document.toObject(Recipe::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(INGREDIENT)
                                .get()
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        val list2 = mutableListOf<Ingredient>()
                                        for (document2 in task2.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Ingredient::class.java))
                                        }
                                        recipes.ingredient = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("message")
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Message>()
                                        for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Message::class.java))
                                        }
                                        recipes.message = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(STEP)
                                .orderBy("sequence", Query.Direction.ASCENDING)
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Step>()
                                        for (document2 in task3.result) {
                                            list2.add(document2.toObject(Step::class.java))
                                        }
                                        recipes.step = list2
                                        list.add(recipes)
                                        count--
                                        if (count == 0) {
                                            continuation.resume(Result.Success(list))
                                        }
                                    }
                                }
                        }
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
                    }
                }
        }

    //改版，根據使用者ID取得，比對作者
    override suspend fun getCreationRecipes(userId: String): Result<List<Recipe>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereEqualTo("author", userId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()
                        for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                            val recipes = document.toObject(Recipe::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(INGREDIENT)
                                .get()
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        val list2 = mutableListOf<Ingredient>()
                                        for (document2 in task2.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Ingredient::class.java))
                                        }
                                        recipes.ingredient = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("message")
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Message>()
                                        for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Message::class.java))
                                        }
                                        recipes.message = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(STEP)
                                .orderBy("sequence", Query.Direction.ASCENDING)
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Step>()
                                        for (document2 in task3.result) {
                                            list2.add(document2.toObject(Step::class.java))
                                        }
                                        recipes.step = list2
                                        list.add(recipes)
                                        count--
                                        if (count == 0) {
                                            continuation.resume(Result.Success(list))
                                        }
                                    }
                                }
                        }
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
                    }
                }
        }

    override suspend fun getCollectRecipes(collect: List<String>): Result<List<Recipe>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereIn("id", collect)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()
                        for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                            val recipes = document.toObject(Recipe::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(INGREDIENT)
                                .get()
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        val list2 = mutableListOf<Ingredient>()
                                        for (document2 in task2.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Ingredient::class.java))
                                        }
                                        recipes.ingredient = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("message")
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Message>()
                                        for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Message::class.java))
                                        }
                                        recipes.message = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(STEP)
                                .orderBy("sequence", Query.Direction.ASCENDING)
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Step>()
                                        for (document2 in task3.result) {
                                            list2.add(document2.toObject(Step::class.java))
                                        }
                                        recipes.step = list2
                                        list.add(recipes)
                                        count--
                                        if (count == 0) {
                                            continuation.resume(Result.Success(list))
                                        }
                                    }
                                }
                        }
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun getPublicRecipes(): Result<List<Recipe>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereEqualTo("public", true)
//                .orderBy("serving")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()
                        for (document in task.result!!) {
//                            Log.d("hank1", document.id + " => " + document.data)
                            val recipes = document.toObject(Recipe::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(INGREDIENT)
                                .get()
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        val list2 = mutableListOf<Ingredient>()
                                        for (document2 in task2.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Ingredient::class.java))
                                        }
                                        recipes.ingredient = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("message")
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Message>()
                                        for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                            list2.add(document2.toObject(Message::class.java))
                                        }
                                        recipes.message = list2
                                    }

                                }
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection(STEP)
                                .orderBy("sequence", Query.Direction.ASCENDING)
                                .get()
                                .addOnCompleteListener { task3 ->
                                    if (task3.isSuccessful) {
                                        val list2 = mutableListOf<Step>()
                                        for (document2 in task3.result) {
                                            list2.add(document2.toObject(Step::class.java))
                                        }
                                        recipes.step = list2
                                        list.add(recipes)
                                        count--
                                        if (count == 0) {
                                            continuation.resume(Result.Success(list))
                                        }
                                    }
                                }
                        }
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }


    override suspend fun getPlan(userId: String, time: Long): Result<List<Plan>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PLAN)
                .whereEqualTo("userId", userId)
                .whereEqualTo("time", time)
//                .orderBy("time")
//                .startAt(time)
//            .whereGreaterThan("time",time)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Plan>()
                        for (document in task.result!!) {
                            Log.d("hank1", document.id + " => " + document.data)
                            val plan = document.toObject(Plan::class.java)
                            list.add(plan)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }


    //食材管理
    override suspend fun getManagement(userId: String, time: Long): Result<List<Management>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("Management")
                .whereEqualTo("userId", userId)
                .whereEqualTo("time", time)
//                .whereLessThan("time")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Management>()
                        for (document in task.result!!) {
//                        Log.d("hank1", document.id + " => " + document.data)
                            val management = document.toObject(Management::class.java)
                            list.add(management)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun getSpecifyManagement(planId: String): Result<List<Management>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("Management")
                .whereEqualTo("planId", planId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Management>()
                        for (document in task.result!!) {
//                        Log.d("hank1", document.id + " => " + document.data)
                            val management = document.toObject(Management::class.java)
                            list.add(management)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun getPeriodManagement(
        userId: String,
        todayTime: Long,
        scopeTime: Long
    ): Result<List<Management>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("Management")
                .whereEqualTo("userId", userId)
//                .whereGreaterThanOrEqualTo("time", todayTime)
                .whereGreaterThan("time", todayTime - 10L)
                .whereLessThan("time", scopeTime)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Management>()
                        for (document in task.result!!) {
//                        Log.d("hank1", document.id + " => " + document.data)
                            val management = document.toObject(Management::class.java)
                            list.add(management)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun getUser(id: String): Result<User> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("User")
            .whereEqualTo("id", id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    val list = mutableListOf<User>()
//                    var list: User
                    for (document in task.result!!) {
                        val user = document.toObject(User::class.java)

                        continuation.resume(Result.Success(user))
                    }
//                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                }
            }
    }

    override suspend fun getSocialUser(userList: List<String>): Result<List<User>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("User")
                .whereIn("id", userList)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<User>()
                        for (document in task.result!!) {
                            val user = document.toObject(User::class.java)
                            list.add(user)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun getFollowList(userList: List<String>): Result<List<User>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("User")
                .whereIn("id", userList)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<User>()
                        for (document in task.result!!) {
                            val user = document.toObject(User::class.java)
                            list.add(user)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun createRecipes(
        summary: Summary,
        ingredient: List<Ingredient>,
        step: List<Step>
    ): Result<String> =
        suspendCoroutine { continuation ->
            val recipe = FirebaseFirestore.getInstance().collection(RECIPES)
            val document = recipe.document()
            if (summary.id == " ") {
                summary.id = document.id
                Log.d("hank1", "需要給ID")
            } else {

                Log.d("hank1", "不需要給ID")
            }

//        article.createdTime = Calendar.getInstance().timeInMillis
            document
                .set(summary)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        Log.d("hank1", "新增成功區，我新增了 -> $recipe")
                        continuation.resume(Result.Success(summary.id))
//                        continuation.resume(Result.Success(document.id))
                    } else {
                        task.exception?.let {
                            Log.d("hank1", "新增失敗")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(kotlin.Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }

            //ingredient
            val recipeIngredient = FirebaseFirestore.getInstance().collection(INGREDIENT)
            for (i in ingredient) {
                val ingredientDocument = recipeIngredient.document()
                i.id = ingredientDocument.id
                document.collection(INGREDIENT).document(ingredientDocument.id).set(i)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
//                            Log.d("hank1", "新增成功區，我新增了ingredient -> $i")
//                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
                                Log.d("hank1", "新增失敗")
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }

            //step
            val recipeStep = FirebaseFirestore.getInstance().collection(STEP)
            for (i in step) {
                val stepDocument = recipeStep.document()
                i.id = stepDocument.id
                document.collection(STEP).document(stepDocument.id).set(i)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
//                            Log.d("hank1", "新增成功區，我新增了step -> $i")
//                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
                                Log.d("hank1", "新增失敗")
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }
//            continuation.resume(Result.Success(document.id))
//            continuation.resume(Result.Success(summary.id))

        }

    override suspend fun userSignIn(user: User): Result<Boolean> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection("User")
                .document(user.id)
                .set(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "pushScore task.isSuccessful")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Log.d(
                                "hank1",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(Result.Fail(MovieApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun deleteRecipes(id: String): Result<Boolean> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .document(id)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "成功刪除")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Log.d(
                                "hank1",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun createPlan(plan: Plan): Result<String> =
        suspendCoroutine { continuation ->
            val recipe = FirebaseFirestore.getInstance().collection(PLAN)
            val document = recipe.document()

            plan.id = document.id
//        article.createdTime = Calendar.getInstance().timeInMillis
            document
                .set(plan)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "新增成功區，我新增了 -> $plan")
                        continuation.resume(Result.Success(plan.id))
                    } else {
                        task.exception?.let {
                            Log.d("hank1", "新增失敗")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(kotlin.Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun deletePlan(id: String): Result<String> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PLAN)
                .document(id)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "成功刪除")
                        continuation.resume(Result.Success(id))
                    } else {
                        task.exception?.let {
                            Log.d(
                                "hank1",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun createManagement(management: Management): Result<Boolean> =
        suspendCoroutine { continuation ->
            val recipe = FirebaseFirestore.getInstance().collection(MANAGEMENT)
            val document = recipe.document()

            management.id = document.id
            document
                .set(management)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "新增成功區，我新增了 -> $management")
//                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Log.d("hank1", "新增失敗")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(kotlin.Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun deleteManagement(id: String): Result<Boolean> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(MANAGEMENT)
                .document(id)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "成功刪除管理的內容")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Log.d(
                                "hank1",
                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                            )
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun setCollect(isCollect: Boolean, recipesId: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            if (isCollect) {
                FirebaseFirestore.getInstance()
                    .collection(USER)
                    .document(UserManager.user.id)
                    .update("collect", FieldValue.arrayRemove(recipesId))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
//                            Log.d("hank1", "成功刪除此收藏")
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
                                Log.d(
                                    "hank1",
                                    "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                )
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            } else {
                FirebaseFirestore.getInstance()
                    .collection(USER)
                    .document(UserManager.user.id)
                    .update("collect", FieldValue.arrayUnion(recipesId))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
//                            Log.d("hank1", "成功新增收藏")
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
                                Log.d(
                                    "hank1",
                                    "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                )
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }

        }

    override suspend fun setLike(isLiked: Boolean, recipesId: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            if (isLiked) {
                FirebaseFirestore.getInstance()
                    .collection(RECIPES)
                    .document(recipesId)
                    .update("like", FieldValue.arrayRemove(UserManager.user.id))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("hank1", "收回讚")
                            continuation.resume(Result.Success(false))
                        } else {
                            task.exception?.let {
                                Log.d(
                                    "hank1",
                                    "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                )
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            } else {
                FirebaseFirestore.getInstance()
                    .collection(RECIPES)
                    .document(recipesId)
                    .update("like", FieldValue.arrayUnion(UserManager.user.id))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("hank1", "成功點讚")
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
                                Log.d(
                                    "hank1",
                                    "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                )
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }

        }

    override suspend fun setPublic(isPublic: Boolean, recipesId: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            if (isPublic) {
                FirebaseFirestore.getInstance()
                    .collection(RECIPES)
                    .document(recipesId)
                    .update("public", true)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("hank1", "要公開了")
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
                                Log.d(
                                    "hank1",
                                    "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                )
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            } else {
                FirebaseFirestore.getInstance()
                    .collection(RECIPES)
                    .document(recipesId)
                    .update("public", false)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("hank1", "不公開了")
                            continuation.resume(Result.Success(false))
                        } else {
                            task.exception?.let {
                                Log.d(
                                    "hank1",
                                    "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                )
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }

        }

    override suspend fun setPrepare(isPrepare: Boolean, managementId: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            if (isPrepare) {
                FirebaseFirestore.getInstance()
                    .collection(MANAGEMENT)
                    .document(managementId)
                    .update("prepare", true)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("hank1", "準備好了")
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
                                Log.d(
                                    "hank1",
                                    "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                )
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            } else {
                FirebaseFirestore.getInstance()
                    .collection(MANAGEMENT)
                    .document(managementId)
                    .update("prepare", false)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("hank1", "還沒準備好")
                            continuation.resume(Result.Success(false))
                        } else {
                            task.exception?.let {
                                Log.d(
                                    "hank1",
                                    "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                )
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }

        }

}
