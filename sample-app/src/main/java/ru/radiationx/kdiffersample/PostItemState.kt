package ru.radiationx.kdiffersample

data class PostItemState(
    val id: String,
    val header: PostItemHeaderState,
    val content: PostItemContentState,
    val footer: PostItemFooterState,
    val comment: PostItemCommentState?
)
