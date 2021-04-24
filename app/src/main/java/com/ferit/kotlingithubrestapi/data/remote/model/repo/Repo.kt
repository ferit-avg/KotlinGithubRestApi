package com.ferit.kotlingithubrestapi.data.model.repo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repo(
    @SerializedName("id")
    @Expose
    val repoId: Int,

    @SerializedName("name")
    @Expose
    val repoName: String,

    @SerializedName("full_name")
    @Expose
    val fullName: String,

    @SerializedName("owner")
    @Expose
    val owner: Owner,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("stargazers_count")
    @Expose
    val stargazersCount: Int,

    @SerializedName("open_issues_count")
    @Expose
    val openIssuesCount: Int
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Repo

        if (repoId != other.repoId) return false
        if (repoName != other.repoName) return false
        if (fullName != other.fullName) return false
        if (owner != other.owner) return false
        if (description != other.description) return false
        if (stargazersCount != other.stargazersCount) return false
        if (openIssuesCount != other.openIssuesCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = repoId
        result = 31 * result + repoName.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + stargazersCount
        result = 31 * result + openIssuesCount
        return result
    }
}

data class Owner(
    @SerializedName("id")
    @Expose
    val ownerId: Int,

    @SerializedName("login")
    @Expose
    val ownerName: String,

    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Owner

        if (ownerId != other.ownerId) return false
        if (ownerName != other.ownerName) return false
        if (avatarUrl != other.avatarUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ownerId
        result = 31 * result + ownerName.hashCode()
        result = 31 * result + avatarUrl.hashCode()
        return result
    }
}
