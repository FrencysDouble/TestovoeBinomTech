package com.example.testovoebinomtech.main_ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testovoebinomtech.R
import com.example.testovoebinomtech.models.MarkerModel
import com.example.testovoebinomtech.ui.theme.BlueIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    markerData: MarkerModel,
    onDismissRequest: () -> Unit,
)
{
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(onDismissRequest = {onDismissRequest.invoke()},
        sheetState = bottomSheetState,
        containerColor = Color.White,
        modifier = Modifier.wrapContentSize()) {
        Dialog(markerData)
    }

}

@Composable
fun Dialog(markerData: MarkerModel)
{
    Column(Modifier.fillMaxWidth().padding(bottom = 50.dp), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row(Modifier.fillMaxWidth().padding(top = 24.dp))
        {
            Row(Modifier.weight(1f).wrapContentSize(), horizontalArrangement = Arrangement.Center) {
                ImageIcon(markerData.imageId)
            }
                Column (Modifier.weight(1f).wrapContentSize())
                {
                    Text(markerData.title, fontSize = 16.sp)
                    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 6.dp))
                    {
                        Icon(
                            imageVector = Icons.Filled.Wifi,
                            contentDescription = "",
                            tint = BlueIcons
                        )
                        Spacer(Modifier.padding(2.dp))
                        Text("GPS", fontSize = 16.sp)
                    }
                }
                Row(Modifier.weight(1f).padding(top = 25.dp).wrapContentSize(), verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "",
                        tint = BlueIcons,

                    )
                    Spacer(Modifier.padding(2.dp))
                    Text(markerData.date, fontSize = 16.sp)
                }
                Row(Modifier.weight(1f).padding(top = 25.dp).wrapContentSize(), verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(
                        imageVector = Icons.Filled.WatchLater,
                        contentDescription = "",
                        tint = BlueIcons,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.padding(2.dp))
                    Text(markerData.time, fontSize = 16.sp)
                }

            }

        Button(onClick = { },colors =
        ButtonColors(
            containerColor = BlueIcons,
            contentColor = Color.White,
            disabledContentColor = Color.White,
            disabledContainerColor = BlueIcons)) {
            Text(stringResource(R.string.show_history_btn))
        }
    }

}

@Composable
fun ImageIcon(imageId: Int) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .border(1.dp, BlueIcons, CircleShape)
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}