package com.pramodk.retrofitdemo

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.pramodk.retrofitdemo.api.ApiRequest
import com.pramodk.retrofitdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BASE_URL = "https://cat-fact.herokuapp.com/"

class MainActivity : AppCompatActivity() {
    private val TAG = "MAIN_ACTIVITY"
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getCurrentData()
        binding.layoutGenerateNewFact.setOnClickListener {
            getCurrentData()
        }
    }

    private fun getCurrentData() {
        binding.tvTextView.visibility = View.INVISIBLE
        binding.tvTimeStamp.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
        val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getCatFacts().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d(TAG, data!!.text)
                    withContext(Dispatchers.Main) {
                        binding.tvTextView.visibility = View.VISIBLE
                        binding.tvTimeStamp.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.tvTextView.text = data.text
                        binding.tvTimeStamp.text = data.createdAt
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "error occurred!!!", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}