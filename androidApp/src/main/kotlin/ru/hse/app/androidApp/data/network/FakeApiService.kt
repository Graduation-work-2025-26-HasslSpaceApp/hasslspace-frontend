package ru.hse.app.androidApp.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import ru.hse.app.androidApp.data.model.ServerInfoDto
import ru.hse.app.androidApp.data.model.UserDto
import ru.hse.app.androidApp.data.model.UserInfoDto

class FakeApiService : ApiService {

    private val fakeUser =
        UserDto("Юлия Кухтина", "yuulkht", "testuser@example.com", "В сети","https://i.postimg.cc/J4DLnLCS/accountphoto.jpg", "Описание профиля описание профиля описание профиля описание профиля")

    private val fakeFriends = listOf(
        UserInfoDto("1", "Алексей Иванов", "alex_ivanov", "В сети", "https://i.postimg.cc/1XfF8BZh/friend1.jpg"),
        UserInfoDto("2", "Мария Петрова", "masha_petrov", "Невидимка", "https://i.postimg.cc/K8Jxt5wQ/friend2.jpg"),
        UserInfoDto("3", "Ирина Смирнова", "irina_smirnov", "Не беспокоить", "https://i.postimg.cc/3RmDC39Y/friend3.jpg"),
        UserInfoDto("4", "Дмитрий Козлов", "dmitry_kozlov", "Не активен", "https://i.postimg.cc/tgG6rqDh/friend4.jpg"),
        UserInfoDto("5", "Елена Федорова", "elena_fedorova", "В сети", "https://i.postimg.cc/MTf5d7Z8/friend5.jpg"),
        UserInfoDto("6", "Иван Ребров", "ivan_rebrov", "Не беспокоить", "https://i.postimg.cc/fyzsbpdZ/friend6.jpg"),
        UserInfoDto("7", "Татьяна Белова", "tatiana_belova", "Невидимка", "https://i.postimg.cc/9F0CjTjR/friend7.jpg"),
        UserInfoDto("8", "Максим Сидоров", "maxim_sidorov", "В сети", "https://i.postimg.cc/7Z1vBGzF/friend8.jpg"),
        UserInfoDto("9", "Ольга Червонова", "olga_chervonova", "Не активен", "https://i.postimg.cc/Y2myr5x6/friend9.jpg"),
        UserInfoDto("10", "Петр Лебедев", "peter_lebedev", "Невидимка", "https://i.postimg.cc/mkGYD5Kp/friend10.jpg")
    )

    private val fakeServers = listOf(
        ServerInfoDto("1", "Сервер 1", 123, "https://i.postimg.cc/2yzCRhY8/server1.jpg"),
        ServerInfoDto("2", "Сервер 2", 15, "https://i.postimg.cc/XJH1pcGs/server2.jpg"),
        ServerInfoDto("3", "Сервер 3", 10, null),
        ServerInfoDto("4", "Сервер 4", 2, "https://i.postimg.cc/mg9f6RYn/server4.jpg"),
        ServerInfoDto("5", "Сервер 5", 28, "https://i.postimg.cc/BZkQQH7X/server5.jpg"),
        ServerInfoDto("6", "Сервер 6", 500, "https://i.postimg.cc/vZq3ZyGG/server6.jpg"),
        ServerInfoDto("7", "Сервер 7", 200, null),
        ServerInfoDto("8", "Сервер 8", 850, "https://i.postimg.cc/mtJ7VHTN/server8.jpg"),
        ServerInfoDto("9", "Сервер 9", 50, "https://i.postimg.cc/8C3bbFGG/server9.jpg"),
        ServerInfoDto("10", "Сервер 10", 600, "https://i.postimg.cc/QcY6h8tT/server10.jpg")
    )

    override suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        nickname: String
    ): Response<String> {
        return Response.success("mock_jwt_token")
    }

    override suspend fun loginUser(email: String, password: String): Response<String> {
        return Response.success("mock_jwt_token")
    }

    override suspend fun checkVerification(): Response<Boolean> {
        return Response.success(true)
    }

    override suspend fun sendVerificationCode(email: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun checkVerificationCode(verificationCode: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun getUserInfo(): Response<UserDto> {
        return Response.success(fakeUser)
    }

    override suspend fun saveUserPhoto(photoUrl: String): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        type: RequestBody,
        photoUrl: RequestBody?
    ): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun getFriends(): Response<List<UserInfoDto>> {
        return Response.success(fakeFriends)
    }

    override suspend fun getServers(): Response<List<ServerInfoDto>> {
        return Response.success(fakeServers)
    }
}
