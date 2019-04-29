package com.example.apla.runpanyapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GitHubUserModel (
        var id: Long = 0,
        var name: String? = null,
        var url: String? = null,
        var email: String? = null,
        var login: String? = null,
        var location: String? = null,
        @SerializedName("avatar_url")
        var avatarUrl: String? = null
) : Parcelable {

    fun hasEmail(): Boolean {
        return email != null && !email!!.isEmpty()
    }

    fun hasLocation(): Boolean {
        return location != null && !location!!.isEmpty()
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(this.id)
        dest.writeString(this.name)
        dest.writeString(this.url)
        dest.writeString(this.email)
        dest.writeString(this.login)
        dest.writeString(this.location)
        dest.writeString(this.avatarUrl)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val user = o as GitHubUserModel?

        if (id != user!!.id) return false
        if (if (name != null) name != user!!.name else user!!.name != null) return false
        if (if (url != null) url != user!!.url else user!!.url != null) return false
        if (if (email != null) email != user!!.email else user!!.email != null) return false
        if (if (login != null) login != user!!.login else user!!.login != null) return false
        return if (if (location != null) location != user!!.location else user!!.location != null) false else !if (avatarUrl != null) avatarUrl != user!!.avatarUrl else user!!.avatarUrl != null

    }

    override fun hashCode(): Int {
        var result = (id xor id.ushr(32)).toInt()
        result = 31 * result + if (name != null) name!!.hashCode() else 0
        result = 31 * result + if (url != null) url!!.hashCode() else 0
        result = 31 * result + if (email != null) email!!.hashCode() else 0
        result = 31 * result + if (login != null) login!!.hashCode() else 0
        result = 31 * result + if (location != null) location!!.hashCode() else 0
        result = 31 * result + if (avatarUrl != null) avatarUrl!!.hashCode() else 0
        return result
    }

    companion object {

        @Suppress("unused")
        @JvmField
        val CREATOR : Parcelable.Creator<GitHubUserModel> = object : Parcelable.Creator<GitHubUserModel> {
            override fun createFromParcel(source: Parcel): GitHubUserModel {
                return source.let { GitHubUserModel(source.readLong(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString()) }
            }

            override fun newArray(size: Int): Array<GitHubUserModel?> {
                return arrayOfNulls(size)
            }
        }

    }

}
