package com.sun.android

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.android.utils.constants.FOOD_NAME_KEY
import com.sun.android.utils.constants.RESOURCE_ID_KEY
import com.sun.android.databinding.ItemFoodBinding

class FoodAdapter(val context: Context) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private lateinit var mListFoods: List<Food>

    inner class FoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listFoods: List<Food>) {
        mListFoods = listFoods
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mListFoods.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = mListFoods[position]
        holder.binding.imgFood.setImageResource(food.resourceId)
        holder.binding.tvName.text = food.nameFood
        holder.binding.tvPrice.text = food.price.toString() + " VND"

        holder.binding.imgFood.setOnClickListener {
            val itemName = food.nameFood
            val intent = Intent(context, DroidCafeDetailActivity::class.java)
            intent.putExtra(RESOURCE_ID_KEY, food.resourceId)
            intent.putExtra(FOOD_NAME_KEY, itemName)
            context.startActivity(intent)
        }
    }
}
