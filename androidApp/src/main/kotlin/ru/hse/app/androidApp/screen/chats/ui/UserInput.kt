package ru.hse.app.androidApp.screen.chats.ui

import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.emojipicker.EmojiPickerView
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.theme.AppTheme

enum class InputSelector {
    NONE,
    EMOJI,
}

enum class EmojiStickerSelector {
    EMOJI,
    STICKER,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInput(
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: () -> Unit = {},
    isDark: Boolean
) {
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }

    if (currentInputSelector != InputSelector.NONE) {
        BackHandler(onBack = dismissKeyboard)
    }

    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var textFieldFocusState by remember { mutableStateOf(false) }

    Surface(tonalElevation = 2.dp, contentColor = MaterialTheme.colorScheme.secondary) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            UserInputText(
                textFieldValue = textState,
                onTextChanged = { textState = it },
                keyboardShown = currentInputSelector == InputSelector.NONE && textFieldFocusState,
                onTextFieldFocused = { focused ->
                    if (focused) {
                        currentInputSelector = InputSelector.NONE
                        resetScroll()
                    }
                    textFieldFocusState = focused
                },
                onMessageSent = {
                    onMessageSent(textState.text)
                    textState = TextFieldValue()
                    resetScroll()
                },
                focusState = textFieldFocusState,
            )
            UserInputSelector(
                onSelectorChange = { currentInputSelector = it },
                sendMessageEnabled = textState.text.isNotBlank(),
                onMessageSent = {
                    onMessageSent(textState.text)
                    textState = TextFieldValue()
                    resetScroll()
                    dismissKeyboard()
                },
                currentInputSelector = currentInputSelector,
                makeKeyboardVisible = {
                    textFieldFocusState = true
                }
            )
            SelectorExpanded(
                onCloseRequested = dismissKeyboard,
                onTextAdded = { textState = textState.addText(it) },
                currentSelector = currentInputSelector,
                isDark = isDark
            )
        }
    }
} //todo не появляется клавиатура после убирания смайликов

private fun TextFieldValue.addText(newString: String): TextFieldValue {
    val newText = this.text.replaceRange(
        this.selection.start,
        this.selection.end,
        newString,
    )
    val newSelection = TextRange(
        start = newText.length,
        end = newText.length,
    )

    return this.copy(text = newText, selection = newSelection)
}

@Composable
private fun SelectorExpanded(
    currentSelector: InputSelector,
    onCloseRequested: () -> Unit,
    onTextAdded: (String) -> Unit,
    isDark: Boolean
) {
    if (currentSelector == InputSelector.NONE) return

    val focusRequester = remember { FocusRequester() }
    SideEffect {
        if (currentSelector == InputSelector.EMOJI) {
            focusRequester.requestFocus()
        }
    }

    Surface(tonalElevation = 8.dp) {
        when (currentSelector) {
            InputSelector.EMOJI -> EmojiSelector(onTextAdded, focusRequester, isDark)
        }
    }
}

@Composable
private fun UserInputSelector(
    onSelectorChange: (InputSelector) -> Unit,
    sendMessageEnabled: Boolean,
    onMessageSent: () -> Unit,
    currentInputSelector: InputSelector,
    modifier: Modifier = Modifier,
    makeKeyboardVisible: () -> Unit
) {
    Row(
        modifier = modifier
            .height(72.dp)
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InputSelectorButton(
            onClick = {
                if (currentInputSelector == InputSelector.EMOJI) {
                    onSelectorChange(InputSelector.NONE)
                    makeKeyboardVisible()
                } else {
                    onSelectorChange(InputSelector.EMOJI)
                }
            },
            icon = painterResource(id = R.drawable.ic_mood),
            selected = currentInputSelector == InputSelector.EMOJI,
            description = "",
        )

        val border = if (!sendMessageEnabled) {
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            )
        } else {
            null
        }
        Spacer(modifier = Modifier.weight(1f))

        val disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

        val buttonColors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.Transparent,
            disabledContentColor = disabledContentColor,
        )

        Button(
            modifier = Modifier.height(36.dp),
            enabled = sendMessageEnabled,
            onClick = onMessageSent,
            colors = buttonColors,
            border = border,
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                "Отправить",
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun InputSelectorButton(
    onClick: () -> Unit,
    icon: Painter,
    description: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundModifier = if (selected) {
        Modifier.background(
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = RoundedCornerShape(14.dp),
        )
    } else {
        Modifier
    }
    IconButton(
        onClick = onClick,
        modifier = modifier.then(backgroundModifier),
    ) {
        val tint = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outline
        }
        Icon(
            icon,
            tint = tint,
            modifier = Modifier
                .padding(8.dp)
                .size(56.dp),
            contentDescription = description,
        )
    }
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalFoundationApi
@Composable
private fun UserInputText(
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    keyboardShown: Boolean,
    onTextFieldFocused: (Boolean) -> Unit,
    onMessageSent: (String) -> Unit,
    focusState: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp, max = 200.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        Box() {
            UserInputTextField(
                textFieldValue,
                onTextChanged,
                onTextFieldFocused,
                keyboardShown,
                keyboardType,
                focusState,
                onMessageSent,
                Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Текстовый ввод"
                        keyboardShownProperty = keyboardShown
                    },
            )
        }
    }
}

@Composable
private fun UserInputTextField(
    textFieldValue: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onTextFieldFocused: (Boolean) -> Unit,
    keyboardShown: Boolean,
    keyboardType: KeyboardType,
    focusState: Boolean,
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var lastFocusState by remember { mutableStateOf(false) }
    AdjustableTextField(
        textFieldValue = textFieldValue,
        onTextChanged = onTextChanged,
        onTextFieldFocused = onTextFieldFocused,
        onMessageSent = onMessageSent,
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "Текстовый ввод"
                keyboardShownProperty = keyboardShown
            },
        keyboardType = keyboardType
    )
}

@Composable
fun AdjustableTextField(
    textFieldValue: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onTextFieldFocused: (Boolean) -> Unit,
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    var isFocused by remember { mutableStateOf(false) }

    val maxLines = 6
    val lineHeight = 24.dp

    val numberOfLines = (textFieldValue.text.length / 30) + 1

    BasicTextField(
        value = textFieldValue,
        onValueChange = { onTextChanged(it) },
        modifier = modifier
            .padding(start = 32.dp, top = 16.dp, end = 32.dp)
            .onFocusChanged { state ->
                if (isFocused != state.isFocused) {
                    onTextFieldFocused(state.isFocused)
                    isFocused = state.isFocused
                }
            }
            .heightIn(min = 48.dp, max = 144.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.None
        ),
        keyboardActions = KeyboardActions {
            if (textFieldValue.text.isNotBlank()) onMessageSent(textFieldValue.text)
        },
        maxLines = maxLines,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground),
        decorationBox = { innerTextField ->
            Box {
                if (textFieldValue.text.isEmpty() && !isFocused) {
                    Text(
                        text = "Напишите сообщение",
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.outline),
                        modifier = Modifier
                    )
                }
                innerTextField()
            }
        }
    )
}


@Composable
fun EmojiSelector(
    onTextAdded: (String) -> Unit,
    focusRequester: FocusRequester,
    isDark: Boolean
) {
    val background = MaterialTheme.colorScheme.background
    var selected by remember { mutableStateOf(EmojiStickerSelector.EMOJI) }

    Column(
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusTarget()
            .semantics { contentDescription = "Выбор эмодзи" }
            .background(background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(background),
        ) {
            ExtendedSelectorInnerButton(
                text = "Эмодзи",
                onClick = { selected = EmojiStickerSelector.EMOJI },
                selected = true,
                modifier = Modifier.weight(1f),
            )
        }

        val themeRes = if (isDark) { // todo
            R.style.Theme_EmojiPicker_Dark
        } else {
            R.style.Theme_EmojiPicker_Light
        }


        AndroidView(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(),
            factory = { context ->
                val themedContext = ContextThemeWrapper(context, themeRes)

                EmojiPickerView(themedContext).apply {
                    emojiGridColumns = 11
                    emojiGridRows = 5F

                    setBackgroundColor(background.toArgb())


                    setOnEmojiPickedListener { emojiViewItem ->
                        onTextAdded(emojiViewItem.emoji)
                    }
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            update = { view -> }
        )
    }
}

@Composable
fun ExtendedSelectorInnerButton(
    text: String,
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = if (selected) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
        else Color.Transparent,
        disabledContainerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.74f),
    )
    TextButton(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .height(36.dp),
        colors = colors,
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserInputPreviewLight() {
    AppTheme(isDark = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
                .imePadding()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            UserInput(
                modifier = Modifier.fillMaxWidth(),
                onMessageSent = {},
                isDark = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInputPreviewDark() {
    AppTheme(isDark = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .imePadding()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            UserInput(
                modifier = Modifier.fillMaxWidth(),
                onMessageSent = {},
                isDark = true
            )
        }
    }
}
