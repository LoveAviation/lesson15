package com.example.lesson15

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val wordDao: WordDao):ViewModel() {

    val topWordsList = this.wordDao.getTopFive()

    fun insertWord(word: String){
        viewModelScope.launch {
            val clone: Word? = wordDao.search(word)
            if(clone?.word == null) {
                wordDao.insert(
                    Word(
                        word,
                        1
                    )
                )
            }else{
                wordDao.update(
                    Word(
                        clone.word,
                        clone.counts + 1
                    )
                )
            }
        }
    }

    fun clearAll(){
        viewModelScope.launch {
            wordDao.delete()
        }
    }
}