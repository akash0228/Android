package com.example.tvappcompose.screens.Dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.room.util.copy
import androidx.tv.material3.MaterialTheme

val tabs= listOf("All","Recent","WatchLater","Search")
val TopBarFocusRequesters = List(size = tabs.size) { FocusRequester() }
@Composable
fun DashboardTopBar(
    selectedTabIndex:Int,
    modifier: Modifier =Modifier,
    focusRequesters: List<FocusRequester> = remember{ TopBarFocusRequesters},
    onTabSelection:(tab:String) -> Unit
){
    val focusManager = LocalFocusManager.current
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier=modifier.height(50.dp),
        containerColor = Color.Transparent,
        divider = { Spacer(modifier = Modifier)},
        contentColor = Color.White
    ) {
        tabs.forEachIndexed{ index, tab ->
            key(index){
                Tab(selected = index==selectedTabIndex, onClick = { focusManager.moveFocus(FocusDirection.Down) },modifier = Modifier
                    .onFocusEvent { if (it.hasFocus) onTabSelection(tab) }
                    .focusRequester(focusRequesters[index])
                    .clip(
                        RoundedCornerShape(16.dp)
                    )) {
                    Text(text = tab, style =MaterialTheme.typography.headlineSmall)
                }
            }
        }
    }
}