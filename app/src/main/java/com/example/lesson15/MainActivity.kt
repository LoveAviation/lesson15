package com.example.lesson15

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lesson15.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao: WordDao = (application as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.AddButton.setOnClickListener{
            if(isValidString(binding.editText.text.toString().trim())){
                mainViewModel.insertWord(binding.editText.text.toString())
            }else{
                Snackbar.make(binding.root, "Invalid word", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.ClearButton.setOnClickListener {
            mainViewModel.clearAll()
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.topWordsList
                .collect{ word ->
                    binding.textView.text = word.joinToString(
                        separator = "\r\n"
                    )
                }
        }
    }

    private fun isValidString(input: String): Boolean {
        val regex = Regex("[a-zA-Z]+(-[a-zA-Z]+)?")
        return input.matches(regex)
    }
}