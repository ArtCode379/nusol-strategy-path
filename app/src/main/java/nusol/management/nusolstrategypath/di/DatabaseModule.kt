package nusol.management.nusolstrategypath.di

import androidx.room.Room
import nusol.management.nusolstrategypath.data.database.QJCXUDatabase
import org.koin.dsl.module

private const val DB_NAME = "qjcxu_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = QJCXUDatabase::class.java,
        name = DB_NAME
        ).build()
    }

    single { get<QJCXUDatabase>().bookingDao()}

}