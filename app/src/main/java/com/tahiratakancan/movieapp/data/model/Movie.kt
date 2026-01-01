package com.tahiratakancan.movieapp.data.model

import android.os.Parcelable
import androidx.room.Entity // EKLENDİ
import androidx.room.PrimaryKey // EKLENDİ
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies") // Bu sınıf artık "movies" adında bir tablo!
data class Movie(
    @PrimaryKey // ID'ler benzersiz olduğu için PrimaryKey yaptık
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double
) : Parcelable

//API den dönen kısım yukarıdaki şekilde