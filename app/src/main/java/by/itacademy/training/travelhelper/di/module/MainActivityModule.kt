package by.itacademy.training.travelhelper.di.module

import androidx.lifecycle.ViewModelProvider
import by.itacademy.training.travelhelper.ui.adapter.OnCountryItemClickListener
import by.itacademy.training.travelhelper.ui.view.MainActivity
import by.itacademy.training.travelhelper.ui.viewmodel.CountriesListViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideCountriesListViewModel(activity: MainActivity): CountriesListViewModel =
        ViewModelProvider(activity).get(CountriesListViewModel::class.java)

    @Provides
    fun provideOnCountryItemClickListener(activity: MainActivity): OnCountryItemClickListener =
        activity
}
