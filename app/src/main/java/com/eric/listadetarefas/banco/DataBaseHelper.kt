package com.eric.listadetarefas.banco

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlin.math.log

class DataBaseHelper(context : Context) : SQLiteOpenHelper (
        context, NomeBanco, null, VERSAO
){
    companion object{
        const val NomeBanco = "listaDeTarefas.db"
        const val VERSAO = 1
        const val NomeTabela = "tarefas"
        const val Coluna_IdTarefas = "id_tarefa"
        const val Coluna_DataCadastro = "data_cadastro"
        const val Coluna_DescricaoDaTarefa = "descicao"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "CREATE TABLE IF NOT EXISTS $NomeTabela(" +
                "$Coluna_IdTarefas INTEGER not NULL PRIMARY KEY AUTOINCREMENT," +
                "$Coluna_DescricaoDaTarefa VARCHAR(70)," +
                "$Coluna_DataCadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL( sql )
            Log.i("info.db","sucesso ao  criar a tabela")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db","Falha ao criar a tabela")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}