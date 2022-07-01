package com.zongmin.cook.data.source.remote

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.zongmin.cook.CookApplication
import com.zongmin.cook.data.*
import com.zongmin.cook.data.source.CookDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object CookRemoteDataSource : CookDataSource {

    const val RECIPES = " Recipes"

    override suspend fun getRecipes(): Result<List<Recipes>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(RECIPES)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var count = task.result.size()
                    val list = mutableListOf<Recipes>()
                    for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                        val recipes = document.toObject(Recipes::class.java)
                        FirebaseFirestore.getInstance()
                            .collection(RECIPES)
                            .document(document.id)
                            .collection("ingredient")
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
                            .collection("step")
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


    override suspend fun getCategoryRecipes(type: String): Result<List<Recipes>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereEqualTo("category", type)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipes>()
//                        Log.d("hank1", "檢查task.result.size -> ${task.result.size()}")
                        for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                            val recipes = document.toObject(Recipes::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("ingredient")
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
                                .collection("step")
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

    override suspend fun getCompoundRecipes(type: String, key: String): Result<List<Recipes>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereEqualTo("category", type)
                .whereEqualTo("name", key)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        Log.d("hank1", "檢查task.result.size -> ${task.result.size()}")
                        var count = task.result.size()
                        val list = mutableListOf<Recipes>()

                        for (document in task.result!!) {
//                            Log.d("hank1", document.id + " => " + document.data)

                            val recipes = document.toObject(Recipes::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("ingredient")
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
                                .collection("step")
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
                            Log.d("hank1", "GGGGGG")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
                    }
                }
        }

    override suspend fun getKeywordRecipes(key: String): Result<List<Recipes>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(RECIPES)
                .whereEqualTo("name", key)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var count = task.result.size()
                        val list = mutableListOf<Recipes>()
                        for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                            val recipes = document.toObject(Recipes::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(RECIPES)
                                .document(document.id)
                                .collection("ingredient")
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
                                .collection("step")
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


    override suspend fun getPlan(): Result<List<Plan>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("Plan")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Plan>()
                    for (document in task.result!!) {
//                        Log.d("hank1", document.id + " => " + document.data)
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
    override suspend fun getManagement(): Result<List<Management>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("Management")
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

    override suspend fun getUser(): Result<User> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("User")
            .whereEqualTo("id", "W5bXC4hAbvs5zOYY7i5R")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    val list = mutableListOf<User>()
//                    var list: User
                    for (document in task.result!!) {
//                        Log.d("hank1", document.id + " => " + document.data)
                        val user = document.toObject(User::class.java)
//                        Log.d("hank1", document.id + " => " + document.data)
//                        Log.d("hank1", "看一下user拿到啥 => $user ")

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

    override suspend fun createRecipes(
        recipes: Recipes,
        ingredient: List<Ingredient>,
        step: List<Step>
    ): Result<Boolean> =
        suspendCoroutine { continuation ->
            val recipe = FirebaseFirestore.getInstance().collection(RECIPES)
            val document = recipe.document()

            recipes.id = document.id
//        article.createdTime = Calendar.getInstance().timeInMillis
            document
                .set(recipes)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "新增成功區，我新增了 -> $recipe")
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

            //ingredient
            val recipeIngredient = FirebaseFirestore.getInstance().collection("Ingredient")
            for (i in ingredient) {
                val ingredientDocument = recipeIngredient.document()
                i.id = ingredientDocument.id
                document.collection("Ingredient").document(ingredientDocument.id).set(i)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("hank1", "新增成功區，我新增了ingredient -> $i")
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
            val recipeStep = FirebaseFirestore.getInstance().collection("Step")
            for (i in step) {
                val stepDocument = recipeStep.document()
                i.id = stepDocument.id
                document.collection("Step").document(stepDocument.id).set(i)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("hank1", "新增成功區，我新增了step -> $i")
                            continuation.resume(Result.Success(true))
                        } else {
                            task.exception?.let {
                                Log.d("hank1", "新增失敗")
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                        }
                    }
            }



        }


    //備份單拿Recipes----------------------------------------------

//    override suspend fun getRecipes(): Result<List<Recipes>> = suspendCoroutine { continuation ->
//        FirebaseFirestore.getInstance()
//            .collection(" Recipes")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
////                    Log.d("hank1","check4")
//                    val list = mutableListOf<Recipes>()
//                    for (document in task.result!!) {
//
////                        Log.d("hank1","check3")
////                        Log.d("hank1",document.id + " => " + document.data)
//
//                        val article = document.toObject(Recipes::class.java)
//
//                        list.add(article)
//                    }
//                    continuation.resume(Result.Success(list))
//                } else {
//                    task.exception?.let {
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
////                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
//                }
//            }
//    }


}
