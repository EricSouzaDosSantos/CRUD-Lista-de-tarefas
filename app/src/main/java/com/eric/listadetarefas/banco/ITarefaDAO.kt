package com.eric.listadetarefas.banco

import com.eric.listadetarefas.model.Tarefa

interface ITarefaDAO {

    fun salvar(tarefa: Tarefa): Boolean
    fun excluir(tarefa: Int): Boolean
    fun atualizar(tarefa: Tarefa): Boolean
    fun ListarTarefas(): List<Tarefa>

}