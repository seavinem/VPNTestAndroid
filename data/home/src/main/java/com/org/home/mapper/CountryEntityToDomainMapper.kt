package com.org.home.mapper

import com.org.database.model.CountryEntity
import com.org.home.model.CountryDomain
import javax.inject.Inject

class CountryEntityToDomainMapper @Inject constructor() {

    operator fun invoke(from: CountryEntity): CountryDomain = with(from) {
        CountryDomain(
            countryName = countryName,
            capital = capital,
            flagUrl = flagUrl
        )
    }
}
