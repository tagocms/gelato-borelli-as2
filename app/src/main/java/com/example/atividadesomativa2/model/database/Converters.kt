package com.example.atividadesomativa2.model.database

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room3.ColumnTypeConverter
import com.example.atividadesomativa2.model.entity.Scoop
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

object Converters {
    @ColumnTypeConverter
    fun fromColor(color: Color) : Int {
        return color.toArgb()
    }

    @ColumnTypeConverter
    fun toColor(color: Int) : Color {
        return Color(color)
    }

    @ColumnTypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC) }
    }
    @ColumnTypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    }
}