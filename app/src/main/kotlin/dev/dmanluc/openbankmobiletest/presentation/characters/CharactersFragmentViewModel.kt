package dev.dmanluc.openbankmobiletest.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTrackerImpl
import dev.dmanluc.openbankmobiletest.domain.usecase.GetCharactersUseCase
import dev.dmanluc.openbankmobiletest.presentation.base.BaseViewModel
import dev.dmanluc.openbankmobiletest.utils.AppDispatchers
import dev.dmanluc.openbankmobiletest.utils.Event
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
                value.fold(ifLeft = ::handleError) { characterList ->
                    mutableCharacterListLiveData.postValue(characterList)
                }
            }
        }
    }

    private fun handleError(error: ApiError) {
        when (error) {
            is ApiError.HttpError -> _snackbarErrorWithStringLiteral.postValue(Event(error.body))
            is ApiError.NetworkError -> _snackbarErrorWithStringLiteral.postValue(Event(error.throwable.localizedMessage.orEmpty()))
            is ApiError.UnknownApiError -> _snackbarErrorWithStringLiteral.postValue(Event(error.throwable.localizedMessage.orEmpty()))
        }
    }

    fun goToCharacterDetail(selectedCharacter: Character) {
        navigate(CharactersFragmentDirections.actionToCharacterDetail(selectedCharacter))
    }

}