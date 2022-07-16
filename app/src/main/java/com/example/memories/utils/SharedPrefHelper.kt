package com.example.memories.utils

import android.content.Context
import android.content.SharedPreferences


import com.google.gson.Gson


class SharedPrefHelper(var context: Context)
{

    private var sharedPreferences: SharedPreferences? = null
    private var editor:SharedPreferences.Editor?=null

    init
    {
        sharedPreferences = context.getSharedPreferences("MemoriesPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }






    /// Package prices method

    //  monthly

//    fun setMonthlyData(purchasePlan: MonthlyData)
//    {
//        val data = Gson().toJson(purchasePlan)
//        editor?.putString(Constants.MONTHLY_DATA, data)
//        editor?.commit()
//    } //
//
//
//
//    fun getMonthlyData() : MonthlyData
//    {
//        val data = sharedPreferences?.getString(Constants.MONTHLY_DATA,"")
//        return Gson().fromJson(data, MonthlyData::class.java)
//    }







}
