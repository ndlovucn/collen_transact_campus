package com.transact.collen.assignment.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.transact.collen.assignment.databinding.CountryFlagsListItemBinding
import com.transact.collen.assignment.entities.Flag

class FlagsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: CountryFlagsListItemBinding = CountryFlagsListItemBinding.bind(view)

    fun bind(flag: Flag) {
        with(binding) {
            this.idCountryCode.text = flag.id
            this.idCountryFlag.setImageBitmap(flag.flagBitmap)
        }
    }
}