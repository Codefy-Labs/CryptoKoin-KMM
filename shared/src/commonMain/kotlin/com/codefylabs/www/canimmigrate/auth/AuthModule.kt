package com.codefylabs.www.canimmigrate.auth

import com.codefylabs.www.canimmigrate.auth.data.local.AuthPersistence
import com.codefylabs.www.canimmigrate.auth.data.local.AuthPersistenceImpl
import com.codefylabs.www.canimmigrate.auth.data.local.entity.LocalDataObject
import com.codefylabs.www.canimmigrate.auth.data.local.entity.SessionObject
import com.codefylabs.www.canimmigrate.auth.data.local.entity.SurveyDataObject
import com.codefylabs.www.canimmigrate.auth.data.remote.api.AuthAPI
import com.codefylabs.www.canimmigrate.auth.data.remote.api.AuthApiImp
import com.codefylabs.www.canimmigrate.auth.data.repo.AuthRepositoryImpl
import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.auth.domain.usescases.ForgotPasswordUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.ForgotPasswordUseCaseImpl
import com.codefylabs.www.canimmigrate.auth.domain.usescases.LocalDataUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.LocalDataUseCaseImpl
import com.codefylabs.www.canimmigrate.auth.domain.usescases.LoginUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.LoginUseCaseImpl
import com.codefylabs.www.canimmigrate.auth.domain.usescases.ResetPasswordUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.ResetPasswordUseCaseImpl
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SessionUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SessionUseCaseImpl
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SignupUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SignupUseCaseImpl
import com.codefylabs.www.canimmigrate.auth.domain.usescases.UpdatePasswordUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.UpdatePasswordUseCaseImpl
import com.codefylabs.www.canimmigrate.auth.domain.usescases.UserPhoneNoUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.UserPhoneNoUseCaseImpl
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val authModule = module {

    single<AuthPersistence> {
        AuthPersistenceImpl(
            Realm.open(
                RealmConfiguration.Builder(
                    schema = setOf(
                        SessionObject::class,
                        LocalDataObject::class,
                        SurveyDataObject::class
                    )
                ).build()
            )
        )
    }

    single<AuthAPI> {
        AuthApiImp(get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            get(),
            get(),
            get()
        )
    }

    single<SessionUseCase> {
        SessionUseCaseImpl(get())
    }

    single<LocalDataUseCase> {
        LocalDataUseCaseImpl(get())
    }

    single<LoginUseCase> {
        LoginUseCaseImpl(get())
    }

    single<SignupUseCase> {
        SignupUseCaseImpl(get())
    }

    single<ForgotPasswordUseCase> {
        ForgotPasswordUseCaseImpl(get())
    }

    single<ResetPasswordUseCase> {
        ResetPasswordUseCaseImpl(get())
    }

    single<UserPhoneNoUseCase> {
        UserPhoneNoUseCaseImpl(get())
    }

    single<UpdatePasswordUseCase>() {
        UpdatePasswordUseCaseImpl(get())
    }
}