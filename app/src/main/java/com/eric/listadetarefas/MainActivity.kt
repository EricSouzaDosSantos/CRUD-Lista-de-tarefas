package com.eric.listadetarefas

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.eric.listadetarefas.adapter.TarefaAdapter
import com.eric.listadetarefas.banco.TarefaDAO
import com.eric.listadetarefas.databinding.ActivityMainBinding
import com.eric.listadetarefas.model.Tarefa

// MainActivity.kt: Atividade principal que exibe a lista de tarefas.

class MainActivity : AppCompatActivity() {

    // Binding para a ActivityMain
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Lista de tarefas a ser exibida
    private var listarTarefa = emptyList<Tarefa>()

    // Adaptador para a RecyclerView
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Configuração do botão de adicionar tarefa
        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this, NovaTarefaActivity::class.java)
            startActivity(intent)
        }

        // Inicialização do adaptador e configuração da RecyclerView
        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id) },
            { tarefa -> editar(tarefa) }
        )
        binding.rvTarefas.adapter = tarefaAdapter
        binding.rvTarefas.layoutManager = LinearLayoutManager(this)
    }

    // Função para editar uma tarefa existente
    private fun editar(tarefa: Tarefa) {
        val intent = Intent(this, NovaTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)
    }

    // Função para confirmar a exclusão de uma tarefa
    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmação da exclusão da tarefa")
        alertBuilder.setMessage("Você deseja realmente excluir essa tarefa?")

        alertBuilder.setPositiveButton("Sim") { _, _ ->
            val tarefaDAO = TarefaDAO(this)

            // Excluir a tarefa do banco de dados
            if (tarefaDAO.excluir(id)) {
                atualizarLista()
                Toast.makeText(
                    this,
                    "Sucesso ao remover tarefa", Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Erro ao remover tarefa", Toast.LENGTH_SHORT
                ).show()
            }
        }
        alertBuilder.setNegativeButton("Não") { _, _ -> }

        alertBuilder.create().show()
    }

    // Função para atualizar a lista de tarefas na RecyclerView
    private fun atualizarLista() {
        val tarefaDAO = TarefaDAO(this)

        // Obter a lista atualizada de tarefas do banco de dados
        listarTarefa = tarefaDAO.ListarTarefas()

        // Atualizar o adaptador da RecyclerView
        tarefaAdapter?.adicionarLista(listarTarefa)
    }

    override fun onStart() {
        super.onStart()
        atualizarLista()
    }
}
