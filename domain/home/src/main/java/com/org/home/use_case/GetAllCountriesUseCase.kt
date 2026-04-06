package com.org.home.use_case

import com.org.home.repository.HomeRepository
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke() = homeRepository.getAvailableCountries()
}
