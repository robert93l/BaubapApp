package com.example.baubapapp.di

import com.example.baubapapp.data.repository.LoginRepositoryImpl
import com.example.baubapapp.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinds {

  @Binds
  abstract fun bindLoginRepositoryImpl(impl: LoginRepositoryImpl): LoginRepository
}