package com.naram.domain

import com.naram.domain.model.FloRepo
import com.naram.domain.repo.FloRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FloRepositoriesUseCase(
    private val floRepository: FloRepository
) {
    operator fun invoke(
        scope: CoroutineScope,
        onResult: (FloRepo) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                floRepository.getRepos()
            }
            onResult(deferred.await())
        }
    }
}