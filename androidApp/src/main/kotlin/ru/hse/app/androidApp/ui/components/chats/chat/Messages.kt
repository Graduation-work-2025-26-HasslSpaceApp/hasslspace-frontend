package ru.hse.app.androidApp.ui.components.chats.chat

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
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
import ru.hse.app.androidApp.ui.entity.model.chats.MessageUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Messages(
    messages: List<MessageUiModel>,
    scrollState: LazyListState,
    onAuthorClick: (ServerMemberUiModel?) -> Unit,
    me: ServerMemberUiModel,
    isDarkTheme: Boolean,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    val jumpToBottomThreshold = 56.dp
    val scope = rememberCoroutineScope()
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
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Log.println(Log.INFO, "ad", messages.map { it.timestamp }.toString())

            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index - 1)?.author
                val nextAuthor = messages.getOrNull(index + 1)?.author
                val content = messages[index]
//                val messageDate = content.timestamp.toLocalDate()
                val isFirstMessageByAuthor = prevAuthor != content.author
                val isLastMessageByAuthor = nextAuthor != content.author

//                val showDayHeader = when {
//                    lastDate == null || messageDate != lastDate -> {
//                        lastDate = messageDate
//                        true
//                    }
//
//                    else -> false
//                }
//
//                if (showDayHeader) {
//                    val dayHeader = "Сегодня"
//
//                    item {
//                        DayHeader(dayString = dayHeader)
//                    }
//                }

                item {
                    Message(
                        onAuthorClick = onAuthorClick,
                        msg = content,
                        isUserMe = content.author?.id == me.id,
                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                        isLastMessageByAuthor = isLastMessageByAuthor,
                        isDarkTheme = isDarkTheme,
                        imageLoader = imageLoader,
                    )
                }
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

        // todo нехорошие цвета в темной теме
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
    onAuthorClick: (ServerMemberUiModel?) -> Unit,
    msg: MessageUiModel,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    isDarkTheme: Boolean,
    imageLoader: ImageLoader
) {
    val borderColor = if (isUserMe) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.surfaceContainer
    }

    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Row(modifier = spaceBetweenAuthors) {
        if (isLastMessageByAuthor) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = if (msg.author == null) "" else msg.author.avatarUrl,
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
            AuthorNameTimestamp(msg)
        }
        ChatItemBubble(
            msg,
            isUserMe,
        )
        if (isFirstMessageByAuthor) {
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorNameTimestamp(msg: MessageUiModel) {
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        VariableMedium(
            text = msg.author?.name ?: "Unknown",
            fontSize = 14.sp,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        VariableLight(
            text = formatMessageTime(msg.timestamp),
            fontSize = 12.sp,
            modifier = Modifier.alignBy(LastBaseline),
            fontColor = MaterialTheme.colorScheme.outline,
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

@Composable
fun ChatItemBubble(message: MessageUiModel, isUserMe: Boolean) {

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
            ClickableMessage(
                message = message,
                isUserMe = isUserMe,
            )
        }

        //TODO

//        message.image?.let {
//            Spacer(modifier = Modifier.height(4.dp))
//            Surface(
//                color = backgroundBubbleColor,
//                shape = ChatBubbleShape,
//            ) {
//                Image(
//                    painter = painterResource(it),
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier.size(160.dp),
//                    contentDescription = stringResource(id = R.string.attached_image),
//                )
//            }
//        }
    }
}

@Composable
fun ClickableMessage(message: MessageUiModel, isUserMe: Boolean) {
    val uriHandler = LocalUriHandler.current

    val styledMessage = messageFormatter(
        text = message.content,
        primary = isUserMe,
    )

    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
        modifier = Modifier.padding(16.dp),
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        //todo Обработка нажатия на имя
                        else -> Unit
                    }
                }
        },
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
            dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        }
    }
}

fun getMessagePreview(): MessageUiModel {
    return MessageUiModel(
        author = getMe(),
        content = "Hello, how are you?",
        timestamp = LocalDateTime.now(),
    )
}

fun getMe(): ServerMemberUiModel {
    return ServerMemberUiModel(
        id = "q",
        name = "Юлия Кухтина",
        nickname = "yuulkht",
        status = StatusPresentation.ACTIVE,
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
        status = StatusPresentation.ACTIVE,
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
        author = getMe(),
        content = "Hello, how are you?",
        timestamp = LocalDateTime.now().minusDays(1),
    )

    AppTheme(isDark = false) {
        AuthorNameTimestamp(msg = message)
    }
}

@Preview(showBackground = true)
@Composable
fun AuthorNameTimestampPreviewDark() {
    val message = MessageUiModel(
        author = getMe(),
        content = "Hello, how are you?",
        timestamp = LocalDateTime.now(),
    )

    AppTheme(isDark = true) {
        AuthorNameTimestamp(msg = message)
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
            me = getMe(),
            isDarkTheme = false,
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxSize()
        )
    }
}

val messages = listOf(
    MessageUiModel(
        author = getMe(),
        content = "Привет! Как ты? Я так давно не слышала от тебя. У меня всё в порядке, но было много работы. Недавно закончила проект, теперь можно немного расслабиться и посвятить время себе. Как ты провёл эти пару недель? Надеюсь, у тебя всё хорошо.",
        timestamp = LocalDateTime.now().minusDays(1),
    ),
    MessageUiModel(
        author = getMe(),
        content = "Сегодня я поехала на выставку, было очень интересно! Столько новых идей, которые можно применить в своей работе. Вообще, в последнее время чувствую, что много открываю для себя. А ты что нового узнал за последнее время?",
        timestamp = LocalDateTime.now().minusHours(2),
    ),
    MessageUiModel(
        author = getNotMe(),
        content = "Привет, как дела? Всё отлично, просто была занята подготовкой к экзаменам, но сейчас у меня всё свободно. Вчера прошёл последний экзамен, и я чувствую себя свободной! Как ты? Что нового?",
        timestamp = LocalDateTime.now().minusHours(1),
    ),
    MessageUiModel(
        author = getNotMe(),
        content = "На выходных я была на открытии нового кафе, и это было просто потрясающе! Там такая уютная атмосфера, и еда невероятная. Мы с друзьями даже заказали несколько блюд на пробу, и все они были восхитительны. Я бы порекомендовала тебе сходить, если будешь в этом районе.",
        timestamp = LocalDateTime.now(),
    ),
    MessageUiModel(
        author = getAlexey(),
        content = "Привет, Юля! С радостью прочитал твой последний пост в блоге. Ты так вдохновляюще пишешь, что всегда интересно прочитать твои новые статьи. Надеюсь, у тебя всё отлично! Как у тебя дела на работе? Я тут немного думаю о смене профессии, хочу попробовать что-то новое, но пока не решаюсь.",
        timestamp = LocalDateTime.now().minusDays(3),
    ),
    MessageUiModel(
        author = getAlexey(),
        content = "Кроме того, в этом месяце планирую заняться путешествиями. Хочу посетить несколько стран, и, возможно, даже попробую жить в другой стране несколько месяцев, чтобы узнать культуру изнутри. Ты когда-нибудь думала о таком? Вдруг получится! А ещё, я планирую улучшить свои навыки в языке, так что будет интересно этим заняться.",
        timestamp = LocalDateTime.now().minusDays(2),
    ),
    MessageUiModel(
        author = getEkaterina(),
        content = "Привет, как ты? Давно не общались! У меня всё хорошо, но столько всего произошло за последнее время. Я только что закончила курс по нейросетям и теперь планирую запускать свой первый проект. Не могу дождаться, когда смогу поделиться результатами с коллегами. А ты как? Чем занимаешься?",
        timestamp = LocalDateTime.now().minusDays(4),
    ),
    MessageUiModel(
        author = getEkaterina(),
        content = "Недавно начала читать книгу по психологии и вообще, она перевернула мой взгляд на многие вещи. Знаешь, это так интересно — наблюдать, как люди воспринимают мир и какие у них убеждения. Стало гораздо легче понимать поведение людей, когда ты знаешь, что происходит у них в голове. Может, ты тоже хочешь почитать? Я могу порекомендовать!",
        timestamp = LocalDateTime.now().minusDays(5),
    ),
    MessageUiModel(
        author = getMe(),
        content = "Сегодня как раз на работе обсуждали, как важно отдыхать и перезаряжаться. Работа - это, конечно, важно, но без восстановления ресурсов невозможно быть эффективным. А как ты восстанавливаешь силы? Я, например, люблю проводить время на природе или делать йогу. Это так помогает мне быть в форме!",
        timestamp = LocalDateTime.now().minusHours(6),
    ),
    MessageUiModel(
        author = getMe(),
        content = "Я вот недавно начала слушать подкасты по психологии и нейробиологии. Очень интересно, как работает наш мозг, как влияют разные факторы на поведение. В общем, стало много нового узнавать. Подумываю о том, чтобы изучить психологию на более глубоком уровне. Ты что-нибудь подобное слушаешь?",
        timestamp = LocalDateTime.now().minusHours(7),
    ),
)

fun getAlexey(): ServerMemberUiModel {
    return ServerMemberUiModel(
        id = "a1",
        name = "Алексей Иванов",
        nickname = "alexey_ivanov",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        mainRole = null,
        allRoles = listOf(),
        isOwner = false
    )
}

fun getEkaterina(): ServerMemberUiModel {
    return ServerMemberUiModel(
        id = "e1",
        name = "Екатерина Смирнова",
        nickname = "katya_smirnova",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        mainRole = null,
        allRoles = listOf(),
        isOwner = false
    )
}


fun getMessagePreview(isCurrentUser: Boolean): MessageUiModel {
    return MessageUiModel(
        author = if (isCurrentUser) getMe() else getNotMe(),
        content = if (isCurrentUser) "Привет, это мое сообщение." else "Привет, как ты?",
        timestamp = LocalDateTime.now(),
    )
}