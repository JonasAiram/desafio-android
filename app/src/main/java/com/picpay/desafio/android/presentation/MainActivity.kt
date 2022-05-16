package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.adapters.user.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private var positionScroll: Int = 0
    private lateinit var progressBar: ProgressBar
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { UserListAdapter() }
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        progressBar = binding.userListProgressBar
        progressBar.visibility = View.VISIBLE

        initObservers()
        viewModel.listUsers()
    }

    override fun onPause() {
        super.onPause()
        positionScroll = binding.nestedScrollView.verticalScrollbarPosition
    }

    override fun onResume() {
        super.onResume()
        binding.nestedScrollView.verticalScrollbarPosition = positionScroll
    }

    private fun initObservers() {
        viewModel.users.observe(this) { state ->
            when (state) {
                MainViewModel.State.Loading -> {
                    Toast
                        .makeText(this@MainActivity,"Loading", Toast.LENGTH_SHORT)
                        .show()
                    progressBar.visibility = View.VISIBLE
                }
                is MainViewModel.State.Error -> {
                    progressBar.visibility = View.GONE
                    Toast
                        .makeText(this@MainActivity, state.error.message, Toast.LENGTH_SHORT)
                        .show()
                }
                is MainViewModel.State.Success -> {
                    progressBar.visibility = View.GONE
                    adapter.submitList(state.list)
                    Toast
                        .makeText(this@MainActivity, state.list.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Log.e("Main", state.list.toString())
                }

            }
        }
    }

}
