package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import ru.hse.group_project.nasvazi.model.enums.NsqlConfigKey
import ru.hse.group_project.nasvazi.repository.SystemPropertyRepository

@Service
class SystemPropertyService(
    private val systemPropertyRepository: SystemPropertyRepository
) {

    fun getConfigAsBoolean(key:NsqlConfigKey) : Boolean{
     val systemProperty= systemPropertyRepository.getSystemProperty(key)
        return systemProperty?.value.toBoolean()
    }
}
