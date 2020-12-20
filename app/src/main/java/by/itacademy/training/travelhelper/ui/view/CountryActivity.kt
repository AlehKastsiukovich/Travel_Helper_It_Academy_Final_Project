package by.itacademy.training.travelhelper.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.itacademy.training.travelhelper.R
import by.itacademy.training.travelhelper.databinding.ActivityCountryBinding
import by.itacademy.training.travelhelper.di.component.CountryActivityComponent
import by.itacademy.training.travelhelper.ui.app.App
import by.itacademy.training.travelhelper.ui.viewmodel.CountryDescriptionViewModel
import javax.inject.Inject

class CountryActivity : AppCompatActivity() {

    @Inject lateinit var model: CountryDescriptionViewModel

    lateinit var component: CountryActivityComponent

    private lateinit var binding: ActivityCountryBinding
    private lateinit var currentFragment: Fragment
    private lateinit var countryDescriptionFragment: CountryDescriptionFragment
    private lateinit var videoListFragment: VideoListFragment
    private lateinit var routeListFragment: RouteListFragment
    private lateinit var mapsFragment: MapsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        initComponent()
        inject()
        setContentView(binding.root)

        setCurrentCountry()
        initFragments()
        addFragmentsToTransaction()
        onNavigationBarItemSelected()
        setUpActionBar()
    }

    private fun initComponent() {
        component = (application as App).appComponent
            .countryActivitySubComponentBuilder()
            .with(this)
            .build()
    }

    private fun inject() {
        component.inject(this)
    }

    private fun initFragments() {
        countryDescriptionFragment = CountryDescriptionFragment()
        videoListFragment = VideoListFragment()
        routeListFragment = RouteListFragment()
        mapsFragment = MapsFragment()
        currentFragment = countryDescriptionFragment
    }

    private fun addFragmentsToTransaction() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentsContainer, countryDescriptionFragment)
            add(R.id.fragmentsContainer, mapsFragment).hide(mapsFragment)
            add(R.id.fragmentsContainer, videoListFragment).hide(videoListFragment)
            commit()
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.appToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onNavigationBarItemSelected() {
        binding.bottomNavigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.countryDescription -> setCurrentFragment(countryDescriptionFragment)
                R.id.countryInfoVideoList -> setCurrentFragment(videoListFragment)
                R.id.routes -> setCurrentFragment(mapsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(openFragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            hide(currentFragment)
            show(openFragment)
            commit()
        }
        currentFragment = openFragment
    }

    private fun setCurrentCountry() {
        val countryName = intent.extras?.getString(resources.getString(R.string.country_key))
        countryName?.let { model.setCurrentCountry(it) }
    }
}
