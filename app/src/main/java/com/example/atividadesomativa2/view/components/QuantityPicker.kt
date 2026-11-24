package com.example.atividadesomativa2.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.atividadesomativa2.R

@Composable
fun QuantityPicker(
    quantity: Int,
    setQuantity: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                setQuantity(quantity - 1)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_remove_24),
                contentDescription = "Diminuir"
            )
        }

        Text(
            text = "${quantity}"
        )

        IconButton(
            onClick = {
                setQuantity(quantity + 1)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_add_24),
                contentDescription = "Aumentar"
            )
        }
    }
}