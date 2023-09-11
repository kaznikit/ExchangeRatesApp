package com.nikit.exchangerates.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikit.domain.models.RateModel

@Entity
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "course")
    val course: Double,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "is_base")
    val isBase: Boolean
)

fun RateModel.toDb() = CurrencyEntity(symbol, course, isFavorite, isBase)

fun CurrencyEntity.toDomain() = RateModel(name, course, isFavorite, isBase)

fun List<CurrencyEntity>.toDomain() = map {
    it.toDomain()
}