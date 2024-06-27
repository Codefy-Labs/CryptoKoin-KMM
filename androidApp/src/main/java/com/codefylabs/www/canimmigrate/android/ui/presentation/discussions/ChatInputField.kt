package com.codefylabs.www.canimmigrate.android.ui.presentation.discussions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import com.codefylabs.www.canimmigrate.dashboard.presentation.NewCommentField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputField(
    onTextChange: (String) -> Unit,
    newCommentField: NewCommentField,
    inputFocusRequest: FocusRequester,
    onClearReplyTo: () -> Unit,
    onClickSend: () -> Unit,
) {


    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.small,
                ambientColor = MaterialTheme.colorScheme.primary
            )
            .clip(MaterialTheme.shapes.small)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small
            )
    ) {
        AnimatedVisibility(visible = newCommentField.replyingComment != null && !newCommentField.replyingToUserName.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Replying to ${newCommentField.replyingToUserName ?: ""}",

                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    ),
                )

                IconButton(onClick = onClearReplyTo, modifier = Modifier.size(16.dp)) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }


      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom){
          TextField(
              value = newCommentField.text,
              onValueChange = onTextChange,
              placeholder = {
                  Text(
                      text = "Add a comment...",
                      style = MaterialTheme.typography.bodyLarge.copy(
                          fontSize = 14.sp,
                          fontWeight = FontWeight.Medium,
                          color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                      )
                  )
              },
              modifier = Modifier
                  .weight(1f)
                  .focusRequester(focusRequester = inputFocusRequest),
              colors = TextFieldDefaults.colors(
                  focusedContainerColor = Color.Transparent,
                  unfocusedContainerColor = Color.Transparent,
                  disabledContainerColor = Color.Transparent,
                  cursorColor = MaterialTheme.colorScheme.primary,
                  focusedIndicatorColor = Color.Transparent,
                  errorIndicatorColor = Color.Transparent,
                  disabledIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent
              ),
              supportingText = {
                  Divider(thickness = 0.3.dp)
                  CommunityRulesText(onLinkClick = {})
              }
          )

          AnimatedVisibility(visible = newCommentField.enableSendButton, modifier = Modifier.padding(bottom = 26.dp)) {
              IconButton(onClick = onClickSend) {
                  Icon(imageVector = Icons.Default.Send, contentDescription = null)
              }
          }
      }


    }

}

@Composable
fun CommunityRulesText(onLinkClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 10.sp)) {
            append("Please follow ")
        }
        pushStringAnnotation(tag = "URL", annotation = "community_rules")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 10.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("community rules")
        }
        pop()
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 10.sp)) {
            append(" when commenting")
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(12.dp) )
        Text(
            text = annotatedString,
            modifier = Modifier
                .padding(vertical = 6.dp)
                .padding(start = 4.dp)
                .clickable {
                    onLinkClick()
                }
        )
    }
}

@Preview
@Composable
private fun ChatInputFieldPreview() {
    AppTheme {
        Surface {
            Column(Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.weight(1f))
                ChatInputField(
                    newCommentField = NewCommentField(replyingToUserName = "ShubhamKodes", enableSendButton = true),
                    inputFocusRequest = FocusRequester(),
                    onClickSend = {},
                    onClearReplyTo = {},
                    onTextChange = {}
                )
            }
        }
    }
}