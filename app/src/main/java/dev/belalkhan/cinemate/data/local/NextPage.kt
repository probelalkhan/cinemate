package dev.belalkhan.cinemate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

const val NEXT_PAGE_ID = 1

@Entity(tableName = "next_page")
data class NextPage(
    @PrimaryKey(autoGenerate = false)
    val id: Int = NEXT_PAGE_ID,
    val nextPage: Int?,
)
