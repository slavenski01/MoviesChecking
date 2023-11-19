package com.example.project.shared.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.shared.cache.db.MoviesDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MoviesDatabase.Schema, "moviesDB.db")
    }
}