package com.example.apla.runpanyapp.models

import android.os.Parcel
import android.os.Parcelable
import com.example.apla.runpanyapp.data.remote.github.entities.GitHubUserEntity

data class GitHubRepositoryModel(
        var id: Long = 0,
        var name: String? = null,
        var description: String? = null,
        var forks: Int = 0,
        var watchers: Int = 0,
        var stars: Int = 0,
        var language: String? = null,
        var homepage: String? = null,
        var fork: Boolean = false,
        var owner: GitHubUserModel? = null
) : Parcelable {

    init {
        this.name = if (name != null) name else ""
        this.description = if (description != null) description else ""
    }

    fun hasHomepage(): Boolean {
        return homepage != null && !homepage!!.isEmpty()
    }

    fun hasLanguage(): Boolean {
        return language != null && !language!!.isEmpty()
    }

    fun isFork(): Boolean {
        return fork
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(this.id)
        dest.writeString(this.name)
        dest.writeString(this.description)
        dest.writeInt(this.forks)
        dest.writeInt(this.watchers)
        dest.writeInt(this.stars)
        dest.writeString(this.language)
        dest.writeString(this.homepage)
        dest.writeParcelable(this.owner, 0)
        dest.writeByte(if (fork) 1.toByte() else 0.toByte())
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as GitHubRepositoryModel?

        if (id != that!!.id) return false
        if (forks != that!!.forks) return false
        if (watchers != that!!.watchers) return false
        if (stars != that!!.stars) return false
        if (fork != that!!.fork) return false
        if (if (name != null) name != that!!.name else that!!.name != null) return false
        if (if (description != null) description != that!!.description else that!!.description != null)
            return false
        if (if (language != null) language != that!!.language else that!!.language != null)
            return false
        return if (if (homepage != null) homepage != that!!.homepage else that!!.homepage != null) false else !if (owner != null) !owner!!.equals(that!!.owner) else that!!.owner != null

    }

    override fun hashCode(): Int {
        var result = (id xor id.ushr(32)).toInt()
        result = 31 * result + if (name != null) name!!.hashCode() else 0
        result = 31 * result + if (description != null) description!!.hashCode() else 0
        result = 31 * result + forks
        result = 31 * result + watchers
        result = 31 * result + stars
        result = 31 * result + if (language != null) language!!.hashCode() else 0
        result = 31 * result + if (homepage != null) homepage!!.hashCode() else 0
        result = 31 * result + if (owner != null) owner!!.hashCode() else 0
        result = 31 * result + if (fork) 1 else 0
        return result
    }

    companion object {

        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<GitHubRepositoryModel> = object : Parcelable.Creator<GitHubRepositoryModel> {
            override fun createFromParcel(source: Parcel): GitHubRepositoryModel {
                return GitHubRepositoryModel(
                        source.readLong(),
                        source.readString(),
                        source.readString(),
                        source.readInt(),
                        source.readInt(),
                        source.readInt(),
                        source.readString(),
                        source.readString(),
                        source.readInt() !== 0,
                        source.readParcelable(GitHubUserEntity::class.java.classLoader)
                        )
            }

            override fun newArray(size: Int): Array<GitHubRepositoryModel?> {
                return arrayOfNulls(size)
            }
        }

    }

}
