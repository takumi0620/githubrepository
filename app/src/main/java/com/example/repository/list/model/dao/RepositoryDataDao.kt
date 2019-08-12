package com.example.repository.list.model.dao

import androidx.room.*
import com.example.repository.list.model.data.RepositoryData

@Dao
interface RepositoryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createRepositoryData(data: RepositoryData)

    @Query("select * from repositorydata where owner_login = :userName")
    fun readRepositoryDataFindByUserName(userName: String): Array<RepositoryData>

    @Query("select * from repositorydata where id = :id")
    fun readRepositoryDataFindById(id: Int): Array<RepositoryData>

}