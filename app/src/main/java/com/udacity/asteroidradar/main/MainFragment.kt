package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.bindAsteroidStatusImage
import com.udacity.asteroidradar.databinding.AstroidItemBinding
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity){

        }
        ViewModelProvider(this,MainViewModelFactory(activity.application))[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        val itemBinding = AstroidItemBinding.inflate(inflater)
        itemBinding.lifecycleOwner = this
        itemBinding.viewmodel = viewModel

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidsAdapter(AsteroidsAdapter.OnClickListener{
            viewModel.displayAsteroidDetails(it)
        })



        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if(it != null){
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsComplete()
            }
        })



        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_weekly_asteroids -> viewModel.getAllAsteroids()
            R.id.show_daily_asteroids -> viewModel.getDailyAsteroids()
            else -> viewModel.getAllAsteroids()
        }
        return true
    }
}
