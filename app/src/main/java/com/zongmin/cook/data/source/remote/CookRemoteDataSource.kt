package com.zongmin.cook.data.source.remote

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
                        for (document in task.result!!) {
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
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()
                        for (document in task.result!!) {
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
                        if (count == 0) {
                            continuation.resume(Result.Success(list))
                        }
                        for (document in task.result) {
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
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipe>()
                        for (document in task.result!!) {
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
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Plan>()
                        for (document in task.result!!) {
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

    override suspend fun getManagement(userId: String, time: Long): Result<List<Management>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("Management")
                .whereEqualTo("userId", userId)
                .whereEqualTo("time", time)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Management>()
                        for (document in task.result!!) {
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
                .whereGreaterThan("time", todayTime - 10L)
                .whereLessThan("time", scopeTime)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Management>()
                        for (document in task.result!!) {
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
                    for (document in task.result!!) {
                        val user = document.toObject(User::class.java)
                        continuation.resume(Result.Success(user))
                    }
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
            if (summary.id == "") {
                summary.id = document.id
            }
            document
                .set(summary)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Result.Success(summary.id))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                    }
                }

            val recipeIngredient = FirebaseFirestore.getInstance().collection(INGREDIENT)
            for (i in ingredient) {
                val ingredientDocument = recipeIngredient.document()
                i.id = ingredientDocument.id
                document.collection(INGREDIENT).document(ingredientDocument.id).set(i)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                        } else {
                            task.exception?.let {
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }

            val recipeStep = FirebaseFirestore.getInstance().collection(STEP)
            for (i in step) {
                val stepDocument = recipeStep.document()
                i.id = stepDocument.id
                document.collection(STEP).document(stepDocument.id).set(i)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                        } else {
                            task.exception?.let {
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }
        }

    override suspend fun userSignIn(user: User): Result<Boolean> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("User")
                .document(user.id)
                .set(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
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
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
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
            document
                .set(plan)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Result.Success(plan.id))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
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
                        continuation.resume(Result.Success(id))
                    } else {
                        task.exception?.let {
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
//                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
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
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
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
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
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
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
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
                            continuation.resume(Result.Success(false))
                        } else {
                            task.exception?.let {
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
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
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
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
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
                            continuation.resume(Result.Success(false))
                        } else {
                            task.exception?.let {
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
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
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
                            continuation.resume(Result.Success(false))
                        } else {
                            task.exception?.let {
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }
        }
}
