package com.zongmin.cook.edit

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.storage.FirebaseStorage
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.R
import com.zongmin.cook.bindImage
import com.zongmin.cook.data.*
import com.zongmin.cook.databinding.FragmentEditRecipesBinding
import com.zongmin.cook.databinding.ItemEditIngredientBinding
import com.zongmin.cook.databinding.ItemEditStepBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.login.UserManager
import java.io.File


class EditRecipesFragment : Fragment() {

    var ingredientList: LinearLayout? = null
    var stepList: LinearLayout? = null

    var uri: Uri? = null
    private var PICK_CONTACT_REQUEST = 1
    private var REQUEST_CODE = 42
    private var ITEM_STEP_IMAGE = 3

    var img1: ImageView? = null
    var img2: ImageView? = null
    private val FILE_NAME = "photo.jpg"
    var photoFile: File? = null

    var tempImageView: ImageView? = null

    private val viewModel by viewModels<EditRecipesViewModel> { getVmFactory() }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //來自檔案
        if (requestCode == PICK_CONTACT_REQUEST) {
            uri = data?.data
//            img1?.setImageURI(uri)
            viewModel.mainUri.value = true
            Log.d("hank1", "看一下上傳拿到的uri是啥 -> $uri")
        }
        //來自itemImage
        if (requestCode == ITEM_STEP_IMAGE) {
            uri = data?.data
//            viewModel.itemUri.value = uri
            tempImageView?.setImageURI(uri)
            Log.d("hank1", "看一下上傳拿到的uri是啥 -> $uri")
        }

        //來自相機
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val takeImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
//            Log.d("hank1", "看一下拍照拿的的takeImage是啥 -> $takeImage")
            img1?.setImageBitmap(takeImage)
//            uri = context?.let { getImageUri(it, takeImage) }
            viewModel.mainUri.value = true
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //    取得暫存圖片檔案
    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(fileName, ".jpg", storageDirectory)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditRecipesBinding.inflate(inflater, container, false)
        val binding2 = ItemEditIngredientBinding.inflate(inflater, container, false)
        val binding3 = ItemEditStepBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val ingredientName = binding2.itemEdittextIngredientName
        val quantity = binding2.itemEdittextQuantity
        val unit = binding2.itemEdittextUnit
        ingredientList = binding.editIngredientList
        stepList = binding.editStepList
        val depiction = binding3.itemEdittextStepDepiction
        val itemImage = binding3.itemStepImage

        binding.imageEditBack.setOnClickListener {
            this.findNavController().navigateUp()
        }

        Log.d(
            "hank1",
            "我收到了資料 -> ${EditRecipesFragmentArgs.fromBundle(requireArguments()).recipe}"
        )
        val recipesData = EditRecipesFragmentArgs.fromBundle(requireArguments()).recipe
        if (recipesData != null) {
            binding.buttonEditDelete.visibility = View.VISIBLE
            viewModel.getRecipesData(recipesData)
            binding.recipe = recipesData
            viewModel.selectSpinnerValue(binding.spinnerEditCategory, recipesData.category)

            //食材所需欄位
            for (i in 0 until (recipesData.ingredient.size - 1)) {
                addView()
//                Log.d("hank1","這欄的size是 ${recipesData.ingredient.size}")
            }
            //放入食材
            if (recipesData.ingredient.isNotEmpty()) {
                for (i in 0 until ingredientList!!.childCount) {
                    if (ingredientList!!.getChildAt(i) is LinearLayoutCompat) {
                        val ll = ingredientList!!.getChildAt(i) as LinearLayoutCompat
                        for (j in 0 until ll.childCount) {
                            if (ll.getChildAt(j) is EditText) {
                                val et = ll.getChildAt(j) as EditText
                                if (et.id == ingredientName.id) {
                                    et.setText(recipesData.ingredient[i].ingredientName)
                                }
                                if (et.id == quantity.id) {
                                    et.setText(recipesData.ingredient[i].quantity)
                                }
                                if (et.id == unit.id) {
                                    et.setText(recipesData.ingredient[i].unit)
                                }
                            }
                        }
                    }
                }
            }

            //步驟所需欄位
            for (i in 0 until (recipesData.step.size - 1)) {
                addStepView()
                Log.d("hank1", "新增1個步驟欄位}")
            }


            //放入步驟
            if (recipesData.step.isNotEmpty()) {
                for (i in 0 until stepList!!.childCount) {
                    if (stepList!!.getChildAt(i) is LinearLayoutCompat) {
                        val ll = stepList!!.getChildAt(i) as LinearLayoutCompat
                        for (j in 0 until ll.childCount) {
                            if (ll.getChildAt(j) is LinearLayoutCompat) {
                                val gg = ll.getChildAt(j) as LinearLayoutCompat
                                for (k in 0 until gg.childCount) {
                                    if (gg.getChildAt(k) is EditText) {
                                        val et = gg.getChildAt(k) as EditText
                                        if (et.id == depiction.id) {
                                            et.setText(recipesData.step[i].depiction)
                                        }
                                    } else if (gg.getChildAt(k) is ShapeableImageView) {
                                        val eImg = gg.getChildAt(k) as ShapeableImageView
                                        if (eImg.id == itemImage.id) {
//                                        eImg.setImageURI(Uri.parse(recipesData.step[i].images))
                                            bindImage(eImg, recipesData.step[i].images)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        binding.buttonEditDelete.setOnClickListener {
            if (recipesData != null) {
                viewModel.deleteRecipes(recipesData.id)
                findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
            }
        }


//        val et = ll.getChildAt(j) as EditText
//        if (et.id == depiction.id) {
//            Log.d("hank1", "第$i 組的depiction放了 => ${recipesData.step[i].depiction}")
//            et.setText(recipesData.step[i].depiction)
//                                Log.d("hank1", "檢查目前的sequence結果 => ${sequence}")
//                                Log.d("hank1", "檢查目前的listnewStep結果 => ${listnewStep}")
//        }
//                            val eImg = ll.getChildAt(j) as ShapeableImageView
//                            if(eImg.id == itemImage.id){
//                                eImg.setImageURI(Uri.parse(recipesData.step[i].images))
//                            }


//打開攝影機----------------------------------------------------------------------
        //打開相機按鈕
        binding.buttonEditCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = context?.let { it1 ->
                FileProvider.getUriForFile(
                    it1, "com.zongmin.cook.fileprovider",
                    photoFile!!
                )
            }
//            Log.d("hank1","尋找有沒有存在的uri，看fileProvider -> $fileProvider")
            uri = fileProvider

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
//            if(takePictureIntent.resolveActivity(MainActivity().packageManager)!= null){
//            }
            startActivityForResult(takePictureIntent, REQUEST_CODE)
        }

//圖片上傳-------------------------------------------------------------------------------------
        img1 = binding.imageEditTest
        img2 = binding.imageEditMain

        var storageReference = FirebaseStorage.getInstance().getReference()


        //上傳照片按鈕
        binding.buttonEditUpload.setOnClickListener {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_CONTACT_REQUEST)
        }


        var unusedFileName = 0L
        var mainImage = ""
        viewModel.mainUri.observe(viewLifecycleOwner) {
            Log.d("hank1", "觸發observe")
            val time = System.currentTimeMillis()
            val picStorage = storageReference.child("image$time")
//            Log.d("hank1", "點擊更換圖片1，看一下picStorage是啥 -> $picStorage")

            uri?.let { it1 ->
                picStorage.putFile(it1).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "上傳成功")
                        picStorage.downloadUrl.addOnSuccessListener {
                            Log.d("hank1", "看一下uri ->$it ")
                            mainImage = it.toString()
                            Glide.with(this /* context */)
                                .load(it)
                                .into(img2!!)

                            Log.d("hank1", "成功更換圖片")
                            if (unusedFileName == 0L) {
                                unusedFileName = time
                                Log.d("hank1", "沒有過去的圖片")
                            } else {
                                storageReference.child("image$unusedFileName").delete()
                                unusedFileName = time
                                Log.d("hank1", "刪除上次張上傳的圖片")
                            }
                        }.addOnFailureListener {
                            // Handle any errors
                        }
                    } else {
                        Log.d("hank1", "上傳失敗")
                    }
                }
            }

        }

//---------------------------------------------------------------------------------------------


        binding.buttonNewStep.setOnClickListener {
            addStepView()
        }

        binding.buttonNewIngredient.setOnClickListener {
            addView()
        }

        //儲存食譜-----------------------------------------------------------
        binding.buttonEditSave.setOnClickListener {
            val newRecipes = Summary()
            val listNewIngredient = mutableListOf<Ingredient>()
            val listNewStep = mutableListOf<Step>()

            //取得步驟內容
            var sequence = 1
            for (i in 0 until stepList!!.childCount) {
                Log.d("hank1", "check1")
                if (stepList!!.getChildAt(i) is LinearLayoutCompat) {
                    Log.d("hank1", "check2")
                    val ll = stepList!!.getChildAt(i) as LinearLayoutCompat
                    var depictionText = ""
                    var depictionUri = ""
                    for (j in 0 until ll.childCount) {
                        Log.d("hank1", "check3")
                        if (ll.getChildAt(j) is LinearLayoutCompat) {
                            Log.d("hank1", "check4")
                            val gg = ll.getChildAt(j) as LinearLayoutCompat
                            //這邊要施工，加K

                            for (k in 0 until gg.childCount) {
                                    if (gg.getChildAt(k) is EditText) {
                                        val et = gg.getChildAt(k) as EditText
                                        if (et.id == depiction.id) {
                                            depictionText = et.text.toString()
                                            Log.d("hank1","檢查edittext的輸入值 -> $depictionText")
                                        }
                                    } else if (gg.getChildAt(k) is ShapeableImageView) {
                                        val eImg = gg.getChildAt(k) as ShapeableImageView
                                        if (eImg.id == itemImage.id) {
                                            Log.d("hank1","要放入的depictionText是 -> $depictionText")
                                        depictionUri = "https://tokyo-kitchen.icook.network/uploads/recipe/cover/360711/84b41b35a1124c7c.jpg"
                                            listNewStep.add(Step("",sequence.toString(),depictionUri,depictionText,
                                                ToolType()
                                            ))
                                        }
                                    }
                                }
                        }
                    }
                    sequence++
                }
            }
//比對用---------------------------------------------------------------------------------------------


//取得食材內容-----------------------------------------------------------------------------------------
            for (i in 0 until ingredientList!!.childCount) {
                if (ingredientList!!.getChildAt(i) is LinearLayoutCompat) {
                    val ll = ingredientList!!.getChildAt(i) as LinearLayoutCompat
                    var itemName = ""
                    var itemQuantity = ""
                    var itemUnit = ""
                    for (j in 0 until ll.childCount) {
                        if (ll.getChildAt(j) is EditText) {
                            val et = ll.getChildAt(j) as EditText
                            if (et.id == ingredientName.id) {
//                                Log.d("hank1", "這組ingredientName放了 => ${et.text}")
                                itemName = et.text.toString()
                            }
                            if (et.id == quantity.id) {
//                                Log.d("hank1", "這組quantity放了 => ${et.text}")
                                itemQuantity = et.text.toString()

                            }
                            if (et.id == unit.id) {
//                                Log.d("hank1", "check6")
//                                Log.d("hank1", "這組unit放了 => ${et.text}")
                                itemUnit = et.text.toString()
                                listNewIngredient.add(

                                    Ingredient("", itemName, itemQuantity, itemUnit)
                                )
//                                Log.d("hank1", "檢查目前的listNewIngredient結果 => ${listNewIngredient}")
                            }
                        }
                    }
                }
            }

            newRecipes.name = binding.edittextEditName.text.toString()
            newRecipes.category = binding.spinnerEditCategory.selectedItem.toString()
            newRecipes.serving = binding.edittextServing.text.toString().toInt()
            newRecipes.mainImage = mainImage
            newRecipes.cookingTime = binding.edittextEditCookTime.text.toString()

            if (recipesData != null) {
                newRecipes.author = recipesData.author
                newRecipes.id = recipesData.id
            }else{
                newRecipes.author = UserManager.user.id
            }

            newRecipes.remark = binding.edittextEditRemark.text.toString()

                Log.d("hank1","我傳的newRecipes是 -> $newRecipes")
                Log.d("hank1","我傳的listNewIngredient是 -> $listNewIngredient")
                Log.d("hank1","我傳的listNewStep是 -> $listNewStep")

            viewModel.create(newRecipes, listNewIngredient, listNewStep)

            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
        }

        //取消
        binding.buttonEditCancel.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
        }


        addStepView()

        viewModel.createId.observe(viewLifecycleOwner){
            viewModel.setCollect(it)
        }

        return binding.root

    }


    private fun addView() {
        val aa: View = layoutInflater.inflate(R.layout.item_edit_ingredient, null, false)
        val cross: ImageView = aa.findViewById<View>(R.id.item_cross) as ImageView
        cross.visibility = View.VISIBLE
        cross.setOnClickListener {
            removeView(aa)
        }
        ingredientList?.addView(aa)

    }

    private fun removeView(view: View) {
        ingredientList?.removeView(view)

    }

    private fun addStepView() {

        val bb: View = layoutInflater.inflate(R.layout.item_edit_step, null, false)
        val cross: ImageView = bb.findViewById<View>(R.id.item_step_cross) as ImageView
        val stepImage = bb.findViewById<ShapeableImageView>(R.id.item_step_image)
        cross.visibility = View.VISIBLE

        cross.setOnClickListener {
            removeStepView(bb)
        }
        stepImage.setOnClickListener {
            Log.d("hank1", "我點到了第${it}個")
//            stepImage.setImageURI(uri)
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, ITEM_STEP_IMAGE)
            tempImageView = stepImage
//            viewModel.itemUri.observe(viewLifecycleOwner) {
//                it?.let {
//                    stepImage.setImageURI(it)
//                    viewModel.itemUri.value = null
//                }
//            }

        }
        stepList?.addView(bb)

    }

    private fun removeStepView(view: View) {
        stepList?.removeView(view)
    }


}
