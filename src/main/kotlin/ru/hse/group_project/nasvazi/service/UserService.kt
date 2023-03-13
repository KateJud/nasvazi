package ru.hse.group_project.nasvazi.service

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.group_project.nasvazi.model.entity.CodeEntity
import ru.hse.group_project.nasvazi.model.entity.UserEntity
import ru.hse.group_project.nasvazi.model.enums.ResponseStatus
import ru.hse.group_project.nasvazi.model.enums.UserRole
import ru.hse.group_project.nasvazi.model.response.AuthResponse
import ru.hse.group_project.nasvazi.repository.UserRepository

private const val ACCOUNT_SID = "ACe994996693bfc46d1a9b6e680619fba3"
private const val AUTH_TOKEN = "yfljtkj-ytdsyjcbvj-ytdjpvj;yj-z-yt-[jxe-'nj-ltkfnm-,jkmit"

/**
 * Сервис пользователя
 */
@Service
class UserService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun getOrCreate(phone: String, name: String): UserEntity {
        return userRepository.get(phone) ?: createUser(phone = phone, name = name)
    }

    fun login(phone: String, expectedRole: UserRole): AuthResponse {
        var user = userRepository.get(phone)
        // нет такого админа в бд
        if (user == null && expectedRole == UserRole.ADMIN) {
            return AuthResponse(ResponseStatus.FAIL, null)
        }

        // создаем обычного пользователя
        if (user == null && expectedRole == UserRole.USER) {
            user = createUser(phone, "LOGIN")
        }

        val userRoles: List<UserRole> = userRepository.getRoles(userId = user?.id!!)
        if (expectedRole == UserRole.ADMIN && !userRoles.contains(expectedRole)) {
            // User do not have permission to do that
            return AuthResponse(ResponseStatus.FAIL, null)
        }

        // generate code
        val code = (1000..9999).random()

        val codeEntity = CodeEntity(
            userId = user.id!!,
            code = code.toLong()
        )
        // save code
        userRepository.createCode(codeEntity)


        Twilio.init(ACCOUNT_SID, AUTH_TOKEN)
        val message: Message = Message.creator(
            PhoneNumber("+79169775431"),
            PhoneNumber("+79169775431"),
            code.toString()
        )
            .create()

        return AuthResponse(ResponseStatus.SUCCESS, code.toLong())
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
    ): UserEntity {
        val userEntity = UserEntity(phone = phone, name = name)
        val user = userRepository.create(userEntity)
        // add role_ref
        userRepository.createRole(user.id!!, UserRole.USER)
        return user
    }

    fun getById(id: Long): UserEntity {
        return userRepository.getById(id)
    }

    fun getAll(): List<UserEntity> {
        return userRepository.getAll()
    }

    fun addBonus(id: Long, bonus: Long) {
        userRepository.addBonus(bonus = bonus, userId = id)
    }
}
