package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.group_project.nasvazi.model.dto.UserDto
import ru.hse.group_project.nasvazi.model.entity.CodeEntity
import ru.hse.group_project.nasvazi.model.entity.UserEntity
import ru.hse.group_project.nasvazi.model.enums.ResponseStatus
import ru.hse.group_project.nasvazi.model.enums.UserRole
import ru.hse.group_project.nasvazi.model.request.LoginAuthRequest
import ru.hse.group_project.nasvazi.model.response.LoginAuthResponse
import ru.hse.group_project.nasvazi.repository.UserRepository
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * Сервис пользователя
 */
@Service
class UserService(
    private val userRepository: UserRepository,
    private val httpClient: HttpClient,
) {

    @Transactional
    fun getOrCreate(phone: String, name: String): UserEntity {
        return userRepository.getByPhone(phone) ?: createUser(phone = phone, name = name)
    }

    fun login(
        request: LoginAuthRequest
    ): LoginAuthResponse {
        val phone = request.phone
        val expectedRole = request.expectedRole
        val name = request.name
        val chatId = request.chatId

        var user = userRepository.getByPhone(phone)
        // нет такого админа в бд
        if (user == null && expectedRole == UserRole.ADMIN) {
            return LoginAuthResponse(ResponseStatus.FAIL, null, null)
        }

        // создаем обычного пользователя
        if (user == null && expectedRole == UserRole.USER) {
            user = createUser(phone = phone, name = name, chatId = chatId)
        }

        val userRoles: List<UserRole> = userRepository.getRoles(userId = user?.id!!)
        if (expectedRole == UserRole.ADMIN && !userRoles.contains(expectedRole)) {
            // User do not have permission to do that
            return LoginAuthResponse(ResponseStatus.FAIL, null, null)
        }

        // generate code
        val code = generateAndSaveCode(user).toLong()

        // sendSms(code = code, phone = phone)

        return LoginAuthResponse(ResponseStatus.SUCCESS, code, userId = user.id)
    }

    private fun generateAndSaveCode(user: UserEntity): Int {
        val code = (1000..9999).random()

        val codeEntity = CodeEntity(
            userId = user.id!!,
            code = code.toLong()
        )
        // save code
        userRepository.createCode(codeEntity)
        return code
    }

    private val LOGIN = "katejud"
    private val PASSWORD = "28074802759"
    private val MEASSAGE_TEMPLATE = "code"//"Your%20NASVAZI%20verification%20code:%20"
    private val SMS_URI = "http://api.smsfeedback.ru/messages/v2/send/"

    private fun sendSms(phone: String, code: Long) {
        // todo if флаг()
        val message = MEASSAGE_TEMPLATE + code
        //http://api.smsfeedback.ru/messages/v2/send/?login=katejud&password=28074802759&phone=79169775431&text=test
        val formattedPhone = phone.replace("-", "").replace(" ", "").replace("+", "")
        // Номер начиная без знака +
        val uri = "$SMS_URI?login=$LOGIN&password=$PASSWORD&phone=$formattedPhone&text=$message"
        val request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .GET()
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()
        val kek = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        println(kek)
    }

    private fun createUser(
        name: String,
        phone: String,
        chatId: Long = 0
    ): UserEntity {
        val userEntity = UserEntity(phone = phone, name = name, chatId = chatId)
        val user = userRepository.create(userEntity)
        // add role_ref
        userRepository.createRole(user.id!!, UserRole.USER)
        return user
    }

    fun getById(id: Long): UserEntity {
        return userRepository.getById(id)
    }

    fun getAll(): List<UserDto> {
        return userRepository.getAll()
    }

    fun addBonus(id: Long, bonus: Long) {
        validateCanReduce(userId = id, bonus = bonus)
        userRepository.addBonus(bonus = bonus, userId = id)
    }

    private fun validateCanReduce(userId: Long, bonus: Long) {
        val balance = userRepository.getById(userId).bonus
        if (userRepository.getById(userId).bonus + bonus < 0)
            throw IllegalArgumentException("Can not reduce $bonus bonuses because current balance is $balance ")
    }
}
