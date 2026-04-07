package com.org.home.mapper

import com.org.database.model.CountryEntity
import com.org.home.model.CountryDomain
import javax.inject.Inject

class CountryDomainToEntityMapper @Inject constructor() {

    operator fun invoke(from: CountryDomain): CountryEntity = with(from) {
        CountryEntity(
            countryName = countryName,
            capital = capital,
            flagUrl = flagUrl
        )
    }
}
