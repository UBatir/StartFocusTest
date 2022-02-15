package uz.example.focusstart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.example.focusstart.data.domain.MainRepository
import uz.example.focusstart.data.model.Currency
import uz.example.focusstart.data.model.Data
import uz.example.focusstart.data.model.Valute

class MainViewModel(private val mainRepository: MainRepository):ViewModel() {

    private var mutableSuccess:MutableLiveData<Data> = MutableLiveData()
    val success:LiveData<Data> = mutableSuccess
    private var mError:MutableLiveData<String> = MutableLiveData()
    val error:LiveData<String> = mError

    fun getCurrencies(){
        mainRepository.getCurrency().onEach {
            it.onSuccess { res->
                mutableSuccess.value=res
            }
            it.onFailure { th->
                mError.value=th.message
            }
        }.catch() {
            mError.value = "catch error in viewModel"
        }.launchIn(viewModelScope)
    }

}