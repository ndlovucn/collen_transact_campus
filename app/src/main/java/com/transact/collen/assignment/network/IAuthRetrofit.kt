package com.mywallst.collen.assignment.network

import okhttp3.OkHttpClient

interface IAuthRetrofit {
    fun <T> create(api: Class<T>): T
    fun setupRetrofit()
    fun client(): OkHttpClient
}
