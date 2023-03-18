package ru.hse.group_project.nasvazi.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.group_project.nasvazi.model.dto.UserDto
import ru.hse.group_project.nasvazi.model.entity.CodeEntity
import ru.hse.group_project.nasvazi.model.entity.UserEntity
import ru.hse.group_project.nasvazi.model.enums.NsqlConfigKey
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
    private val systemPropertyService: SystemPropertyService,
    @Value("\${sms.password}") private val smsPassword: String,
    @Value("\${sms.uri}") private val smsUri: String,
    @Value("\${sms.login}") private val smsLogin: String,
) {

    private val MEASSAGE_TEMPLATE = "code"

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

        sendSms(code = code, phone = phone)

        return LoginAuthResponse(ResponseStatus.SUCCESS, code, userId = user.id)
    }

    fun checkSms(userId: Long, code: Long): ResponseStatus {
        return if (isSmsEnabled()) {
            checkSmsCode(userId, code)
        } else {
            ResponseStatus.SUCCESS
        }
    }

    private fun checkSmsCode(userId: Long, code: Long): ResponseStatus {
        return if (userRepository.checkCodeAndUser(userId, code))
            ResponseStatus.SUCCESS
        else ResponseStatus.FAIL
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

    private fun sendSms(phone: String, code: Long) {
        if (isSmsEnabled()) {
            val message = MEASSAGE_TEMPLATE + code
            val formattedPhone = phone.replace("-", "").replace(" ", "").replace("+", "")
            // Номер начиная без знака +
            val uri = "$smsUri?login=$smsLogin&password=$smsPassword&phone=$formattedPhone&text=$message"
            val request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build()
            httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        }
    }

    private fun isSmsEnabled() = systemPropertyService.getConfigAsBoolean(NsqlConfigKey.ENABLE_SMS)

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
