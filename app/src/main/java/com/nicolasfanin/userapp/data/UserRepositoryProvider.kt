package com.nicolasfanin.userapp.data

import com.nicolasfanin.userapp.data.repository.UserRepository

object UserRepositoryProvider {

    fun provideUserRepository(): UserRepository {
        return UserRepository(RandomUserService.create())
    }

}
