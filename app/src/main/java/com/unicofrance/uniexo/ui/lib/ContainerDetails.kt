package com.unicofrance.uniexo.ui.lib

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unicofrance.uniexo.R.drawable.ic_container_11

@Composable
fun ContainerDetails(
    modifier: Modifier,
    name: String,
    id: String,
    latitude: Double,
    longitude: Double,
    stream: String,
    creationDate: String,
    iconId: Int,
    onClose: () -> Unit
) {
    Box(modifier.background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(iconId),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.padding(0.dp)
                    ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = "Close"
                    )
                }
            }
            HorizontalDivider( modifier = Modifier.padding(bottom = 4.dp))
            InfoField("ID", id)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoField(
                    "Latitude",
                    String.format("%.4f", latitude),
                    Modifier.weight(1f)
                )
                InfoField(
                    "Longitude",
                    String.format("%.5f", longitude),
                    Modifier.weight(1f)
                )
            }
            InfoField("Flux", stream)
            InfoField(
                "Date de création",
                creationDate
                    .substringBefore(" ")
                    .split("-")
                    .reversed()
                    .joinToString("/")
            )
        }
    }
}

@Preview
@Composable
fun ContainerDetailsPreview() {
    ContainerDetails(
        Modifier.fillMaxWidth(),
        name = "BÉZ-528",
        id = "36fbaf96-96df-4a61-acd2-bdcf6a1c99db",
        latitude = 43.3643,
        longitude = 3.22270,
        stream = "Déchets ménagers",
        creationDate = "2025-05-05 15:38:10",
        iconId = ic_container_11,
        onClose = {}
    )
}

@Composable
fun InfoField(
    title: String,
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = title, fontSize = 10.sp/*, color = */)
            Text(text = text, fontSize = 16.sp)
        }
    }
}