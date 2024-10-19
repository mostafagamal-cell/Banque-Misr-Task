package banquemisr.challenge05.mostafa.di

sealed class UiStates<out T> {
    data object Loading : UiStates<Nothing>()
    data class Success<out T>(val data: T) : UiStates<T>()
    data class Error(val message: String) : UiStates<Nothing>()
}