package com.example.ntor.presentation.main.progress.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ntor.core.entities.Run
import com.example.ntor.databinding.ItemRunBinding
import com.example.ntor.presentation.DataHelper
import java.util.*

fun main(){
    val dateSplit = Date(Date().time).toString().split(" ")
   println(dateSplit)
}


class RunAdapter(
    private val runs: MutableList<Run> = mutableListOf(),
    private val openDetail: (Long) -> Unit = {},
) : RecyclerView.Adapter<RunAdapter.RunListViewHolder>() {


    fun updateRuns(_runs: List<Run>) {
        runs.apply {
            clear()
            addAll(_runs)
            notifyDataSetChanged()
        }
    }

    class RunListViewHolder(
        private val binding: ItemRunBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            run: Run,
            openDetail: (id: Long) -> Unit,
        ) {
            binding.apply {
                root.setOnClickListener {
                    openDetail(run.date)
                }
                distanceTextView.text = DataHelper.formatNumber(run.distance)
                dateTextView.text = DataHelper.formatDate(run.date)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRunBinding.inflate(layoutInflater, parent, false)
        return RunListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RunListViewHolder, position: Int) {
        holder.bind(runs[position], openDetail)
    }

    override fun getItemCount(): Int = runs.size
}