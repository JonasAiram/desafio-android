package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.adapters.user.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

//    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
//    private lateinit var adapter: UserListAdapter

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { UserListAdapter() }
    private val viewModel by viewModel<MainViewModel>()

//    private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
//    private val gson: Gson by lazy { GsonBuilder().create() }

//    private val okHttp: OkHttpClient by lazy {
//        OkHttpClient.Builder()
//            .build()
//    }

//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(url)
//            .client(okHttp)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//    }

//    private val service: PicPayService by lazy {
//        retrofit.create(PicPayService::class.java)
//    }

    override fun onResume() {
        super.onResume()
        viewModel.listUsers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        progressBar = binding.userListProgressBar
        progressBar.visibility = View.VISIBLE

        initObservers()
        viewModel.listUsers()
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

//    override fun onResume() {
//        super.onResume()
//
//        recyclerView = findViewById(R.id.recyclerView)
//        progressBar = findViewById(R.id.user_list_progress_bar)
//
//        adapter = UserListAdapter()
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        progressBar.visibility = View.VISIBLE
//        service.getUsers()
//            .enqueue(object : Callback<List<User>> {
//                override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                    val message = getString(R.string.error)
//
//                    progressBar.visibility = View.GONE
//                    recyclerView.visibility = View.GONE
//
//                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                    progressBar.visibility = View.GONE
//
//                    adapter.users = response.body()!!
//                }
//            })
//    }
}
