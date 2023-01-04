package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.domain.FilterAsteroids
import com.udacity.asteroidradar.ui.main.adapter.AsteroidClickListener
import com.udacity.asteroidradar.utils.Constants

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, MainViewModel.Factory(activity.application, asteroidCallBack)).get(
            MainViewModel::class.java
        )
    }

    private val asteroidCallBack = AsteroidClickListener {
        findNavController().apply {
            navigate(MainFragmentDirections.actionShowDetail(it))
        }
    }

    private lateinit var binding : FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.invalidateAll()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_all_menu->{
               viewModel.filterAsteroids(FilterAsteroids.WEAK_ASTEROID)
            }
            R.id.show_rent_menu->{
                viewModel.filterAsteroids(FilterAsteroids.TODAY_ASTEROID)
            }
            R.id.show_buy_menu->{
                viewModel.filterAsteroids(FilterAsteroids.SAVED_ASTEROID)
            }

        }
        binding.invalidateAll()
        return true
    }
}
