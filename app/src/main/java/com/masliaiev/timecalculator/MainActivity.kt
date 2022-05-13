package com.masliaiev.timecalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.masliaiev.timecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModelFactory by lazy {
        ViewModelFactory(application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalculate.setOnClickListener {
            if (binding.etTimeEnter.text.isNotEmpty()){
                viewModel.calculateResult(binding.etTimeEnter.text.toString().toDouble())
                binding.etTimeEnter.text.clear()
            } else {
                Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show()
            }
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                binding.etTimeEnter.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
        binding.buttonSaveResult.setOnClickListener {
            viewModel.save()
        }
        binding.buttonReset.setOnClickListener {
            viewModel.resetResult()
            binding.etTimeEnter.text.clear()
        }

        viewModel.result.observe(this){
            binding.tvResult.text = it
        }
        viewModel.savedResult.observe(this){
            binding.tvSavedResult.text = it
        }

    }
}