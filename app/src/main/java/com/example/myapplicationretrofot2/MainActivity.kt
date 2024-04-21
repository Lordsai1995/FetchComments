package com.example.myapplicationretrofot2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationretrofot2.RetrofitInstance
import com.example.myapplicationretrofot2.databinding.ActivityMainBinding
import com.example.myapplicationretrofot2.CommentAdapter
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var commentAdapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.progressbar.isVisible = true
            val response = try {
                RetrofitInstance().api.getComments()
            } catch (e: IOException) {
                Log.e(TAG, "IOException,you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException,unexpected response")
                binding.progressbar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                commentAdapter.comment = response.body()!!
            } else {
                Log.e(TAG, " response not successful")
            }
            binding.progressbar.isVisible = false
        }

    }

    private fun setupRecyclerView() = binding.rvComment.apply {
        adapter = CommentAdapter()
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}