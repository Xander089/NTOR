package com.example.ntor.presentation.main.progress.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ntor.core.entities.Run
import com.example.ntor.databinding.ItemRunBinding


class RunAdapter(
    private val _runs: MutableList<Run>,
    private val openDetail: (Long) -> Unit = {},
    private val showBottomModal: (Long) -> Unit = {},
) : RecyclerView.Adapter<RunAdapter.PlaylistViewHolder>() {


    fun updateRuns(runs: List<Run>) {
        _runs.apply {
            clear()
            addAll(runs)
            notifyDataSetChanged()
        }
    }

    class PlaylistViewHolder(
        private val binding: ItemRunBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            run: Run,
            openDetail: (id: Long) -> Unit,
            showBottomModal: (id: Long) -> Unit,
        ) {
            binding.root.setOnClickListener {
                openDetail(run.date)
            }

            binding.root.setOnLongClickListener {
                showBottomModal(run.date)
                true
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRunBinding.inflate(layoutInflater, parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = _runs[position]
        holder.bind(playlist, openDetail, showBottomModal)
    }

    override fun getItemCount(): Int = _runs.size
}