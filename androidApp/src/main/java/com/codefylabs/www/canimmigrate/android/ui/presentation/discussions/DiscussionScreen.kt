package com.codefylabs.www.canimmigrate.android.ui.presentation.discussions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.TopBar
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import com.codefylabs.www.canimmigrate.dashboard.domain.models.discussions.Comment
import com.codefylabs.www.canimmigrate.dashboard.domain.models.discussions.Discussions
import com.codefylabs.www.canimmigrate.dashboard.domain.models.discussions.Reply
import com.codefylabs.www.canimmigrate.dashboard.domain.models.discussions.User
import com.codefylabs.www.canimmigrate.dashboard.presentation.DiscussionEvent
import com.codefylabs.www.canimmigrate.dashboard.presentation.DiscussionSharedVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun DiscussionsScreen(navigateUp: () -> Unit, viewModel: DiscussionSharedVM = koinViewModel()) {
    val inputFocusRequest = remember {
        FocusRequester()
    }
    val state by viewModel.state.collectAsState()
    val lazyColumnState = rememberLazyListState()

    OnEvent(event = viewModel.event) {
        when (it) {
            is DiscussionEvent.Error -> {

            }

            DiscussionEvent.RequestCommentFieldFocus -> {
                inputFocusRequest.requestFocus()
            }

            is DiscussionEvent.Success -> {

            }
        }
    }

    Scaffold(topBar = {
        TopBar(title = "", actions = {}, onBackPress = navigateUp)
    }, floatingActionButton = {
        ChatInputField(
            onTextChange = viewModel::onCommentChange,
            newCommentField = state.newCommentField,
            inputFocusRequest = inputFocusRequest,
            onClickSend = viewModel::saveComment,
            onClearReplyTo = viewModel::clearReplyTo
        )
    }, contentWindowInsets = WindowInsets(0)) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            CommentSection(
                lazyListState = lazyColumnState,
                data = generateDummyData(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                onClickReplyTo = { comment, reply ->
                    viewModel.onClickReply(comment, reply)
                },
                onClickLike = viewModel::onLike
            )
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentSection(
    modifier: Modifier,
    lazyListState: LazyListState,
    data: Discussions,
    onClickReplyTo: (Comment, Reply?) -> Unit,
    onClickLike: (id: Int) -> Unit,
) {

    LazyColumn(modifier = modifier, state = lazyListState) {
        items(items = data.comments, key = { it.id }) { comment ->
            CommentItem(
                comment = comment,
                onClickReplyTo = onClickReplyTo,
                onClickLike = onClickLike,
                modifier = Modifier.animateItemPlacement()
            )
        }
    }

}

@Composable
fun CommentItem(
    modifier: Modifier,
    comment: Comment,
    onClickReplyTo: (Comment, Reply?) -> Unit,
    onClickLike: (id: Int) -> Unit,
) {

    val viewAllReplies = rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier = modifier.padding(vertical = 10.dp)) {
        UserInfoRow(user = comment.user, timestamp = comment.timestamp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = comment.comment, style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        LikesAndReplyRow(
            likes = comment.likes,
            repliesCount = comment.replies.size,
            onLike = {
                onClickLike(comment.id)
            },
            onClickReply = {
                onClickReplyTo(comment, null)
            }, viewRepliesState = viewAllReplies
        )

        AnimatedVisibility(comment.replies.isNotEmpty() && viewAllReplies.value) {
            RepliesContainer(
                replies = comment.replies,
                onClickLike = onClickLike,
                onClickReplyTo = {
                    onClickReplyTo(comment, it)
                })
        }

    }
}

@Composable
fun RepliesContainer(
    replies: List<Reply>,
    onClickReplyTo: (Reply) -> Unit,
    onClickLike: (id: Int) -> Unit,
) {
    var columnHeight by remember { mutableIntStateOf(0) }

    ConstraintLayout(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
        val (dashedLine, repliesColumn) = createRefs()

        DrawDashedLine(
            modifier = Modifier
                .height(with(LocalDensity.current) { columnHeight.toDp() })
                .constrainAs(dashedLine) {
                    top.linkTo(repliesColumn.top)
                    bottom.linkTo(repliesColumn.bottom)
                    start.linkTo(parent.start)
                }
        )

        Column(
            Modifier
                .constrainAs(repliesColumn) {
                    top.linkTo(parent.top)
                    start.linkTo(dashedLine.end, margin = 16.dp)
                    end.linkTo(parent.end)
                }
                .onGloballyPositioned { coordinates ->
                    columnHeight = coordinates.size.height
                }
        ) {
            replies.forEach { reply ->
                ReplyItem(reply, modifier = Modifier.fillMaxWidth(), onLike = {
                    onClickLike(reply.id)
                }, onClickReply = {
                    onClickReplyTo(reply)
                })
            }
        }
    }
}

@Composable
fun ReplyItem(reply: Reply, modifier: Modifier, onLike: () -> Unit, onClickReply: () -> Unit) {
    Column(modifier = modifier.padding(start = 16.dp, top = 16.dp)) {
        UserInfoRow(user = reply.user, timestamp = reply.timestamp)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = reply.comment, style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        LikesAndReplyRow(
            likes = reply.likes,
            repliesCount = 0,
            onLike = onLike,
            onClickReply = onClickReply,
            viewRepliesState = null
        )
    }
}

@Composable
private fun UserInfoRow(user: User, timestamp: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(user.profilePicture),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                )
            )
            Text(
                text = timestamp,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f)

                )
            )
        }
    }
}


@Composable
fun DrawDashedLine(modifier: Modifier) {
    Canvas(
        modifier = modifier
            .width(5.dp)
    ) {
        val stroke =
            Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
        drawLine(
            color = Color.Gray,
            start = androidx.compose.ui.geometry.Offset(0f, 0f),
            end = androidx.compose.ui.geometry.Offset(0f, size.height),
            strokeWidth = 1f,
            pathEffect = stroke.pathEffect
        )
    }
}


@Composable
private fun LikesAndReplyRow(
    likes: Int,
    repliesCount: Int,
    onLike: () -> Unit,
    onClickReply: () -> Unit,
    viewRepliesState: MutableState<Boolean>?,
) {
    Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = onLike, modifier = Modifier.size(20.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp),
                tint = MaterialTheme.colorScheme.onBackground.copy(0.7f)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$likes Likes",
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    onClickReply()
                }
                .padding(horizontal = 6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_reply),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onBackground.copy(0.7f)

            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Reply", style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        if (repliesCount > 0) {
            ClickableText(
                text = buildAnnotatedString {
                    if (viewRepliesState?.value == true) append("Hide Replies")
                    else append("View $repliesCount more reply")

                },
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                )
            ) {
                viewRepliesState?.value = !(viewRepliesState?.value ?: true)
            }

        }
    }
}

// Sample data generation
fun generateDummyData(): Discussions {
    val users = listOf(
        User("Maude Hall", "https://cdn-icons-png.freepik.com/512/3135/3135715.png"),
        User("Dianne Russell", "https://cdn-icons-png.freepik.com/512/3135/3135789.png"),
        User(
            "Esther Howard",
            "https://a0.anyrgb.com/pngimg/1236/14/no-facial-features-no-avatar-no-eyes-expressionless-avatar-icon-delayering-avatar-user-avatar-men-head-portrait.png"
        )
    )

    val replies1 = listOf(
        Reply(
            id = 1,
            likes = 10,
            timestamp = "10 min ago",
            user = users[2],
            comment = "This could be due to them taking their time to release a stable version."
        ),
        Reply(
            id = 2,
            likes = 10,
            timestamp = "10 min ago",
            user = users[2],
            comment = "This could be due to them taking their time to release a stable version."
        )
    )

    val comments = listOf(
        Comment(
            1,
            2,
            "That's a fantastic new app feature. You and your team did an excellent job of incorporating user testing feedback.",
            "14 min ago",
            users[0],
            replies1
        ),
        Comment(
            2,
            1,
            "But don't you think the timing is off because many other apps have done this even earlier, causing people to switch apps?",
            "24 min ago",
            users[1],
            replies1
        )
    )

    return Discussions(1, 1, 1, 2, comments)
}

@Preview
@Composable
private fun PreviewComments() {
    AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CommentSection(
                    modifier = Modifier.fillMaxSize(),
                    data = generateDummyData(),
                    onClickLike = {},
                    onClickReplyTo = { i, d -> },
                    lazyListState = rememberLazyListState()
                )
            }
        }
    }
}