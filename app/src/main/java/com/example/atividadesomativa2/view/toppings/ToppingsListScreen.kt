package com.example.atividadesomativa2.view.toppings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = Modifier.padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(topping.color)
                .clip(CircleShape)
        )

        Text(
            topping.name,
            style = MaterialTheme.typography.titleMedium
        )

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