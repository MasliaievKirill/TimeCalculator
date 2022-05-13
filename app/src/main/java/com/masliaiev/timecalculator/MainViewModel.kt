package com.masliaiev.timecalculator

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val application: Application) : ViewModel() {


    private val sharedPreferences =
        application.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    private val _savedResult = MutableLiveData<String>()
    val savedResult: LiveData<String>
        get() = _savedResult

    private var resultInMinutes = 0
    private var savedResultInMinutes = 0


    init {
        _result.value = String.format(application.getString(R.string.result), minutesToTime(resultInMinutes))
        getSavedResult()
    }

    fun save(){
        sharedPreferences.edit().putInt(SAVED_VALUE, resultInMinutes).apply()
        getSavedResult()
    }

    fun calculateResult(value: Double){
        val minutes = timeToMinutes(value)
        resultInMinutes += minutes
        _result.value = String.format(application.getString(R.string.result), minutesToTime(resultInMinutes))
    }

    fun resetResult(){
        resultInMinutes = 0
        _result.value = String.format(application.getString(R.string.result), minutesToTime(resultInMinutes))
    }

    private fun getSavedResult(){
        savedResultInMinutes = sharedPreferences.getInt(SAVED_VALUE, 0)
        _savedResult.value = String.format(application.getString(R.string.saved_result), minutesToTime(savedResultInMinutes))
    }

    private fun timeToMinutes(time: Double): Int{
        val hours = time.toInt()
        val minutes = (time - hours) * 100
        return ((hours * 60) + minutes).toInt()
    }

    private fun minutesToTime(minutes: Int): Double{
        val hours = minutes / 60
        val min = (minutes % 60).toDouble() / 100
        return hours + min
    }

    companion object {
        private const val APP_PREFS = "app_prefs"
        private const val SAVED_VALUE = "saved_value"
    }
}