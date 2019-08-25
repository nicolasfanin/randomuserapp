package com.nicolasfanin.userapp.ui.data

object UserRepositoryProvider {

    fun provideUserRepository(): UserRepository {
        return UserRepository(RandomUserService.Factory.create())
    }

}