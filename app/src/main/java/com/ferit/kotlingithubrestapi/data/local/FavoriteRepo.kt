package com.ferit.kotlingithubrestapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_repo")
data class FavoriteRepo(
    val repoId: Int,
    val ownerId: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val repoName: String,
    val fullName: String,
    val description: String,
    val stargazersCount: Int,
    val openIssuesCount: Int
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
