package dev.dmanluc.openbankmobiletest.presentation.characters

import androidx.lifecycle.*
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTrackerImpl
import dev.dmanluc.openbankmobiletest.domain.usecase.GetCharactersUseCase
import dev.dmanluc.openbankmobiletest.presentation.base.BaseViewModel
import dev.dmanluc.openbankmobiletest.utils.AppDispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharactersFragmentViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val appDispatchers: AppDispatchers
) : BaseViewModel() {

    private val mutableCharacterListLiveData = MutableLiveData<List<Character>>()
    val characterListLiveData: LiveData<List<Character>> get() = mutableCharacterListLiveData

    var pagingLoadTracker: PagingLoadTracker = PagingLoadTrackerImpl()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch(appDispatchers.io) {
            getCharactersUseCase(pagingLoadTracker).collect { value ->
                value.fold(ifLeft = {}) { characterList ->
                    mutableCharacterListLiveData.postValue(characterList)
                }
            }
        }
    }



}