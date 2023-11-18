package com.example.shared.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
            AppDatabase.Schema.create(it)
        }
    }
}