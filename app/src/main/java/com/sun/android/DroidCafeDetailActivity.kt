package com.sun.android

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.sun.android.constants.FOOD_NAME_KEY
import com.sun.android.constants.RESOURCE_ID_KEY
import com.sun.android.databinding.ActivityDroidCafeDetailBinding
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class DroidCafeDetailActivity : AppCompatActivity() {
    private val binding: ActivityDroidCafeDetailBinding by lazy {
        ActivityDroidCafeDetailBinding.inflate(layoutInflater)
    }

    private val datePickerDialog: DatePickerDialog by lazy {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.format(calendar.time)
            binding.datePickerButton.text = date
        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, dateSetListener, year, month, day)
    }

    private val alertDialog: AlertDialog by lazy {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.order_title))
        builder.setMessage(getString(R.string.order_message, itemName))
        builder.setPositiveButton(getString(R.string.order_confirm)) { _, _ ->
            Toast.makeText(this, getString(R.string.order_successfully), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DroidCafeMainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(getString(R.string.order_cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create()
    }

    private val itemName: String by lazy {
        intent.getStringExtra(FOOD_NAME_KEY).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val itemImage = intent.getIntExtra(RESOURCE_ID_KEY, 0)

        binding.coffeeImage.setImageResource(itemImage)
        binding.tvChoosingItem.text = itemName

        binding.datePickerButton.setOnClickListener {
            datePickerDialog.show()
        }
        binding.datePickerButton.text = getTodayDate()
        binding.btnShoppingCart.setOnClickListener {
            if (validateInputFields()) {
                alertDialog.show()
            } else {
                Toast.makeText(
                    this@DroidCafeDetailActivity,
                    getString(R.string.validate_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getTodayDate(): String {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        month += 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        return DateFormatSymbols().shortMonths[month - 1]
    }

    fun openDatePicker(view: View) {
        datePickerDialog.show()
    }

    private fun validateInputFields(): Boolean {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()

        return !TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(email) &&
            !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(address)
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
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}
