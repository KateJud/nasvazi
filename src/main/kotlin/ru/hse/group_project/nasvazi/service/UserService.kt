package ru.hse.group_project.nasvazi.service

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
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

private const val ACCOUNT_SID = "AC099936283bbaf1f451cf198df29c2339"
private const val AUTH_TOKEN = "c57fe3176ded45c4daecfb45e8b53180"

/**
 * Сервис пользователя
 */
@Service
class UserService(
    private val userRepository: UserRepository,
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
        val code = (1000..9999).random()

        val codeEntity = CodeEntity(
            userId = user.id!!,
            code = code.toLong()
        )
        // save code
        userRepository.createCode(codeEntity)

        return LoginAuthResponse(ResponseStatus.SUCCESS, code.toLong(), userId = user.id)
    }

    fun testTwilio() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN)
        val message: Message = Message.creator(
            PhoneNumber("+79169775431"),
            PhoneNumber("+79169775431"),
            "keks"
        ).create()
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
