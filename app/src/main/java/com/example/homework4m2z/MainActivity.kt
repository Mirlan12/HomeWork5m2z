package com.example.homework4m2z

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.homework4m2z.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var page = 1
    var keySearch = ""
    var adapter = ImageAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCLickers()
    }

    private fun initCLickers() {
        binding.buttonSer.setOnClickListener {
            page = 1
            keySearch = binding.editText.text.toString()
            adapter.cleanList()
            requestImage()
        }
        binding.buttonSer.setOnClickListener {
            if (keySearch != binding.editText.text.toString()) {
                adapter.cleanList()
                page = 1
                requestImage()
            } else {
                ++page
                requestImage()
            }
        }

    }

    private fun requestImage() {
        PixaService().api.getImage(binding.editText.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(call: Call<PixaModel>, response: Response<PixaModel>) {
                    if (response.isSuccessful) {
                        adapter.addImage(response.body()!!.hits)
                        binding.imgRecycler.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    Log.e("123456", "onFailure: ${t.message}")
                }
            })
    }
}


