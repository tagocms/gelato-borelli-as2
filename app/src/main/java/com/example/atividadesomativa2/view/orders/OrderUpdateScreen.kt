package com.example.atividadesomativa2.view.orders

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.atividadesomativa2.R
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.model.entity.auxiliary.DraftIceCream
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamContainer
import com.example.atividadesomativa2.model.entity.auxiliary.OrderStatus
import com.example.atividadesomativa2.model.entity.read.IceCreamWithDetails
import com.example.atividadesomativa2.viewmodel.IceCreamViewModel
import com.example.atividadesomativa2.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderUpdateScreen(
    orderViewModel: OrderViewModel,
    onNewIceCream: () -> Unit,
    onIceCreamUpdate: (Int) -> Unit,
    orderId: Int,
    onGoBack: () -> Unit
) {
    val orderStatusOptions = listOf(OrderStatus.PENDING, OrderStatus.PREPARING, OrderStatus.READY, OrderStatus.DELIVERED)
    LaunchedEffect(orderId) {
        if (orderId != null) orderViewModel.initDraft(orderId)
    }

    val existingOrder = orderId?.let {
        orderViewModel.getById(it)
    }

    var orderStatus by remember(existingOrder) {
        mutableStateOf(existingOrder?.order?.status ?: OrderStatus.PENDING)
    }

    val iceCreams = orderViewModel.draftIceCreams
    val isNewDraft = remember(orderId) {
        (existingOrder?.iceCreams?.size ?: 0) == 0
    }

    val screenTitle = if (!isNewDraft) "Editar Pedido" else "Novo Pedido"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = screenTitle, style = MaterialTheme.typography.headlineMedium)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isNewDraft && existingOrder != null) {
                            orderViewModel.delete(existingOrder)
                        }
                        orderViewModel.resetDraft()
                        onGoBack()
                    }) {
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
            if (existingOrder != null && !isNewDraft) {
                Column {
                    Text(
                        text = "Status do pedido",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    orderStatusOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (orderStatus == option),
                                    onClick = { orderStatus = option },
                                    role = Role.RadioButton
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (orderStatus == option),
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
            }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Sorvetes do pedido",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    IconButton(onClick = onNewIceCream) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_add_24),
                            contentDescription = "Adicionar sorvete"
                        )
                    }
                }

                if (iceCreams.isNotEmpty()) {
                    Column {
                        iceCreams.forEach { iceCream ->
                            IceCreamItem(
                                iceCream = iceCream,
                                onEdit = { onIceCreamUpdate(iceCream.id) },
                                onDelete = { orderViewModel.removeDraftIceCream(iceCream) }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Ainda não há sorvetes cadastrados.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    if (orderViewModel.isOrderValid(iceCreams, orderStatus)) {
                        orderViewModel.saveOrderWithDrafts(orderId, orderStatus) {
                            onGoBack()
                        }
                    }
                },
                enabled = orderViewModel.isOrderValid(iceCreams, orderStatus),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }

            if (existingOrder != null && !isNewDraft) {
                Button(
                    onClick = {
                        onGoBack()
                        orderViewModel.delete(existingOrder)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Excluir")
                }
            }
        }
    }
}

@Composable
fun IceCreamItem(
    iceCream: DraftIceCream,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.padding(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IceCreamDrawing(iceCream.scoops, iceCream.toppings, iceCream.container)

                Spacer(Modifier.width(8.dp))

                Text(
                    "${iceCream.quantity}",
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
fun IceCreamDrawing(
    scoops: List<Scoop>,
    toppings: List<Topping>,
    container: IceCreamContainer,
    size: Int = 50
) {
    val containerColor = Color(0xFFD2691E)

    Box(
        modifier = Modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size.dp)) {
            val w = size.toFloat() * density
            val h = size.toFloat() * density
            val defaultCenter = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.35f)

            when (container) {
                IceCreamContainer.CONE -> {
                    val path = Path().apply {
                        moveTo(w * 0.25f, h * 0.45f)
                        lineTo(w * 0.75f, h * 0.45f)
                        lineTo(w * 0.5f, h * 0.95f)
                        close()
                    }
                    drawPath(path, color = containerColor)
                }
                IceCreamContainer.CUP -> {
                    drawRect(
                        color = containerColor,
                        topLeft = androidx.compose.ui.geometry.Offset(w * 0.15f, h * 0.5f),
                        size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.45f)
                    )
                }
                IceCreamContainer.CESTINO -> {
                    val path = Path().apply {
                        moveTo(w * 0.1f, h * 0.45f)
                        lineTo(w * 0.9f, h * 0.45f)
                        lineTo(w * 0.8f, h * 0.9f)
                        lineTo(w * 0.2f, h * 0.9f)
                        close()
                    }
                    drawPath(path, color = containerColor)
                }
            }

            val scoopRadius = w * 0.22f
            val scoopCenters = mutableListOf<androidx.compose.ui.geometry.Offset>()

            when (scoops.size) {
                1 -> {
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.35f))
                }
                2 -> {
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.45f))
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.25f))
                }
                3 -> {
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.35f, h * 0.4f))
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.65f, h * 0.4f))
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.2f))
                }
                4 -> {
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.35f, h * 0.45f))
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.65f, h * 0.45f))
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.35f, h * 0.25f))
                    scoopCenters.add(androidx.compose.ui.geometry.Offset(w * 0.65f, h * 0.25f))
                }
                else -> {
                    if (scoops.isNotEmpty()) {
                        for (i in 0 until scoops.size) {
                            val angle = (i * 2.0 * Math.PI / scoops.size).toFloat()
                            scoopCenters.add(
                                androidx.compose.ui.geometry.Offset(
                                    w * 0.5f + (w * 0.15f * kotlin.math.cos(angle)),
                                    h * 0.35f + (h * 0.15f * kotlin.math.sin(angle))
                                )
                            )
                        }
                    }
                }
            }

            scoops.forEachIndexed { index, scoop ->
                val center = scoopCenters.getOrElse(index) { defaultCenter }
                drawCircle(
                    color = scoop.color,
                    radius = scoopRadius,
                    center = center
                )
            }

            toppings.forEachIndexed { tIndex, topping ->
                val offsets = listOf(
                    androidx.compose.ui.geometry.Offset(-0.1f, -0.1f),
                    androidx.compose.ui.geometry.Offset(0.12f, 0.05f),
                    androidx.compose.ui.geometry.Offset(-0.05f, 0.15f),
                    androidx.compose.ui.geometry.Offset(0.08f, -0.12f)
                )
                val baseCenter = scoopCenters.getOrElse(tIndex % scoopCenters.size) { defaultCenter }
                val sprinkleOffset = offsets.getOrElse(tIndex % offsets.size) { androidx.compose.ui.geometry.Offset(0f, 0f) }

                val sprinkleX = baseCenter.x + (w * sprinkleOffset.x)
                val sprinkleY = baseCenter.y + (h * sprinkleOffset.y)

                drawRect(
                    color = topping.color,
                    topLeft = androidx.compose.ui.geometry.Offset(sprinkleX - 3f, sprinkleY - 6f),
                    size = androidx.compose.ui.geometry.Size(6f, 14f)
                )
            }
        }
    }
}