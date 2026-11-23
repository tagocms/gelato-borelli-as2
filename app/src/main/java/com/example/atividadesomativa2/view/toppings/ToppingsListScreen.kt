package com.example.atividadesomativa2.view.toppings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.atividadesomativa2.R
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.viewmodel.ToppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToppingsListScreen(
    viewModel: ToppingViewModel,
    onNewTopping: () -> Unit,
    onToppingUpdate: (Int) -> Unit,
    onGoBack: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewTopping
            ) {
                Text("+")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Adicionais", style = MaterialTheme.typography.headlineMedium)
                },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_24),
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height((16.dp)))

            LazyColumn {
                items(viewModel.toppings) { topping ->
                    ToppingItem(
                        topping = topping,
                        onEdit = { onToppingUpdate(topping.id) },
                        onDelete = { viewModel.delete(topping) }
                    )
                }
            }
        }
    }
}

@Composable
fun ToppingItem(
    topping: Topping,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.padding(12.dp).fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ToppingDrawing(topping.color)

                Spacer(Modifier.width(8.dp))

                Text(
                    topping.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }


            Spacer(Modifier.height(8.dp))

            Row {
                Button(
                    onClick = onEdit
                ) {
                    Text("Editar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Excluir")
                }
            }
        }
    }
}

@Composable
fun ToppingDrawing(color: Color, size: Int = 50) {
    Box(
        modifier = Modifier
            .size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-12).dp, y = (4).dp)
                .rotate(-50f)
                .width(8.dp)
                .height(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 10.dp, y = 16.dp)
                .rotate(35f)
                .width(8.dp)
                .height(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 4.dp, y = (-12).dp)
                .rotate(20f)
                .width(8.dp)
                .height(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
    }
}