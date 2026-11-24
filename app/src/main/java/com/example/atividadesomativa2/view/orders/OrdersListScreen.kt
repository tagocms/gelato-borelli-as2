package com.example.atividadesomativa2.view.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.atividadesomativa2.R
import com.example.atividadesomativa2.model.entity.read.OrderWithIceCream
import com.example.atividadesomativa2.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersListScreen(
    viewModel: OrderViewModel,
    onNewOrder: () -> Unit,
    onOrderUpdate: (Int) -> Unit,
    goToScoops: () -> Unit,
    goToToppings: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewOrder
            ) {
                Text("+")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Pedidos", style = MaterialTheme.typography.headlineMedium)
                },
                actions = {
                    IconButton(onClick = goToScoops) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_shaved_ice_24),
                            contentDescription = "Bolas de sorvete"
                        )
                    }

                    IconButton(onClick = goToToppings) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_snowflake_24),
                            contentDescription = "Adicionais"
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
                items(viewModel.orders) { order ->
                    OrderItem(
                        order = order,
                        onEdit = { onOrderUpdate(order.order.id) },
                        onDelete = { viewModel.delete(order) }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderItem(
    order: OrderWithIceCream,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.padding(12.dp).fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                "Id: ${order.order.id}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                "Status: ${order.order.status}",
                style = MaterialTheme.typography.bodyMedium
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
}