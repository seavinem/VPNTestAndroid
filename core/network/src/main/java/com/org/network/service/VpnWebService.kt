package com.org.network.service

import com.org.network.model.CountryDto
import retrofit2.http.GET

interface VpnWebService {

    @GET("/v3.1/all?fields=name,capital,flags")
    suspend fun getAvailableCountries(): List<CountryDto>
}
