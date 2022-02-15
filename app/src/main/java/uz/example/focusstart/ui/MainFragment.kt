package uz.example.focusstart.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.example.focusstart.R
import uz.example.focusstart.data.model.Currency
import uz.example.focusstart.databinding.FragmentMainBinding
import uz.example.focusstart.utils.scope
import java.util.*
import kotlin.math.roundToInt

class MainFragment:Fragment(R.layout.fragment_main) {

    private val viewModel by viewModel<MainViewModel>()
    private val adapter by inject<MainAdapter>()
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val categoryList= mutableListOf<String>()
    private val currencyList= mutableListOf<Double>()
    private val nominalList= mutableListOf<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        ))
        viewModel.getCurrencies()
        var selectedCategory=""
        var selectedCurrency = 0.0
        var nominal=0
        val categoryAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner, categoryList)
        actCategory.setAdapter(categoryAdapter)
        actCategory.setOnFocusChangeListener { _, _ ->
            actCategory.showDropDown()
        }
        actCategory.setOnItemClickListener { adapterView, _, i, _ ->
            etRub.clearFocus()
            tilCategory.isErrorEnabled = false
            if (adapterView.getItemAtPosition(i) != getString(R.string.not_selected_ru)) {
                selectedCategory=categoryList[i]
                selectedCurrency = currencyList[i]
                nominal=nominalList[i]
            }
        }
        btnConvert.setOnClickListener{
            etRub.clearFocus()
            if(!etRub.text.isNullOrEmpty()&&selectedCurrency!=0.0){
                val x=etRub.text.toString().toDouble()
                val res=(x/selectedCurrency*nominal*100).roundToInt() / 100.0
                etConvert.setText(res.toString())
            }
        }
        val glideUrl = GlideUrl("https://flagcdn.com/h20/ru.png")
        Glide.with(requireContext())
            .load(glideUrl)
            .into(ivRussia)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.actionRefresh->{
                    progressBar.visibility = View.VISIBLE
                    viewModel.getCurrencies()
                    return@setOnMenuItemClickListener true
                }
                else->false
            }
        }
        setUpObserver()
    }

    private fun setUpObserver()=binding.scope{
        progressBar.visibility = View.VISIBLE
        viewModel.success.observe(viewLifecycleOwner,{
            progressBar.visibility = View.GONE
            val list= mutableListOf<Currency>()
            it.Valute.forEach { (_, u) ->
                list.add(u)
                categoryList.add(u.Name)
                currencyList.add(u.Value)
                nominalList.add(u.Nominal)
            }
            adapter.models=list
        })
        viewModel.error.observe(viewLifecycleOwner, {
            progressBar.visibility = View.GONE
        })
    }
}