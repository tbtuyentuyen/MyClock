package com.example.myclock

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val PREF_NAME = "SharedPreferences"
    private val KEY_IS_LOGIN = "is_login"
    private val KEY_ID = "id"
    private val KEY_REMEMBER_ME = "remember_me"
    private val KEY_IS_USER = "is_user"


    private val pref: SharedPreferences? = context.getSharedPreferences(PREF_NAME,
        Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor? = pref?.edit()

    fun setRememberMe(isRememberMe: Boolean) {
        editor?.putBoolean(KEY_REMEMBER_ME, isRememberMe)
        editor?.apply()
    }

    fun setUser(isUser: Boolean) {
        editor?.putBoolean(KEY_IS_USER, isUser)
        editor?.apply()
    }

    fun setLogin(isLogin: Boolean) {
        editor?.putBoolean(KEY_IS_LOGIN, isLogin)
        editor?.apply()
    }

    fun setInfoUser(id: String){
        editor?.putString(KEY_ID, id)
        editor?.apply()
    }


    fun isRememberMe(): Boolean?{
        return pref?.getBoolean(KEY_REMEMBER_ME, false)
    }

    fun isUser(): Boolean?{
        return pref?.getBoolean(KEY_IS_USER, false)
    }

    fun isLogin(): Boolean?{
        return pref?.getBoolean(KEY_IS_LOGIN, false)
    }

    fun getUserName(): String?{
        return pref?.getString(KEY_ID, "")
    }


    fun removeData(){
        editor?.putBoolean(KEY_IS_LOGIN, false)
        editor?.apply()
    }
}