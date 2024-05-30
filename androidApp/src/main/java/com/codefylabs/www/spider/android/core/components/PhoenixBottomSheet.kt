package com.codefylabs.www.spider.android.core.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoenixBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {

    val scope = rememberCoroutineScope()

    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val hideSheet = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onDismiss()
            }
        }
    }

    if (visible)
        ModalBottomSheet(
            modifier = Modifier,
            onDismissRequest = hideSheet::invoke,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            content()
        }

}