package com.eric.listadetarefas.banco

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.eric.listadetarefas.model.Tarefa

class TarefaDAO(context: Context) : ITarefaDAO {

    private val escrita = DataBaseHelper(context).writableDatabase
    private val leitura = DataBaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {

        val conteudo = ContentValues()
        conteudo.put("${DataBaseHelper.Coluna_DescricaoDaTarefa}", tarefa.descricao)

        try {
            escrita.insert(
                DataBaseHelper.NomeTabela,
                null,
                conteudo
            )
            Log.i("info.db","sucesso ao salvar a tarefa")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db","Falha ao salvar a tarefa")
            return false
        }
        return true
    }

    override fun excluir(idTarefa: Int): Boolean {
        val args = arrayOf(idTarefa.toString())
        try {
            escrita.delete(
                DataBaseHelper.NomeTabela,
                "${DataBaseHelper.Coluna_IdTarefas} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao remover tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover tarefa")
            return false
        }

        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.idTarefa.toString())
        val conteudo = ContentValues()
        conteudo.put("${DataBaseHelper.Coluna_DescricaoDaTarefa}", tarefa.descricao)

        try {
            escrita.update(
                DataBaseHelper.NomeTabela,
                conteudo,
                "${DataBaseHelper.Coluna_IdTarefas} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao atualizar tarefa")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao atualizar tarefa")
            return false
        }

        return true
    }

    override fun ListarTarefas(): List<Tarefa> {

        val listarTarefa = mutableListOf<Tarefa>()

        val sql = "SELECT " +
                "${DataBaseHelper.Coluna_IdTarefas}, " +
                "${DataBaseHelper.Coluna_DescricaoDaTarefa}, " +
                "strftime('%m/%m/%Y %H:%M', ${DataBaseHelper.Coluna_DataCadastro}) AS ${DataBaseHelper.Coluna_DataCadastro} "+
                "FROM ${DataBaseHelper.NomeTabela};"

        val cursor = leitura.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex( DataBaseHelper.Coluna_IdTarefas )
        val indiceDescricao = cursor.getColumnIndex( DataBaseHelper.Coluna_DescricaoDaTarefa )
        val indiceData = cursor.getColumnIndex( DataBaseHelper.Coluna_DataCadastro )

        while( cursor.moveToNext() ){

            val idTarefa = cursor.getInt( indiceId )
            val descricao = cursor.getString( indiceDescricao )
            val data = cursor.getString( indiceData )

            listarTarefa.add(
                Tarefa(idTarefa, descricao, data)
            )
        }

        return listarTarefa
    }
}