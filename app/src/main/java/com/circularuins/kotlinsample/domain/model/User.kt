package com.circularuins.kotlinsample.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by circularuins on 2018/01/07.
 */
@Entity
data class User(
        @PrimaryKey
        @ColumnInfo(name = "user_id")
        @SerializedName("id")
        val userId: String,

        val name: String?,

        @ColumnInfo(name = "profile_image_url")
        val profileImageUrl: String) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = source.run {
                User(readString(), readString(), readString())
            }

            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeString(userId)
            writeString(name)
            writeString(profileImageUrl)
        }
    }
}