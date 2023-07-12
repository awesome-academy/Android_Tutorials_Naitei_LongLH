package com.sun.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.android.databinding.ActivityDroidCafeMainBinding

class DroidCafeMainActivity : AppCompatActivity() {
    private val binding: ActivityDroidCafeMainBinding by lazy {
        ActivityDroidCafeMainBinding.inflate(layoutInflater)
    }

    private val foodAdapter: FoodAdapter by lazy {
        FoodAdapter(this).apply {
            setData(getListFoods())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rcvFood.adapter = foodAdapter
    }

    private fun getListFoods(): MutableList<Food> {
        val list: MutableList<Food> = mutableListOf()
        list.add(Food(R.drawable.ca_phe_sua_phin, getString(R.string.item_ca_phe_sua_phin), 20000))
        list.add(Food(R.drawable.bac_xiu, getString(R.string.item_bac_xiu), 28000))
        list.add(Food(R.drawable.cacao_da, getString(R.string.item_cacao_da), 28000))
        list.add(Food(R.drawable.capuchino, getString(R.string.item_capuchino), 32000))
        list.add(Food(R.drawable.tra_sua_tran_chau_duong_den, getString(R.string.item_tra_sua_tran_chau_duong_den), 22000))
        list.add(Food(R.drawable.chanh_day, getString(R.string.item_chanh_day), 30000))
        list.add(Food(R.drawable.banh_my_trung, getString(R.string.item_banh_my_trung), 15000))
        list.add(Food(R.drawable.banh_my_cha_ca, getString(R.string.item_banh_my_cha_ca), 15000))
        list.add(Food(R.drawable.banh_trang_tron, getString(R.string.item_banh_trang_tron), 15000))
        list.add(Food(R.drawable.xuc_xich_chien, getString(R.string.item_xuc_xich_chien), 15000))
        return list
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        menu?.let { safeMenu ->
            val nightModeItem = safeMenu.findItem(R.id.night_mode)
            nightModeItem?.setTitle(
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    R.string.day_mode
                } else {
                    R.string.night_mode
                }
            )
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.night_mode) {
            val nightMode = AppCompatDelegate.getDefaultNightMode()
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            // Recreate the activity for the theme change to take effect.
            recreate()
        }
        return true
    }
}
