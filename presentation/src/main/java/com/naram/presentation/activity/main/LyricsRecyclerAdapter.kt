package com.naram.presentation.activity.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naram.presentation.activity.main.model.Lyrics
import com.naram.presentation.databinding.ItemLyricsBinding

class LyricsRecyclerAdapter(
    private val vm: MainViewModel
) : RecyclerView.Adapter<LyricsRecyclerAdapter.ViewHolder>() {

    var items = ArrayList<Lyrics>()
    private lateinit var binding: ItemLyricsBinding

    inner class ViewHolder(
        private val binding: ItemLyricsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Lyrics) {
            binding.lyrics = item
            binding.vm = vm
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemLyricsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}