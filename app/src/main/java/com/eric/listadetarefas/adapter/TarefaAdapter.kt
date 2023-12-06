package com.eric.listadetarefas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eric.listadetarefas.databinding.ItemTarefaBinding
import com.eric.listadetarefas.model.Tarefa

// Adapter para a RecyclerView que exibe uma lista de tarefas.
class TarefaAdapter(
    // Callback para lidar com cliques no botão de exclusão.
    val onClickExcluir: (Int) -> Unit,
    // Callback para lidar com cliques no botão de edição.
    val onClickEditar: (Tarefa) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    // Lista de tarefas a ser exibida.
    private var listaTarefas: List<Tarefa> = emptyList()

    // Atualiza a lista de tarefas e notifica a RecyclerView.
    fun adicionarLista(lista: List<Tarefa>) {
        this.listaTarefas = lista
        notifyDataSetChanged()
    }

    // ViewHolder para exibir cada item de tarefa na RecyclerView.
    inner class TarefaViewHolder(itemBinding: ItemTarefaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

        private val binding: ItemTarefaBinding

        init {
            binding = itemBinding
        }

        // Associa os dados da tarefa atual aos elementos da interface do usuário.
        fun bind(tarefa: Tarefa) {
            binding.textDescricao.text = tarefa.descricao
            binding.textData.text = tarefa.dataCadastro

            // Configuração do botão de exclusão
            binding.btnExcluir.setOnClickListener {
                onClickExcluir(tarefa.idTarefa)
            }

            // Configuração do botão de edição
            binding.btnEditar.setOnClickListener {
                onClickEditar(tarefa)
            }
        }
    }

    // Cria novas instâncias de TarefaViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemTarefaBinding = ItemTarefaBinding.inflate(
            layoutInflater, parent, false
        )
        return TarefaViewHolder(itemTarefaBinding)
    }

    // Associa os dados da tarefa à ViewHolder.
    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]
        holder.bind(tarefa)
    }

    // Retorna o número total de itens na lista de tarefas.
    override fun getItemCount(): Int {
        return listaTarefas.size
    }
}
