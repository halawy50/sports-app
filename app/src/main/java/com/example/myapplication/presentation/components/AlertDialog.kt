package com.example.myapplication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.ui.theme.black

import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonWithBorder
import com.example.myapplication.ui.theme.white

@Composable
fun AlertDialog(
    messageAlert: String = "",
    titleButtonOne : String = "",
    onClickButtonOne: () -> Unit,
    titleButtonTwo : String = "",
    onClickButtonTwo: () -> Unit = {},
    isButtonOne: Boolean = true,
    isButtonTwo: Boolean = false
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(black.copy(0.6f))
                .clip(RoundedCornerShape(10.dp)),

            contentAlignment = Alignment.Center
        ) {

          Box(modifier = Modifier.padding(10.dp)) {
               Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(white)
                        .padding(horizontal = 10.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

            Text(text = messageAlert, textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (isButtonOne)
                            ButtonFill(
                                modifier = Modifier.weight(0.5f).fillMaxHeight(),
                                onClick = onClickButtonOne,
                                label = titleButtonOne
                            )

                        if (isButtonTwo)
                            Spacer(modifier = Modifier.width(10.dp))

                        if (isButtonTwo)
                            ButtonWithBorder(
                                modifier = Modifier.weight(0.5f).fillMaxHeight(),
                                onClick = onClickButtonTwo,
                                text = titleButtonTwo
                            )
                    }
                }
            }

        }
    }
}

