package com.zongmin.cook.recipes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.util.MutableLong
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.source.CookRepository
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


class RecipesViewModel: ViewModel() {
//class RecipesViewModel(
//    private val cookRepository: CookRepository
//): ViewModel() {

    val searchText = MutableLiveData<String>()

    val threeMeals = MutableLiveData("早餐")

//    val date = MutableLiveData(SimpleDateFormat("MM/dd").format(Date()))
//    val date = MutableLiveData("")

//     var _date = MutableLiveData<String>()
//     var date = MutableLiveData<String>()
     var date = MutableLiveData<Long>()
//     var date = MutableLong(0)

//    val date: LiveData<String>
//        get() = _date

    private val _timeStamp = MutableLiveData<Timestamp>()

    val timeStamp: LiveData<Timestamp>
        get() = _timeStamp


    init {
        getDate()
    }

    private fun getDate(){
//        _date.value = SimpleDateFormat("MM/dd").format(Date())
//        date.value = SimpleDateFormat("MM/dd").format(Date())

        date.value = System.currentTimeMillis()
//        date = System.currentTimeMillis()
    }


    fun showDateTimeDialog(context: Context) {
        val calendar = Calendar.getInstance()
        val nowYear = calendar.get(Calendar.YEAR)
        val nowMonth = calendar.get(Calendar.MONTH)
        val nowDay = calendar.get(Calendar.DAY_OF_MONTH)
        val nowHour = calendar.get(Calendar.HOUR_OF_DAY)
        val nowMinute = calendar.get(Calendar.MINUTE)

        var showYear = 0
        var showMonth = 0
        var showDay = 0
//        var showHour: Int
//        var showMinute: Int
//        calendar.

        val timePickerOnDataSetListener =
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
//                showHour = hour
//                showMinute = minute
//                Logger.i("hour: $showHour, minute: $showMinute")
//                Logger.i("Dialog selected time year: $showYear, month: $showMonth, day: $showDay, hour: $showHour, minute: $showMinute")
                calendar.set(Calendar.YEAR, showYear)
                calendar.set(Calendar.MONTH, showMonth)
                calendar.set(Calendar.DAY_OF_MONTH, showDay)

//                calendar.set(Calendar.HOUR_OF_DAY, showHour)
//                calendar.set(Calendar.MINUTE, showMinute)
//                Logger.i("Dialog selected calendar.time = ${calendar.time}")
                Log.d("hank1","看一下最後拿到的時間是 -> ${calendar}")
                Log.d("hank1","看一下最後拿到的時間是 -> ${calendar.time}")
                Log.d("hank1","看一下最後拿到的時間是 -> ${calendar.timeInMillis}")
//                _timeStamp.value = Timestamp(calendar.time)
//                _timeStamp.value = calendar.time
            }

        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
//                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE)
//                setText(sdf.format(calendar.time))
                showYear = year
                showMonth = month
                showDay = day
//                Logger.i("year: $showYear, month: $showMonth, day: $showDay")
//                Log.d("hank1","看一下最後拿到的時間是 -> ${calendar.time}")
//                Log.d("hank1","看一下最後拿到的時間是 -> ${calendar.timeInMillis}")
                Log.d("hank1","看一下最後拿到的日期是 -> ${year}/$month/$day")

                val storedDate = GregorianCalendar(year, month, day)
                Log.d("hank1","看一下最後拿到的日期轉毫秒是 -> ${storedDate.timeInMillis}")
                date.value = storedDate.timeInMillis
//                TimePickerDialog(
//                    context,
//                    timePickerOnDataSetListener,
//                    nowHour,
//                    nowMinute,
//                    true).show()
            }

        DatePickerDialog(
            context,

            datePickerOnDataSetListener,
            nowYear,
            nowMonth,
            nowDay).show()
    }











}