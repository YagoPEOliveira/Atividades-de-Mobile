package com.example.testeaula.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,

    val description: String = "",

    val isCompleted: Boolean = false,

    val remoteId: Long? = null,

    val syncStatus: Int = SYNC_PENDING

) {
    companion object {
        const val SYNCED = 0
        const val SYNC_PENDING = 1
        const val SYNC_DELETE_PENDING = 2
    }
}