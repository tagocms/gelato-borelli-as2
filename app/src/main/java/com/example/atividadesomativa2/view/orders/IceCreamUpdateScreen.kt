package com.example.atividadesomativa2.view.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.atividadesomativa2.R
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamContainer
import com.example.atividadesomativa2.view.components.QuantityPicker
import com.example.atividadesomativa2.viewmodel.IceCreamViewModel
import com.example.atividadesomativa2.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IceCreamUpdateScreen(
    iceCreamViewModel: IceCreamViewModel,
    orderViewModel: OrderViewModel,
    iceCreamId: Int?,
    orderId: Int,
    onGoBack: () -> Unit
) {
    val iceCreamContainerOptions = listOf(IceCreamContainer.CUP, IceCreamContainer.CONE, IceCreamContainer.CESTINO)

    val existingDraft = iceCreamId?.let { id ->
        orderViewModel.draftIceCreams.find { it.id == id }
    }

    var container by remember { mutableStateOf(existingDraft?.container ?: IceCreamContainer.CUP) }
    var quantity by remember { mutableIntStateOf(existingDraft?.quantity ?: 1) }
    var scoops by remember { mutableStateOf(existingDraft?.scoops ?: emptyList()) }
    var toppings by remember { mutableStateOf(existingDraft?.toppings ?: emptyList()) }

    val screenTitle = existingDraft?.let {
        "Editar Sorvete"
    } ?: "Novo Sorvete"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = screenTitle, style = MaterialTheme.typography.headlineMedium)
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
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                Text(
                    text = "Contêiner do sorvete",
                    style = MaterialTheme.typography.headlineSmall
                )

                iceCreamContainerOptions.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (container == option),
                                onClick = { container = option },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (container == option),
                            onClick = null
                        )
                        Text(
                            text = option.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Column {
                Text(
                    text = "Bolas de sorvete",
                    style = MaterialTheme.typography.headlineSmall
                )

                iceCreamViewModel.allScoops.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val scoopCount = scoops.count { it.id == option.id }

                        Text(
                            text = option.flavor,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        QuantityPicker(scoopCount) { newValue ->
                            if (newValue >= 0) {
                                if (newValue > scoopCount) {
                                    scoops = scoops + option
                                } else if (newValue < scoopCount) {
                                    scoops = scoops - option
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Column {
                Text(
                    text = "Adicionais",
                    style = MaterialTheme.typography.headlineSmall
                )

                iceCreamViewModel.allToppings.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val toppingCount = toppings.count { it.id == option.id }

                        Text(
                            text = option.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        QuantityPicker(toppingCount) { newValue ->
                            if (newValue >= 0) {
                                if (newValue > toppingCount) {
                                    toppings = toppings + option
                                } else if (newValue < toppingCount) {
                                    toppings = toppings - option
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Column {
                Text(
                    text = "Quantidade",
                    style = MaterialTheme.typography.headlineSmall
                )

                QuantityPicker(quantity) { newValue ->
                    if (newValue >= 1) {
                        quantity = newValue
                    }
                }
            }

            Button(
                onClick = {
                    if (iceCreamViewModel.isIceCreamValid(toppings, scoops, container, quantity)) {
                        if (existingDraft != null) {
                            orderViewModel.updateDraftIceCream(existingDraft.id, container, quantity, scoops, toppings)
                        } else {
                            orderViewModel.addDraftIceCream(container, quantity, scoops, toppings)
                        }
                        onGoBack()
                    }
                },
                enabled = iceCreamViewModel.isIceCreamValid(toppings, scoops, container, quantity),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }

            if (existingDraft != null) {
                Button(
                    onClick = {
                        onGoBack()
                        orderViewModel.removeDraftIceCream(existingDraft)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Excluir")
                }
            }
        }
    }
}