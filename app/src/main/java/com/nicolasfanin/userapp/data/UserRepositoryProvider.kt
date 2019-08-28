package com.nicolasfanin.userapp.data

object UserRepositoryProvider {

    fun provideUserRepository(): UserRepository {
        return UserRepository(RandomUserService.Factory.create())
    }

}