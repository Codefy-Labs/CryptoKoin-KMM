package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.dashboard.domain.models.discussions.Comment
import com.codefylabs.www.canimmigrate.dashboard.domain.models.discussions.Reply
import kotlinx.coroutines.launch

class DiscussionSharedVM(
) : StateViewModel<DiscussionEvent, DiscussionViewState>(DiscussionViewState.initial()) {

    fun onClickReply(replyingComment: Comment, replyingToReply: Reply?) {
        coroutine.launch {
            updateState(
                state.value.copy(
                    newCommentField = NewCommentField(
                        replyingComment = replyingComment,
                        replyingToUserName = replyingToReply?.user?.name
                            ?: replyingComment.user.name
                    )
                )
            )
            sendEvent(DiscussionEvent.RequestCommentFieldFocus)
        }
    }

    fun onCommentChange(value: String) {
        updateState(
            state.value.copy(
                newCommentField = state.value.newCommentField.copy(
                    text = value,
                    enableSendButton = value.trim().isNotEmpty()
                )
            )
        )
    }

    fun clearReplyTo() {
        updateState(
            state.value.copy(
                newCommentField = state.value.newCommentField.copy(
                    replyingToUserName = null,
                    replyingComment = null
                )
            )
        )
    }

    fun saveComment() {
        coroutine.launch {
            if (state.value.newCommentField.text.trim().isEmpty()) {
                return@launch
            }

            //Call UseCase
        }
    }

    fun onLike(id: Int) {

    }
}

data class NewCommentField(
    val text: String = "",
    val replyingComment: Comment? = null,
    val replyingToUserName: String? = null,
    val enableSendButton: Boolean = false,
)

sealed class DiscussionEvent : Event {
    data class Success(val message: String) : DiscussionEvent()
    data class Error(val error: String) : DiscussionEvent()
    data object RequestCommentFieldFocus : DiscussionEvent()
}

data class DiscussionViewState(
    val isLoading: Boolean = false,
    val comments: List<Comment> = emptyList(),
    val newCommentField: NewCommentField = NewCommentField(),

    ) : State {
    companion object {
        fun initial() = DiscussionViewState()
    }
}


