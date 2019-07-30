package de.joyn.myapplication.di.app

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.joyn.myapplication.App
import de.joyn.myapplication.di.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    AndroidSupportInjectionModule::class,
    BuilderModule::class, ViewModelModule::class,
    NetworkModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
}