package com.zongmin.cook.recipes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*


class RecipesViewModel : ViewModel() {

    val searchText = MutableLiveData<String>()
    val threeMeals = MutableLiveData("早餐")
    var date = MutableLiveData<Long>()

    init {
        date.value = getToday()
    }

    private fun getToday(): Long {
        val year = SimpleDateFormat("yyyy").format(Date())
        val month = SimpleDateFormat("MM").format(Date())
        val day = SimpleDateFormat("dd").format(Date())
        return GregorianCalendar(year.toInt(), month.toInt() - 1, day.toInt()).timeInMillis

    }


    fun showDateTimeDialog(context: Context) {
        val calendar = Calendar.getInstance()
        val nowYear = calendar.get(Calendar.YEAR)
        val nowMonth = calendar.get(Calendar.MONTH)
        val nowDay = calendar.get(Calendar.DAY_OF_MONTH)

        var showYear = 0
        var showMonth = 0
        var showDay = 0

        val timePickerOnDataSetListener =
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.YEAR, showYear)
                calendar.set(Calendar.MONTH, showMonth)
                calendar.set(Calendar.DAY_OF_MONTH, showDay)

            }

        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                showYear = year
                showMonth = month
                showDay = day
                val storedDate = GregorianCalendar(year, month, day)
                date.value = storedDate.timeInMillis
            }

        DatePickerDialog(
            context,

            datePickerOnDataSetListener,
            nowYear,
            nowMonth,
            nowDay
        ).show()
    }


}