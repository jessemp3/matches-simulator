package com.sporthela.mathes_simulator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.sporthela.mathes_simulator.databinding.ActivityMainBinding

class MainActivityKotlin : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

         binding =  ActivityMainBinding.inflate(layoutInflater)

//        binding.firstText.text = "oláa, testando com o kotlin"
        setContentView(binding.root)
    }
}