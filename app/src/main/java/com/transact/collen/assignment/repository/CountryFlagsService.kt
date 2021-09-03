package com.transact.collen.assignment.repository


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CountryFlagsService {

    @Headers("Content-Type: application/octet-stream")
    @GET("{country_code}/shiny/{size}.png")
    suspend fun getCountryFlags(
        @Path("country_code") countryCode: String,
        @Path("size") size: Int = 64
    ): ResponseBody
}