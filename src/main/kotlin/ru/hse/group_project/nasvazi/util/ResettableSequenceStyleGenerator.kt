package ru.hse.group_project.nasvazi.util

import org.hibernate.HibernateException
import org.hibernate.MappingException
import org.hibernate.boot.model.relational.Database
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.enhanced.SequenceStyleGenerator
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.Type
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Нужен только для того, чтобы сбрасывать кеш хибернейта по сиквенсам в тестинге.
 * Актуален при использовании типа генерации [GenerationType.SEQUENCE], так
 * как иначе сброс сиквенса не сбрасывает кеш хибернейта и он продолжает использовать
 * значения, которые больше текущего сиквенса.
 * Творчески скопипащено - https://stackoverflow.com/questions/43586360/how-to-reset-hibernate-sequence-generators
 */
class ResettableSequenceStyleGenerator : SequenceStyleGenerator() {
    @Volatile
    private var instanceCycle = 0

    @Volatile
    private var configureType: Type? = null

    @Volatile
    private var configureParams: Properties? = null

    @Volatile
    private var configureServiceRegistry: ServiceRegistry? = null

    @Volatile
    private var registerExportablesDatabase: Database? = null

    @Throws(MappingException::class)
    override fun configure(type: Type, params: Properties, serviceRegistry: ServiceRegistry) {
        configureType = type
        configureParams = params
        configureServiceRegistry = serviceRegistry
        super.configure(type, params, serviceRegistry)
    }

    @Throws(HibernateException::class)
    override fun generate(session: SharedSessionContractImplementor, `object`: Any): Any? {
        if (instanceCycle != cycle.get()) {
            super.configure(configureType, configureParams, configureServiceRegistry)
            super.registerExportables(registerExportablesDatabase)
            instanceCycle = cycle.get()
        }
        return super.generate(session, `object`)
    }

    override fun registerExportables(database: Database) {
        registerExportablesDatabase = database
        super.registerExportables(database)
    }

    companion object {
        private val cycle = AtomicInteger(0)

       // todo @VisibleForTesting
        fun resetAllInstances() {
            cycle.incrementAndGet()
        }
    }
}
