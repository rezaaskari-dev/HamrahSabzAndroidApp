package com.android.argan


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.util.Locale

class CalculatorFragment : Fragment() {

    private lateinit var editTextPower: EditText
    private lateinit var editTextHours: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var textViewResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        // Initialize UI elements
        editTextPower = view.findViewById(R.id.editTextPower)
        editTextHours = view.findViewById(R.id.editTextHours)
        buttonCalculate = view.findViewById(R.id.buttonCalculate)
        textViewResult = view.findViewById(R.id.textViewResult)

        // Set click listener for the calculate button
        buttonCalculate.setOnClickListener {
            calculateEnergyConsumption()
        }

        return view
    }

    private fun calculateEnergyConsumption() {
        val powerStr = editTextPower.text.toString()
        val hoursStr = editTextHours.text.toString()

        // Input validation
        if (powerStr.isBlank() || hoursStr.isBlank()) {
            Toast.makeText(requireContext(), "لطفاً هر دو فیلد را پر کنید", Toast.LENGTH_SHORT)
                .show()
            return
        }

        try {
            val powerWatts = powerStr.toDouble()
            val hours = hoursStr.toDouble()

            if (powerWatts <= 0 || hours <= 0) {
                Toast.makeText(
                    requireContext(),
                    "توان و ساعت باید بزرگتر از صفر باشند",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            // Calculate energy consumption in kWh
            val energyKwh = (powerWatts / 1000.0) * hours

            // Format the result to two decimal places and use Persian number format
            val formatter = NumberFormat.getNumberInstance(Locale("fa", "IR"))
            formatter.maximumFractionDigits = 2
            formatter.minimumFractionDigits = 2

            val formattedKwh = formatter.format(energyKwh)

            // Display the result
            textViewResult.text = "مقدار مصرف: ${formattedKwh} کیلووات‌ساعت"

        } catch (e: NumberFormatException) {
            Toast.makeText(
                requireContext(),
                "ورودی نامعتبر است. لطفاً اعداد صحیح وارد کنید.",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }
}
