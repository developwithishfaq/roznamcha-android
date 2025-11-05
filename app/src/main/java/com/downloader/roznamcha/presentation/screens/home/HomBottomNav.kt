package com.downloader.roznamcha.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HomeBottomNav(selectedIndex: Int, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        HomeBottomNavItem(
            text = "RozNamcha",
            selected = selectedIndex == 0,
            onClick = {
                onClick.invoke(0)
            }
        )
        HomeBottomNavItem(
            text = "Khata",
            selected = selectedIndex == 1,
            onClick = {
                onClick.invoke(1)
            }
        )
        HomeBottomNavItem(
            text = "Purchases",
            selected = selectedIndex == 2,
            onClick = {
                onClick.invoke(2)
            }
        )
    }
}
@Composable
fun RowScope.HomeBottomNavItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
            .clickable {
                onClick.invoke()
            },
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        color = if (selected) {
            Color.Blue
        } else {
            Color.Black
        }
    )
}