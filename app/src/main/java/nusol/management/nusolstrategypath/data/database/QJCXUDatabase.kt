package nusol.management.nusolstrategypath.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nusol.management.nusolstrategypath.data.dao.BookingDao
import nusol.management.nusolstrategypath.data.database.converter.Converters
import nusol.management.nusolstrategypath.data.entity.BookingEntity

@Database(
    entities = [BookingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class QJCXUDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}

