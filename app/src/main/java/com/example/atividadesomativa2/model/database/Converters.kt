package com.example.atividadesomativa2.model.database

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room3.ColumnTypeConverter

object Converters {
    @ColumnTypeConverter
    fun fromColor(color: Color) : Int {
        return color.toArgb()
    }

    @ColumnTypeConverter
    fun toColor(color: Int) : Color {
        return Color(color)
    }
}