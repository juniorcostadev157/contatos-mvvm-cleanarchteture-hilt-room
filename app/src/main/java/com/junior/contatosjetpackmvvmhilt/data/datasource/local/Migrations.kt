package com.junior.contatosjetpackmvvmhilt.data.datasource.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2){
    override fun migrate (db: SupportSQLiteDatabase){
        db.execSQL("ALTER TABLE contacts ADD COLUMN bairro TEXT NOT NULL DEFAULT ''")
        db.execSQL("ALTER TABLE contacts ADD COLUMN localidade TEXT NOT NULL DEFAULT ''")
        db.execSQL("ALTER TABLE contacts ADD COLUMN estado TEXT NOT NULL DEFAULT ''")
        db.execSQL("ALTER TABLE contacts ADD COLUMN logradouro TEXT NOT NULL DEFAULT ''")
    }
}



val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Cria tabela nova com o nome/colunas certas
        db.execSQL("""
            CREATE TABLE contacts_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                phone TEXT NOT NULL,
                complemento TEXT NOT NULL, -- novo nome
                cep TEXT NOT NULL,
                localidade TEXT NOT NULL,
                bairro TEXT NOT NULL,
                estado TEXT NOT NULL,
                logradouro TEXT NOT NULL
            )
        """.trimIndent())

        // Copia os dados da tabela antiga (address -> complemento)
        db.execSQL("""
            INSERT INTO contacts_new (id, name, phone, complemento, cep, localidade, bairro, estado, logradouro)
            SELECT id, name, phone, address, cep, localidade, bairro, estado, logradouro
            FROM contacts
        """.trimIndent())

        // Remove a antiga
        db.execSQL("DROP TABLE contacts")

        // Renomeia a nova
        db.execSQL("ALTER TABLE contacts_new RENAME TO contacts")
    }
}
