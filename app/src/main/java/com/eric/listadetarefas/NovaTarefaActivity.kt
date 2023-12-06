package com.eric.listadetarefas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eric.listadetarefas.banco.TarefaDAO
import com.eric.listadetarefas.databinding.ActivityMainBinding
import com.eric.listadetarefas.databinding.ActivityNovaTarefaBinding
import com.eric.listadetarefas.model.Tarefa

class NovaTarefaActivity : AppCompatActivity() {

    // Binding para a ActivityNovaTarefa
    private val binding by lazy {
        ActivityNovaTarefaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Tarefa a ser editada (se houver)
        var tarefa: Tarefa? = null
        val bundle = intent.extras
        if (bundle != null) {
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText(tarefa.descricao)
        }

        // Configuração do botão de salvar
        binding.btnSalvar.setOnClickListener {
            val descricao = binding.editTarefa.text.toString()

            if (binding.editTarefa.text.isNotEmpty()) {
                if (tarefa != null) {
                    // Se há uma tarefa existente, realiza a edição
                    editar(tarefa, descricao)
                } else {
                    // Se não, salva uma nova tarefa
                    salvar(descricao)
                }
            } else {
                Toast.makeText(this, "Preencha uma tarefa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função para editar uma tarefa existente
    private fun editar(tarefa: Tarefa, novaDescricao: String) {
        val tarefaAtualizar = Tarefa(tarefa.idTarefa, novaDescricao, "default")
        val tarefaDAO = TarefaDAO(this)
        if (tarefaDAO.atualizar(tarefaAtualizar)) {
            Toast.makeText(
                this,
                "A tarefa foi atualizada com sucesso",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        } else {
            Toast.makeText(
                this,
                "Erro ao atualizar tarefa",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Função para salvar uma nova tarefa
    private fun salvar(descricao: String) {
        val tarefa = Tarefa(-1, descricao, "oi")
        val tarefaDAO = TarefaDAO(this)
        if (tarefaDAO.salvar(tarefa)) {
            Toast.makeText(
                this,
                "A tarefa foi salva com sucesso",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        } else {
            Toast.makeText(
                this,
                "Erro ao salvar tarefa",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}