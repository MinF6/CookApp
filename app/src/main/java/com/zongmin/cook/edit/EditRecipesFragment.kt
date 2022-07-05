package com.zongmin.cook.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import com.google.firebase.storage.FirebaseStorage
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.R
import com.zongmin.cook.data.*
import com.zongmin.cook.databinding.FragmentEditRecipesBinding
import com.zongmin.cook.databinding.ItemEditIngredientBinding
import com.zongmin.cook.databinding.ItemEditStepBinding
import com.zongmin.cook.ext.getVmFactory
import java.io.ByteArrayOutputStream
import java.io.File


class EditRecipesFragment : Fragment() {

    var ingredientList: LinearLayout? = null
    var stepList: LinearLayout? = null

    var intent: Intent? = null
    var uri: Uri? = null
    var PICK_CONTACT_REQUEST = 1
    var REQUEST_CODE = 42
    var img1: ImageView? = null
    var img2: ImageView? = null

    val FILE_NAME = "photo.jpg"
    var photoFile: File? = null

    private val viewModel by viewModels<EditRecipesViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data);
        //來自檔案
        if (requestCode == PICK_CONTACT_REQUEST) {
            uri = data?.data
            img1?.setImageURI(uri)
            Log.d("hank1","看一下上傳拿到的uri是啥 -> $uri")

        }

        //來自相機
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            val takeImage = data?.extras?.get("data") as Bitmap

            val takeImage = BitmapFactory.decodeFile(photoFile?.absolutePath)


            Log.d("hank1","看一下拍照拿的的takeImage是啥 -> $takeImage")
            img1?.setImageBitmap(takeImage)

            uri = context?.let { getImageUri(it,takeImage) }
//            img1?.setImageURI(takeImage)
        }



        super.onActivityResult(requestCode, resultCode, data);


    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

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

        val ingredientName = binding2.itemEdittextIngredientName
        val quantity = binding2.itemEdittextQuantity
        val unit = binding2.itemEdittextUnit
        ingredientList = binding.editIngredientList
        stepList = binding.editStepList
        val depiction = binding3.itemEdittextStepDepiction

//打開攝影機----------------------------------------------------------------------

        binding.buttonEditCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)


            val fileProvider = context?.let { it1 -> FileProvider.getUriForFile(it1,"com.zongmin.cook.fileprovider",
                photoFile!!
            ) }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)


//            if(takePictureIntent.resolveActivity(MainActivity().packageManager)!= null){
//
//            }

            startActivityForResult(takePictureIntent,REQUEST_CODE)





        }




//打開攝影機結束----------------------------------------------------------------------


//圖片上傳-------------------------------------------------------------------------------------
        img1 = binding.imageEditTest
        img2 = binding.imageEditMain

        var storageReference = FirebaseStorage.getInstance().getReference()


        //換置圖片
        binding.buttonEditUpload.setOnClickListener {
            intent = Intent()
            intent!!.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent!!.setType("image/*")
            intent!!.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, PICK_CONTACT_REQUEST)

        }


        var apple = 0L
        binding.buttonEditChangeImage.setOnClickListener {

            //上傳圖片   應該要改去viewModel用coroutineScope.launch
            val time = System.currentTimeMillis()
            val picStorage = storageReference.child("image$time")
            Log.d("hank1", "點擊更換圖片1，看一下picStorage是啥 -> $picStorage")

            uri?.let { it1 ->
                picStorage.putFile(it1).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hank1", "上傳成功")
                        picStorage.downloadUrl.addOnSuccessListener {
                            Log.d("hank1", "看一下uri ->$it ")
                            Glide.with(this /* context */)
                                .load(it)
                                .into(img2!!)

                            Log.d("hank1", "成功更換圖片")
                            if(apple == 0L){
                                apple = time
                                Log.d("hank1", "沒有過去的圖片")
                            }else{
                                storageReference.child("image$apple").delete()
                                apple = time
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


        binding.buttonEditSave.setOnClickListener {
            val newRecipes = Summary()
            val listNewIngredient = mutableListOf<Ingredient>()
            val listnewStep = mutableListOf<Step>()

            //取得步驟內容
            var sequence = 1
            for (i in 0 until stepList!!.childCount) {
                if (stepList!!.getChildAt(i) is LinearLayoutCompat) {
                    val ll = stepList!!.getChildAt(i) as LinearLayoutCompat
                    for (j in 0 until ll.childCount) {
                        if (ll.getChildAt(j) is EditText) {
                            val et = ll.getChildAt(j) as EditText
                            if (et.id == depiction!!.id) {
//                                Log.d("hank1", "這組depiction放了 => ${et.text}")
                                listnewStep.add(
                                    Step(
                                        "",
                                        sequence.toString(),
                                        listOf("https://tokyo-kitchen.icook.network/uploads/recipe/cover/407229/1e9aa981b9a4a97f.jpg"),
                                        et.text.toString(),
                                        ToolType()
                                    )
                                )
                                sequence++
//                                Log.d("hank1", "檢查目前的sequence結果 => ${sequence}")
//                                Log.d("hank1", "檢查目前的listnewStep結果 => ${listnewStep}")
                            }
                        }
                    }
                }
            }

            //取得食材內容
            for (i in 0 until ingredientList!!.childCount) {
                if (ingredientList!!.getChildAt(i) is LinearLayoutCompat) {
                    val ll = ingredientList!!.getChildAt(i) as LinearLayoutCompat
                    var itemName = ""
                    var itemQuantity = ""
                    var itemUnit = ""
                    for (j in 0 until ll.childCount) {
                        if (ll.getChildAt(j) is EditText) {
                            val et = ll.getChildAt(j) as EditText
                            if (et.id == ingredientName!!.id) {
//                                Log.d("hank1", "這組ingredientName放了 => ${et.text}")
                                itemName = et.text.toString()
                            }
                            if (et.id == quantity!!.id) {
//                                Log.d("hank1", "這組quantity放了 => ${et.text}")
                                itemQuantity = et.text.toString()
                            }
                            if (et.id == unit!!.id) {
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
            newRecipes.mainImage =
                "https://tokyo-kitchen.icook.network/uploads/recipe/cover/406300/ec1128b22f4092d6.jpg"
            newRecipes.cookingTime = binding.edittextEditCookTime.text.toString()
            newRecipes.author = "W5bXC4hAbvs5zOYY7i5R"
            newRecipes.remark = binding.edittextEditRemark.text.toString()
            viewModel.create(newRecipes, listNewIngredient, listnewStep)

            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
        }

        //取消
        binding.buttonEditCancel.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
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
        cross.visibility = View.VISIBLE

        cross.setOnClickListener {
            removeStepView(bb)
        }
        stepList?.addView(bb)

    }

    private fun removeStepView(view: View) {
        stepList?.removeView(view)
    }


}
