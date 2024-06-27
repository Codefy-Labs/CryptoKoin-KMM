package com.codefylabs.www.canimmigrate.android.ui.presentation.newsDetail.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.presentation.discussions.ChatInputField
import com.codefylabs.www.canimmigrate.android.ui.presentation.discussions.CommentSection
import com.codefylabs.www.canimmigrate.android.ui.presentation.discussions.generateDummyData
import com.codefylabs.www.canimmigrate.dashboard.presentation.DiscussionEvent
import com.codefylabs.www.canimmigrate.dashboard.presentation.DiscussionSharedVM
import kotlinx.coroutines.launch

@Composable
fun DiscussionsPage(viewModel: DiscussionSharedVM) {
    val inputFocusRequest = remember {
        FocusRequester()
    }
    val state by viewModel.state.collectAsState()
    val lazyColumnState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()

    OnEvent(event = viewModel.event) {
        when (it) {
            is DiscussionEvent.Error -> {

            }

            DiscussionEvent.RequestCommentFieldFocus -> {
                inputFocusRequest.requestFocus()
                state.newCommentField.replyingComment?.let {
                   coroutine.launch {
                       lazyColumnState.animateScrollToItem(it.id)
                   }
                }
            }

            is DiscussionEvent.Success -> {

            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .imePadding()) {
        CommentSection(
            lazyListState = lazyColumnState,
            data = generateDummyData(),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            onClickReplyTo = { comment, reply ->
                viewModel.onClickReply(comment, reply)
            },
            onClickLike = viewModel::onLike
        )
        ChatInputField(
            onTextChange = viewModel::onCommentChange,
            newCommentField = state.newCommentField,
            inputFocusRequest = inputFocusRequest,
            onClickSend = viewModel::saveComment,
            onClearReplyTo = viewModel::clearReplyTo
        )
    }


}
