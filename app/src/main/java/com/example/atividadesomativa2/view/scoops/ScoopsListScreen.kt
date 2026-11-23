package com.example.atividadesomativa2.view.scoops

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
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.viewmodel.ScoopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoopsListScreen(
    viewModel: ScoopViewModel,
    onNewScoop: () -> Unit,
    onScoopUpdate: (Int) -> Unit,
    onGoBack: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewScoop
            ) {
                Text("+")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Bolas de sorvete", style = MaterialTheme.typography.headlineMedium)
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
                items(viewModel.scoops) { scoop ->
                    ScoopItem(
                        scoop = scoop,
                        onEdit = { onScoopUpdate(scoop.id) },
                        onDelete = { viewModel.delete(scoop) }
                    )
                }
            }
        }
    }
}

@Composable
fun ScoopItem(
    scoop: Scoop,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(scoop.color)
                .clip(CircleShape)
        )

        Text(
            "Sabor: ${scoop.flavor}",
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