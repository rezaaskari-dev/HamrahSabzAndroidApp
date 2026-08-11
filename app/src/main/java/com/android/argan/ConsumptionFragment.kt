package com.android.argan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConsumptionFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ConsumptionAdapter

    private lateinit var scrollView: NestedScrollView
    private lateinit var btnShow: FloatingActionButton
    private lateinit var btnSubmit: Button
    private lateinit var btnCancel: Button

    private lateinit var spinner: Spinner
    private lateinit var etAmount: EditText
    private lateinit var etDate: EditText

    private var items = mutableListOf<ConsumptionItem>()

    companion object{
        const val PREFS_NAME="consumption_prefs"
        const val KEY="consumptions"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_consumption,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        recyclerView=view.findViewById(R.id.recyclerView)

        scrollView=view.findViewById(R.id.scrollView_one)

        btnShow=view.findViewById(R.id.button)

        btnSubmit=view.findViewById(R.id.btnSubmit)

        btnCancel=view.findViewById(R.id.btnCancel)

        spinner=view.findViewById(R.id.spinnerService)

        etAmount=view.findViewById(R.id.etAmount)

        etDate=view.findViewById(R.id.etDate)
        val services = listOf(
            "آب",
            "برق",
            "گاز",
            "اینترنت"
        )

        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            services
        )

        loadData()

        adapter=ConsumptionAdapter(items){

            deleteItem(it)

        }

        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        recyclerView.adapter=adapter

        btnShow.setOnClickListener {

            scrollView.visibility=View.VISIBLE

            btnShow.visibility=View.GONE
            recyclerView.visibility=View.GONE

        }

        btnCancel.setOnClickListener {

            scrollView.visibility=View.GONE

            btnShow.visibility=View.VISIBLE
            recyclerView.visibility=View.VISIBLE

        }

        btnSubmit.setOnClickListener {

            addConsumption()

        }

    }

    private fun addConsumption(){

        val type=spinner.selectedItem.toString()

        val amountText=etAmount.text.toString().trim()

        val date=etDate.text.toString().trim()

        if(amountText.isEmpty()||date.isEmpty()){

            Toast.makeText(requireContext(),"همه فیلدها را پر کنید",Toast.LENGTH_SHORT).show()

            return

        }

        val amount=amountText.toDoubleOrNull()

        if(amount==null){

            Toast.makeText(requireContext(),"مبلغ صحیح نیست",Toast.LENGTH_SHORT).show()

            return

        }

        val item=ConsumptionItem(type,amount,date)

        items.add(0,item)

        adapter.notifyItemInserted(0)

        recyclerView.scrollToPosition(0)

        saveData()

        etAmount.text.clear()

        etDate.text.clear()

        spinner.setSelection(0)

        scrollView.visibility=View.GONE

        btnShow.visibility=View.VISIBLE
        recyclerView.visibility=View.VISIBLE

    }

    private fun deleteItem(position:Int){

        if(position !in items.indices)return

        items.removeAt(position)

        adapter.notifyItemRemoved(position)

        saveData()

    }

    private fun saveData(){

        val prefs=requireContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

        val json=Gson().toJson(items)

        prefs.edit().putString(KEY,json).apply()

    }

    private fun loadData(){

        val prefs=requireContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

        val json=prefs.getString(KEY,null)

        items=if(json!=null){

            val type=object:TypeToken<MutableList<ConsumptionItem>>(){}.type

            Gson().fromJson(json,type)

        }else{

            mutableListOf()

        }

    }

}