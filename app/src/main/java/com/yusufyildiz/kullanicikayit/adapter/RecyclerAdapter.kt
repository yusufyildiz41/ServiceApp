package com.yusufyildiz.kullanicikayit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufyildiz.kullanicikayit.databinding.ActivityManagerBinding
import com.yusufyildiz.kullanicikayit.databinding.RecyclerRowBinding
import com.yusufyildiz.kullanicikayit.model.User

class RecyclerAdapter(val usersList: ArrayList<User>) : RecyclerView.Adapter<RecyclerAdapter.InfoHolder>() {

    class InfoHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InfoHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoHolder, position: Int) {
        holder.binding.recyclerNameText.text = usersList.get(position).name
        holder.binding.recyclerSurNameText.text = usersList.get(position).surname
        holder.binding.recyclerPhoneText.text = usersList.get(position).phone
        holder.binding.recyclerStationText.text = usersList.get(position).stationAddress
        holder.binding.recyclerStationDetailsText.text = usersList.get(position).detailsAddress
        holder.binding.recyclerWorkAddressText.text = usersList.get(position).workAddress
        holder.binding.recyclerHomeAddressText.text = usersList.get(position).homeAddress
        holder.binding.recyclerWorkStartTimeText.text = usersList.get(position).startWorkTime
        holder.binding.recyclerWorkFinishTimeText.text = usersList.get(position).finishWorkTime
    }

    override fun getItemCount(): Int {
        return usersList.size
    }


}