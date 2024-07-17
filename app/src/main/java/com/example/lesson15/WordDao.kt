package com.example.lesson15

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM dictionary ORDER BY counts DESC LIMIT 5")
    fun getTopFive(): Flow<List<Word>>

    @Insert
    suspend fun insert(dictionary: Word)

    @Query("SELECT * FROM dictionary WHERE word = :word LIMIT 1")
    suspend fun search(word: String) : Word?

    @Query("DELETE FROM dictionary")
    suspend fun delete()

    @Update
    suspend fun update(dictionary: Word)
}