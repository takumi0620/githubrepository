package com.example.repository.list.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repository.list.model.dao.RepositoryDataDao
import com.example.repository.list.model.data.RepositoryData

@Database(entities = [RepositoryData::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun repositoryDataDao(): RepositoryDataDao
}