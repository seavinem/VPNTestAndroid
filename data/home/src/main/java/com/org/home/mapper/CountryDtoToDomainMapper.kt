package com.org.home.mapper

import com.org.home.model.CountryDomain
import com.org.network.model.CountryDto
import javax.inject.Inject

class CountryDtoToDomainMapper @Inject constructor() {

    operator fun invoke(from: CountryDto): CountryDomain = with(from) {
        CountryDomain(
            countryName = name?.common.orEmpty(),
            capital = capital?.firstOrNull().orEmpty(),
            flagUrl = flags?.svg.orEmpty()
        )
    }
}