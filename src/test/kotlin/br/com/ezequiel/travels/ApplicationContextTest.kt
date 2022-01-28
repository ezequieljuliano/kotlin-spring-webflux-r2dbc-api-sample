package br.com.ezequiel.travels

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.util.StreamUtils
import java.nio.charset.Charset

open class ApplicationContextTest {

    @Autowired
    private lateinit var r2dbcEntityTemplate: R2dbcEntityTemplate

    protected fun initializeDatabase() {
        val schema = StreamUtils.copyToString(
            ClassPathResource("db/migration/h2/V1.0.0.1__create_initial_structure.sql").inputStream,
            Charset.defaultCharset()
        )
        r2dbcEntityTemplate.databaseClient.sql(schema).fetch().rowsUpdated().block()
    }

}