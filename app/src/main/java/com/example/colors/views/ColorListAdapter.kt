package com.example.colors.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.colors.R
import com.example.colors.databinding.ItemViewBinding
import com.example.colors.models.ColorModel
import kotlinx.android.synthetic.main.item_view.view.*

class ColorListAdapter(val colorList: ArrayList<ColorModel>) :
    RecyclerView.Adapter<ColorListAdapter.ColorViewHolder>(), ColorClickListener {

    fun updateColorList(newColorList: List<ColorModel>) {
        colorList.clear()
        colorList.addAll(newColorList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemViewBinding>(inflater, R.layout.item_view, parent, false)
        return ColorViewHolder(view)
    }


    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.view.color = colorList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    override fun onColorClick(v: View) {


    }

    class ColorViewHolder(var view: ItemViewBinding) : RecyclerView.ViewHolder(view.root)
}

