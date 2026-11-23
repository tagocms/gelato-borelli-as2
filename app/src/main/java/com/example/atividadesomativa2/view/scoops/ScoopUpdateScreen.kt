package com.example.atividadesomativa2.view.scoops

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.atividadesomativa2.R
import com.example.atividadesomativa2.viewmodel.ScoopViewModel
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.SliderOrientation
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoopUpdateScreen(
    viewModel: ScoopViewModel,
    scoopId: Int?,
    onGoBack: () -> Unit
) {
    val existingScoop = scoopId?.let {
        viewModel.getById(it)
    }

    var flavor by remember {
        mutableStateOf(existingScoop?.flavor ?: "")
    }

    var color by remember {
        mutableStateOf(existingScoop?.color ?: Color.Blue)
    }

    val screenTitle = existingScoop?.let {
        "Editar Bola de Sorvete"
    } ?: "Nova Bola de Sorvete"

    val colorController = rememberColorPickerController()
    colorController.selectByColor(color, false)

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
        ) {
            OutlinedTextField(
                value = flavor,
                onValueChange = { flavor = it },
                label = { Text("Sabor") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
            ) {
                Text(
                    text = "Cor da Bola de Sorvete",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(10.dp)
                )
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(10.dp),
                    controller = colorController,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        color = colorEnvelope.color
                    }
                )

                BrightnessSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(10.dp),
                    controller = colorController,
                    orientation = SliderOrientation.Horizontal,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (viewModel.canSave(flavor, color)) {
                        if (existingScoop != null) {
                            viewModel.update(existingScoop.id, flavor, color)
                        } else {
                            viewModel.add(flavor, color)
                        }
                        onGoBack()
                    }
                },
                enabled = viewModel.canSave(flavor, color),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }

            if (existingScoop != null) {
                Button(
                    onClick = {
                        onGoBack()
                        viewModel.delete(existingScoop)
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