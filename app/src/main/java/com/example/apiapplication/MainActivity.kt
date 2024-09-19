package com.example.apiapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.apiapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var SWAdapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        setupRecycleView()
        SWAdapter.onItemClick = {
            val intent = Intent(this, CharacterView::class.java)
            intent.putExtra("name",it)
            startActivity(intent)
        }
        fetchData()
    }

    private fun setupRecycleView() = binding.rvStarWars.apply {
        SWAdapter = RVAdapter()
        adapter = SWAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun fetchData() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                binding.progressBar.isVisible = true
                val response = try {
                    RetrofitInstance.api.getStarWars()
                } catch (e: IOException) {
                    e(TAG, "IOException: No internet connection")
                    binding.progressBar.isVisible = false
                    makeInvisible()
                    return@repeatOnLifecycle
                } catch (e: HttpException) {
                    e(TAG, "onCreate: HTTP EXCEPTION", null)
                    return@repeatOnLifecycle
                }
                Log.d(TAG, "onCreate: We are at the if condition")
                if (response.isSuccessful && response.body() != null) {
                    SWAdapter.starWars = response.body()!!
                } else {
                    e(TAG, "Response not successful: ${response.code()} - ${response.errorBody()?.string()}")
                }
                binding.progressBar.isVisible = false
            }
        }
    }

    override fun onRefresh() {
        Log.d(TAG, "onRefresh: We are refreshing now")
        makeInvisible()
        fetchData()
        Log.d(TAG, "onRefresh: Done with fetch data")
        binding.swipeRefreshLayout.isRefreshing = false
        resetVisibility()
        Log.d(TAG, "onRefresh: done")
    }

    private fun makeInvisible() {
        binding.noInternet.isVisible = true
        binding.editTextText.isVisible = false
        binding.rvStarWars.isVisible = false
    }

    private fun resetVisibility() {
        binding.progressBar.isVisible = true
        binding.editTextText.isVisible = true
        binding.rvStarWars.isVisible = true
        binding.noInternet.isVisible = false
    }

}
