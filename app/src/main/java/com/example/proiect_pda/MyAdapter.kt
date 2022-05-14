package com.example.proiect_pda

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter (private val picture_list: ArrayList<Pictures>) : RecyclerView.Adapter <MyAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_in_recycleview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curr_picture = picture_list[position]
        val image_taken = BitmapFactory.decodeFile(curr_picture.image_path)
        holder.ico.setImageBitmap(image_taken)
        holder.ico_name.text = curr_picture.image_path
    }

    override fun getItemCount(): Int {
        return picture_list.size
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ico : ShapeableImageView = view.findViewById(R.id.ico)
        val ico_name: TextView = view.findViewById(R.id.ico_name)
    }

}