package no.sd.sdbot.testcontainer

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class TestWithDbContainer {

    companion object dbContainer : PostgreSQLContainer<dbContainer>(
        "postgres:latest"
    ) {
        init {
            withDatabaseName("sdbot_db")
            withUsername("sdbot")
            withPassword("sdbot")
            withCreateContainerCmdModifier { cmd -> cmd.withName("sdbot-test-container") }
            start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", dbContainer::getJdbcUrl)
            registry.add("spring.datasource.username", dbContainer::getUsername)
            registry.add("spring.datasource.password", dbContainer::getPassword)
        }
    }
}
