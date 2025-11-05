package com.downloader.roznamcha.domain.usecases

import com.downloader.roznamcha.data.repository.PersonToDealRepository

class GetPersonByKhata(
    private val personToDealRepository: PersonToDealRepository
) {
    suspend operator fun invoke(khataNumber: Int) =
        personToDealRepository.getByKhataNumber(khataNumber)

}