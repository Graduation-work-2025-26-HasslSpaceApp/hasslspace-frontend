package ru.hse.app.androidApp.ui.components.settings.usersettings

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.yalantis.ucrop.UCrop
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun PhotoSetting(
    selectedImageUri: Uri?,
    onSelectedImageUri: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
    onPhotoPickClick: () -> Unit,
) {
    val context = LocalContext.current

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val croppedUri = UCrop.getOutput(result.data!!)
                onSelectedImageUri(croppedUri)
            }
        }
    )

    //TODO доделать взаимодействие с фото
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                // TODO Добавить инъекцию зависимостей
                CropProfilePhotoService().startCrop(it, context, cropLauncher)
//                val fileSizeInBytes = viewModel.cropProfilePhotoService.getImageSize(context, it)
//                val maxSizeInBytes = 20 * 1024 * 1024
//
//                if (fileSizeInBytes > maxSizeInBytes) {
//                    ToastManager(context).showToast("Файл слишком большой. Максимальный размер 20 МБ.")
//                } else {
//                    CropProfilePhotoService().startCrop(it, context, cropLauncher)
//                }
            }
        }
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VariableLight(
            text = "Фото",
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Row(
            modifier = Modifier
                .clickable { onPhotoPickClick() },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            selectedImageUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Выбранное изображение",
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray, shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.reload_photo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(15.dp)
                    .clickable { imagePickerLauncher.launch("image/*") }
            )

        }
    }
}

@Preview(
    name = "Light Theme - Without Photo",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PhotoSettingWithoutPhotoPreview() {
    AppTheme(isDark = false) {
        PhotoSetting(
            selectedImageUri = null,
            onSelectedImageUri = {},
            onPhotoPickClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Preview(
    name = "Dark Theme - With Photo",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PhotoSettingWithPhotoPreview() {
    AppTheme(isDark = true) {
        PhotoSetting(
            selectedImageUri = Uri.parse("android.resource://ru.hse.app.androidApp/drawable/avatar_default_dark"),
            onSelectedImageUri = {},
            onPhotoPickClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Preview(
    name = "Light Theme - With Photo",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PhotoSettingLightWithPhotoPreview() {
    AppTheme(isDark = false) {
        PhotoSetting(
            selectedImageUri = Uri.parse("android.resource://ru.hse.app.androidApp/drawable/avatar_default_dark"),
            onSelectedImageUri = {},
            onPhotoPickClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}