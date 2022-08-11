package com.naram.domain.repo

import com.naram.domain.model.FloRepo

interface FloRepository {
    suspend fun getRepos(): FloRepo
}