package com.junior.contatosjetpackmvvmhilt.data.datasource.local


    import androidx.room.Database

    import androidx.room.RoomDatabase
    import com.junior.contatosjetpackmvvmhilt.data.datasource.local.dao.ContactDao

@Database(entities = [ContactEntity::class], version = 3, exportSchema = true)
    abstract class AppDataBase : RoomDatabase() {
        abstract fun contactDao(): ContactDao
    }
