package com.transact.collen.assignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.transact.collen.assignment.R
import com.transact.collen.assignment.entities.Flag
import com.transact.collen.assignment.ui.viewholder.FlagsListViewHolder

class FlagsListAdapter :
    RecyclerView.Adapter<FlagsListViewHolder>(), Filterable {

    val originalList: MutableList<Flag> = mutableListOf()
    var filteredList: MutableList<Flag> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagsListViewHolder {
        return FlagsListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.country_flags_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FlagsListViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun swapItems(flags: List<Flag?>?) {
        this.filteredList.clear()
        this.originalList.clear()
        flags?.let { this.originalList.addAll(it.filterNotNull()) }
        flags?.let { this.filteredList.addAll(originalList) }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList = (results.values as List<Flag>).toMutableList()
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredResults: List<Flag?>?
                if (constraint.length == 0) {
                    filteredResults = originalList
                } else {
                    filteredResults = originalList.filter { it.id == constraint.toString() }
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }
        }
    }
}