package ru.hse.group_project.nasvazi.config

import jdk.jshell.spi.ExecutionControl.NotImplementedException
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.time.temporal.ChronoUnit

const val SYSTEM_PROPERTY_CACHE = "systemPropertyCache"

private val ONE_MIN: Duration = ChronoUnit.MINUTES.duration

@Configuration
@EnableCaching
open class CacheConfig {

    @Bean
    open fun cacheManager(): CacheManager {
        val manager = SimpleCacheManager()
        manager.setCaches(
            listOf(
                buildFixedTimeEvictionCache(SYSTEM_PROPERTY_CACHE, ONE_MIN)
            )
        )

        return manager
    }

    /**
     * Значение из кэша удаляется по прошествии заданного времени
     */
    private fun buildFixedTimeEvictionCache(name: String, expire: Duration) {
        // todo add dependencies
        // return CaffeineCache(
        //     name,
        //     Caffeine.newBuilder()
        //         .expireAfterWrite(expire)
        //         .recordStats()
        //         .build()
        // )
    }
}

private fun SimpleCacheManager.setCaches(listOf: List<Unit>) {
}
