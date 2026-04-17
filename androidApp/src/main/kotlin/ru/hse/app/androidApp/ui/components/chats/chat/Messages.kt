package ru.hse.app.androidApp.ui.components.chats.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.imageLoader
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.entity.model.chats.ChatMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.MessageUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Messages(
    messages: List<MessageUiModel>,
    scrollState: LazyListState,
    onAuthorClick: (ChatMemberUiModel?) -> Unit,
    onReadMsg: (String) -> Unit,
    isDarkTheme: Boolean,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    val jumpToBottomThreshold = 56.dp
    val scope = rememberCoroutineScope()

    val visibleMessages = remember { mutableStateMapOf<String, Boolean>() }

    val firstUnreadIndex by remember(messages) {
        derivedStateOf {
            messages.indexOfLast { !it.isRead }
        }
    }

    LaunchedEffect(firstUnreadIndex) {
        if (firstUnreadIndex != -1) {
            scrollState.scrollToItem(firstUnreadIndex)
        }
    }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                visibleItems.forEach { itemInfo ->
                    val messageId = messages.getOrNull(itemInfo.index)?.id ?: return@forEach
                    val isCurrentlyVisible = visibleMessages[messageId] != true

                    if (isCurrentlyVisible && !messages.find { it.id == messageId }!!.isRead) {
                        onReadMsg(messageId)
                        visibleMessages[messageId] = true
                    }
                }
            }
    }

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = if (isDarkTheme) R.drawable.chat_background_dark else R.drawable.chat_background_light),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .alpha(0.7f),
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
        ) {
            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index - 1)?.author
                val nextAuthor = messages.getOrNull(index + 1)?.author
                val content = messages[index]
                val isFirstMessageByAuthor = prevAuthor != content.author
                val isLastMessageByAuthor = nextAuthor != content.author

                item(key = content.id) {
                    Message(
                        onAuthorClick = onAuthorClick,
                        msg = content,
                        isUserMe = content.author.isCurrentUser,
                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                        isLastMessageByAuthor = isLastMessageByAuthor,
                        isDarkTheme = isDarkTheme,
                        imageLoader = imageLoader,
                        modifier = Modifier.onVisibilityChanged { visibility ->
                            if (visibility && !content.isRead) {
                                onReadMsg(content.id)
                            }
                        }
                    )
                }

//                if (index == firstUnreadIndex) { todo придумать как добавить
//                    item(key = "unread_header_$index") {
//                        DayHeader("Непрочитано")
//                    }
//                }
            }
        }

        val jumpThreshold = with(LocalDensity.current) {
            jumpToBottomThreshold.toPx()
        }

        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex != 0 ||
                        scrollState.firstVisibleItemScrollOffset > jumpThreshold
            }
        }

        JumpToBottom(
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun Message(
    onAuthorClick: (ChatMemberUiModel?) -> Unit,
    msg: MessageUiModel,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    isDarkTheme: Boolean,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isUserMe) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.surfaceContainer
    }

    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier

    Row(modifier = modifier.then(spaceBetweenAuthors)) {
        if (isLastMessageByAuthor) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = msg.author.photoURL,
                    imageLoader = imageLoader,
                    error = painterResource(
                        if (isDarkTheme)
                            R.drawable.avatar_default_dark
                        else
                            R.drawable.avatar_default_light
                    )
                ),
                contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = { onAuthorClick(msg.author) })
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                contentScale = ContentScale.Crop
            )
        } else {
            Spacer(modifier = Modifier.width(74.dp))
        }

        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
        )
    }
}

@Composable
fun AuthorAndTextMessage(
    msg: MessageUiModel,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if (isLastMessageByAuthor) {
            AuthorName(msg)
        }
        ChatItemBubble(
            message = msg,
            isUserMe = isUserMe,
        )
        if (isFirstMessageByAuthor) {
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorName(msg: MessageUiModel) {
    VariableMedium(
        text = msg.author.name,
        fontSize = 14.sp,
        modifier = Modifier.paddingFrom(LastBaseline, after = 8.dp),
        fontColor = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
fun ChatItemBubble(
    message: MessageUiModel,
    isUserMe: Boolean
) {
    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.surfaceContainer
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape,
        ) {
            // Контейнер для текста и времени в одной строке
            Column (
                modifier = Modifier.padding(12.dp, 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Текст сообщения
                ClickableMessage(
                    message = message,
                    isUserMe = isUserMe,
                )

                // Время сообщения (стиль Telegram)
                Spacer(modifier = Modifier.width(8.dp))
                MessageTimestamp(
                    timestamp = message.timestamp,
                    isUserMe = isUserMe,
                    isRead = message.isRead
                )
            }
        }
    }
}

@Composable
fun ClickableMessage(
    message: MessageUiModel,
    isUserMe: Boolean,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    val styledMessage = messageFormatter(
        text = message.content,
        primary = isUserMe,
    )

    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = if (isUserMe)
                MaterialTheme.colorScheme.onSecondary
            else
                MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier,
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        else -> Unit
                    }
                }
        },
    )
}

@Composable
fun MessageTimestamp(
    timestamp: LocalDateTime,
    isUserMe: Boolean,
    isRead: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        VariableLight(
            text = formatMessageTime(timestamp),
            fontSize = 11.sp,
            fontColor = if (isUserMe) {
                MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
            } else {
                MaterialTheme.colorScheme.outline
            },
        )
    }
}

@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(16.dp),
    ) {
        DayHeaderLine()
        Text(
            text = dayString,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
        )
        DayHeaderLine()
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

@Composable
private fun RowScope.DayHeaderLine() {
    HorizontalDivider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    )
}


fun formatMessageTime(dateTime: LocalDateTime): String {
    val now = LocalDate.now()
    val messageDate = dateTime.toLocalDate()

    return when {
        messageDate.isEqual(now) -> {
            dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
        messageDate.isEqual(now.minusDays(1)) -> {
            "Вчера ${dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
        }
        else -> {
            dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
        }
    }
}

fun getMessagePreview(): MessageUiModel {
    return MessageUiModel(
        author = getMeChat(),
        content = "Hello, how are you?",
        fileUrl = null,
        timestamp = LocalDateTime.now(),
        id = "1",
        isRead = true,
    )
}

fun getMe(): ServerMemberUiModel {
    return ServerMemberUiModel(
        id = "q",
        name = "Юлия Кухтина",
        nickname = "yuulkht",
        status = StatusPresentation.ONLINE,
        avatarUrl = "",
        mainRole = null,
        allRoles = listOf(),
        isOwner = true
    )
}

fun getNotMe(): ServerMemberUiModel {
    return ServerMemberUiModel(
        id = "s",
        name = "Марина Жукова",
        nickname = "jukova",
        status = StatusPresentation.ONLINE,
        avatarUrl = "",
        mainRole = null,
        allRoles = listOf(),
        isOwner = false
    )
}

@Preview(showBackground = true)
@Composable
fun DayHeaderPreview() {
    AppTheme(isDark = false) {
        DayHeader("Today")
    }
}

@Preview(showBackground = true)
@Composable
fun DayHeaderPreviewD() {
    AppTheme(isDark = true) {
        DayHeader("Today")
    }
}

@Preview(showBackground = true)
@Composable
fun ChatItemBubblePreview() {
    val messageFromMe = getMessagePreview(isCurrentUser = true)
    val messageFromOthers = getMessagePreview(isCurrentUser = false)

    AppTheme(isDark = false) {
        Column {
            ChatItemBubble(
                message = messageFromMe,
                isUserMe = true,
            )
            ChatItemBubble(
                message = messageFromOthers,
                isUserMe = false,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatItemBubblePreviewDark() {
    val messageFromMe = getMessagePreview(isCurrentUser = true)
    val messageFromOthers = getMessagePreview(isCurrentUser = false)
    AppTheme(isDark = true) {
        Column {
            ChatItemBubble(
                message = messageFromMe,
                isUserMe = true,
            )
            ChatItemBubble(
                message = messageFromOthers,
                isUserMe = false,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClickableMessagePreview() {
    val messageFromMe = getMessagePreview(isCurrentUser = true)
    val messageFromOthers = getMessagePreview(isCurrentUser = false)

    AppTheme(isDark = false) {
        Column {
            ClickableMessage(
                message = messageFromMe,
                isUserMe = true,
            )
            ClickableMessage(
                message = messageFromOthers,
                isUserMe = false,
            )
        }
    }

    AppTheme(isDark = true) {
        Column {
            ClickableMessage(
                message = messageFromMe,
                isUserMe = true,
            )
            ClickableMessage(
                message = messageFromOthers,
                isUserMe = false,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorNameTimestampPreview() {
    val message = MessageUiModel(
        author = getMeChat(),
        content = "Hello, how are you?",
        timestamp = LocalDateTime.now().minusDays(1),
        fileUrl = null,
        id = "1",
        isRead = true,
    )

    AppTheme(isDark = false) {
        AuthorName(msg = message)
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorNameTimestampPreviewDark() {
    val message = MessageUiModel(
        author = getMeChat(),
        content = "Hello, how are you?",
        timestamp = LocalDateTime.now(),
        id = "1",
        fileUrl = null,
        isRead = true,
    )

    AppTheme(isDark = true) {
        AuthorName(msg = message)
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorAndTextMessagePreview() {
    val messageFromMe = getMessagePreview(isCurrentUser = true)
    val messageFromOthers = getMessagePreview(isCurrentUser = false)

    AppTheme(isDark = false) {
        Column {
            AuthorAndTextMessage(
                msg = messageFromMe,
                isUserMe = true,
                isFirstMessageByAuthor = true,
                isLastMessageByAuthor = false,
            )
            AuthorAndTextMessage(
                msg = messageFromOthers,
                isUserMe = false,
                isFirstMessageByAuthor = false,
                isLastMessageByAuthor = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorAndTextMessagePreviewDark() {
    val messageFromMe = getMessagePreview(isCurrentUser = true)
    val messageFromOthers = getMessagePreview(isCurrentUser = false)

    AppTheme(isDark = true) {
        Column {
            AuthorAndTextMessage(
                msg = messageFromMe,
                isUserMe = true,
                isFirstMessageByAuthor = true,
                isLastMessageByAuthor = false,
            )
            AuthorAndTextMessage(
                msg = messageFromOthers,
                isUserMe = false,
                isFirstMessageByAuthor = false,
                isLastMessageByAuthor = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    val messageFromMe = getMessagePreview(isCurrentUser = true)
    val messageFromOthers = getMessagePreview(isCurrentUser = false)

    val imageLoader = LocalContext.current.imageLoader

    AppTheme(isDark = false) {
        Column {
            Message(
                onAuthorClick = {},
                msg = messageFromMe,
                isUserMe = true,
                isFirstMessageByAuthor = true,
                isLastMessageByAuthor = false,
                isDarkTheme = false,
                imageLoader = imageLoader
            )
            Message(
                onAuthorClick = {},
                msg = messageFromOthers,
                isUserMe = false,
                isFirstMessageByAuthor = false,
                isLastMessageByAuthor = true,
                isDarkTheme = false,
                imageLoader = imageLoader
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagePreviewDark() {
    val messageFromMe = getMessagePreview(isCurrentUser = true)
    val messageFromOthers = getMessagePreview(isCurrentUser = false)

    val imageLoader = LocalContext.current.imageLoader

    AppTheme(isDark = true) {
        Column {
            Message(
                onAuthorClick = {},
                msg = messageFromMe,
                isUserMe = true,
                isFirstMessageByAuthor = true,
                isLastMessageByAuthor = false,
                isDarkTheme = true,
                imageLoader = imageLoader
            )
            Message(
                onAuthorClick = {},
                msg = messageFromOthers,
                isUserMe = false,
                isFirstMessageByAuthor = false,
                isLastMessageByAuthor = true,
                isDarkTheme = true,
                imageLoader = imageLoader
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesPreview() {

    val scrollState = rememberLazyListState()

    val imageLoader = LocalContext.current.imageLoader
    val scope = rememberCoroutineScope()

    val sortedMessages = messages.sortedByDescending { it.timestamp }

    AppTheme(isDark = false) {
        Messages(
            messages = sortedMessages,
            scrollState = scrollState,
            onAuthorClick = { },
            isDarkTheme = false,
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxSize(),
            onReadMsg = {}
        )
    }
}

val messages = listOf(
    MessageUiModel(
        author = getMeChat(),
        content = "Привет! Как ты? Я так давно не слышала от тебя. У меня всё в порядке, но было много работы. Недавно закончила проект, теперь можно немного расслабиться и посвятить время себе. Как ты провёл эти пару недель? Надеюсь, у тебя всё хорошо.",
        timestamp = LocalDateTime.now().minusDays(1),
        id = "1",
        isRead = true,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getMeChat(),
        content = "Сегодня я поехала на выставку, было очень интересно! Столько новых идей, которые можно применить в своей работе. Вообще, в последнее время чувствую, что много открываю для себя. А ты что нового узнал за последнее время?",
        timestamp = LocalDateTime.now().minusHours(2),
        id = "2",
        isRead = false,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getNotMeChat(),
        content = "Привет, как дела? Всё отлично, просто была занята подготовкой к экзаменам, но сейчас у меня всё свободно. Вчера прошёл последний экзамен, и я чувствую себя свободной! Как ты? Что нового?",
        timestamp = LocalDateTime.now().minusHours(1),
        id = "3",
        isRead = false,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getNotMeChat(),
        content = "На выходных я была на открытии нового кафе, и это было просто потрясающе! Там такая уютная атмосфера, и еда невероятная. Мы с друзьями даже заказали несколько блюд на пробу, и все они были восхитительны. Я бы порекомендовала тебе сходить, если будешь в этом районе.",
        timestamp = LocalDateTime.now(),
        id = "4",
        isRead = false,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getAlexey(),
        content = "Привет, Юля! С радостью прочитал твой последний пост в блоге. Ты так вдохновляюще пишешь, что всегда интересно прочитать твои новые статьи. Надеюсь, у тебя всё отлично! Как у тебя дела на работе? Я тут немного думаю о смене профессии, хочу попробовать что-то новое, но пока не решаюсь.",
        timestamp = LocalDateTime.now().minusDays(3),
        id = "5",
        isRead = true,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getAlexey(),
        content = "Кроме того, в этом месяце планирую заняться путешествиями. Хочу посетить несколько стран, и, возможно, даже попробую жить в другой стране несколько месяцев, чтобы узнать культуру изнутри. Ты когда-нибудь думала о таком? Вдруг получится! А ещё, я планирую улучшить свои навыки в языке, так что будет интересно этим заняться.",
        timestamp = LocalDateTime.now().minusDays(2),
        id = "6",
        isRead = true,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getEkaterina(),
        content = "Привет, как ты? Давно не общались! У меня всё хорошо, но столько всего произошло за последнее время. Я только что закончила курс по нейросетям и теперь планирую запускать свой первый проект. Не могу дождаться, когда смогу поделиться результатами с коллегами. А ты как? Чем занимаешься?",
        timestamp = LocalDateTime.now().minusDays(4),
        id = "7",
        isRead = true,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getEkaterina(),
        content = "Недавно начала читать книгу по психологии и вообще, она перевернула мой взгляд на многие вещи. Знаешь, это так интересно — наблюдать, как люди воспринимают мир и какие у них убеждения. Стало гораздо легче понимать поведение людей, когда ты знаешь, что происходит у них в голове. Может, ты тоже хочешь почитать? Я могу порекомендовать!",
        timestamp = LocalDateTime.now().minusDays(5),
        id = "8",
        isRead = true,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getEkaterina(),
        content = "Н",
        timestamp = LocalDateTime.now().minusDays(5),
        id = "11",
        isRead = true,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getMeChat(),
        content = "Сегодня как раз на работе обсуждали, как важно отдыхать и перезаряжаться. Работа - это, конечно, важно, но без восстановления ресурсов невозможно быть эффективным. А как ты восстанавливаешь силы? Я, например, люблю проводить время на природе или делать йогу. Это так помогает мне быть в форме!",
        timestamp = LocalDateTime.now().minusHours(6),
        id = "9",
        isRead = false,
        fileUrl = null,
    ),
    MessageUiModel(
        author = getMeChat(),
        content = "Я вот недавно начала слушать подкасты по психологии и нейробиологии. Очень интересно, как работает наш мозг, как влияют разные факторы на поведение. В общем, стало много нового узнавать. Подумываю о том, чтобы изучить психологию на более глубоком уровне. Ты что-нибудь подобное слушаешь?",
        timestamp = LocalDateTime.now().minusHours(7),
        id = "10",
        isRead = false,
        fileUrl = null,
    ),
)

fun getAlexey(): ChatMemberUiModel {
    return ChatMemberUiModel(
        id = "a1",
        name = "Алексей Иванов",
        username = "alexey_ivanov",
        status = StatusPresentation.ONLINE,
        photoURL = "",
        isCurrentUser = false
    )
}

fun getEkaterina(): ChatMemberUiModel {
    return ChatMemberUiModel(
        id = "e1",
        name = "Екатерина Смирнова",
        username = "katya_smirnova",
        status = StatusPresentation.ONLINE,
        photoURL = "",
        isCurrentUser = false
    )
}


fun getMessagePreview(isCurrentUser: Boolean): MessageUiModel {
    return MessageUiModel(
        author = if (isCurrentUser) getMeChat() else getNotMeChat(),
        content = if (isCurrentUser) "Привет, это мое сообщение." else "Привет, как ты?",
        timestamp = LocalDateTime.now(),
        id = "11",
        isRead = true,
        fileUrl = null,
    )
}

fun getMeChat(): ChatMemberUiModel {
    return ChatMemberUiModel(
        id = "q",
        name = "Юлия Кухтина",
        username = "yuulkht",
        status = StatusPresentation.ONLINE,
        photoURL = "",
        isCurrentUser = true
    )
}

fun getNotMeChat(): ChatMemberUiModel {
    return ChatMemberUiModel(
        id = "s",
        name = "Марина Жукова",
        username = "jukova",
        status = StatusPresentation.ONLINE,
        photoURL = "",
        isCurrentUser = false
    )
}