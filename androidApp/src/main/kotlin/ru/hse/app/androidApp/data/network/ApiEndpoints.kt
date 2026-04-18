package ru.hse.app.androidApp.data.network

const val USER_SERVICE_URL = "/api/user-service"

// Вход и регистрация
const val LOGIN_URL = "/auth/login"
const val REGISTER_URL = "/auth/register"

// Верификация пользователя
const val SEND_CODE = "/users/me/verification"
const val CHECK_VERIFICATION = "/users/me/verification/status"
const val CHECK_VERIFICATION_CODE = "/users/me/verification/confirm"

// Управление профилем
const val GET_USER_PROFILE = "/users/me" // Получить профиль пользователя
const val UPDATE_PROFILE = "/users/me" // Обновить профиль пользователя
const val SET_USER_STATUS = "/users/me/status" // Установить статус
const val UPLOAD_USER_PHOTO = "/photos/user/upload?type=user" // Загрузить фото

// Взаимодействие с пользователями
const val GET_USER_PROFILE_BY_ID = "/users/profile" // Получить профиль пользователя по ID
const val FRIENDS_LIST = "/users/me/friends" // Получить список друзей
const val SEND_FRIEND_REQUEST = "/users/me/friends" // Отправить запрос дружбы
const val RESPOND_TO_FRIEND_REQUEST = "/friends/me/response" // Ответить на запрос дружбы
const val BLOCK_USER = "/users/block" // Заблокировать пользователя
const val REMOVE_FROM_FRIENDS = "/users/me/friends/delete" // Удалить из друзей

const val SERVER_SERVICE_URL = "/api/server-service"

// Создание и управление серверами
const val CREATE_SERVER_URL = "/servers" // Создать сервер
const val GET_SERVER_URL = "/servers" // Получить сервер
const val GET_SERVERS_LIST_URL = "/servers" // Получить список серверов
const val DELETE_SERVER_URL = "/servers" // Удалить сервер

const val UPLOAD_SERVER_PHOTO = "/photos/server/upload" // Загрузить фото

const val UPDATE_SERVER_URL = "/servers" // Обновить сервер

// Управление участниками серверов
const val JOIN_SERVER_URL = "/invites/join" // Присоединиться к серверу
const val LEAVE_SERVER_URL = "/servers/members/me" // Покинуть сервер
const val GET_SERVER_MEMBERS_URL = "/servers/members" // Список участников сервера
const val KICK_MEMBER_URL = "/servers/members" // Выгнать участника
const val GET_FRIENDS_NOT_IN_SERVER_URL =
    "/servers/members/not-in-server" // Список друзей, которых нет на сервере

const val UPDATE_SERVER_OWNER_URL = "/servers/members/change-owner" // Обновить владельца

// Приглашения
const val CREATE_INVITE_URL = "/servers/invites" // Создать приглашение
const val GET_ACTIVE_INVITES_URL = "/servers/invites" // Список активных приглашений

const val DELETE_INVITE_URL = "/servers/invites" // Удалить приглашение

// Роли
const val CREATE_ROLE_URL = "/servers/roles" // Создать роль
const val GET_ROLES_URL = "/servers/roles" // Список ролей
const val DELETE_ROLE_URL = "/servers/roles" // Удалить роль
const val UPDATE_ROLE_URL = "/servers/roles" // Обновить роль
const val ASSIGN_ROLE_URL = "/servers/members/roles" // Выдать роль
const val REVOKE_ROLE_URL = "/servers/members/roles" // Забрать роль

// Каналы
const val CREATE_CHANNEL_URL = "/servers/channels" // Создать канал
const val DELETE_CHANNEL_URL = "/servers/channels" // Удалить канал
const val GET_CHANNEL_INFO_URL = "/servers/channels" // Получить инфо о канале
const val UPDATE_CHANNEL_URL = "/servers/channels" // Обновить инфо о канале
const val DELETE_CHANNEL_PERMISSION_URL = "/servers/channels/permissions" // Удалить права на канал
const val ASSIGN_CHANNEL_PERMISSION_URL =
    "/servers/channels/permissions" // Назначить права роли на канал
const val GET_CHANNEL_PERMISSIONS_URL = "/servers/channels/permissions" // Получить права канала

// Звонки
const val VOICE_SERVICE_URL = "/api/voice-service"
const val GET_TOKEN_FOR_VOICE_ROOM = "/token"

// Чаты
const val GET_TOKEN_FOR_CENTRIFUGO = "/token"
const val CHAT_SERVICE_URL = "/api/chat-service"
const val GET_CHAT_URL = "/chats"
const val GET_MESSAGES_HISTORY_URL = "/chat/messages"
const val SEND_MESSAGE_URL = "/chats/message"
const val START_CHAT_URL = "/chats"