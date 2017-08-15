package com.kristal.library.appbase.recyclerview.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Created by Kristal on 6/8/2017.
 */
class RecyclerViewHolder<out BINDING : ViewDataBinding>(val binding: BINDING) : RecyclerView.ViewHolder(binding.root)