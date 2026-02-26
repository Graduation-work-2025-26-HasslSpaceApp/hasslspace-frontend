package ru.hse.app.androidApp.screen.servers

import androidx.lifecycle.ViewModel
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService
import ru.hse.app.androidApp.domain.usecase.servers.CreateServerRoleUseCase
import ru.hse.app.androidApp.domain.usecase.servers.CreateServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.CreateTextChannelUseCase
import ru.hse.app.androidApp.domain.usecase.servers.CreateVoiceChannelUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteChannelUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerInvitationUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerMemberUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInfoUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerRolesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerUserRolesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetUserServersUseCase
import ru.hse.app.androidApp.domain.usecase.servers.JoinServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchChannelPropertiesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchServerOwnerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchServerPropertiesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.SendServerInvitationUseCase
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class ServersViewModel @Inject constructor(
    private val createServerRoleUseCase: CreateServerRoleUseCase,
    private val createServerUseCase: CreateServerUseCase,
    private val createTextChannelUseCase: CreateTextChannelUseCase,
    private val createVoiceChannelUseCase: CreateVoiceChannelUseCase,

    private val deleteChannelUseCase: DeleteChannelUseCase,
    private val deleteServerInvitationUseCase: DeleteServerInvitationUseCase,
    private val deleteServerMemberUseCase: DeleteServerMemberUseCase,
    private val deleteServerUseCase: DeleteServerUseCase,

    private val getServerInfoUseCase: GetServerInfoUseCase,
    private val getServerInvitationUseCase: DeleteServerInvitationUseCase,
    private val getServerRoleInfoUseCase: GetServerInfoUseCase,
    private val getServerRolesUseCase: GetServerRolesUseCase,
    private val getServerUserRolesUseCase: GetServerUserRolesUseCase,
    private val getUserServersUseCase: GetUserServersUseCase,

    private val joinServerUseCase: JoinServerUseCase,

    private val patchChannelPropertiesUseCase: PatchChannelPropertiesUseCase,
    private val patchServerOwnerUseCase: PatchServerOwnerUseCase,
    private val patchServerPropertiesUseCase: PatchServerPropertiesUseCase,
    private val patchServerRoleUseCase: PatchServerOwnerUseCase,

    private val sendServerInvitationUseCase: SendServerInvitationUseCase,

    private val photoConverterService: PhotoConverterService,
    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val imageLoader: ImageLoader

): ViewModel() {

}