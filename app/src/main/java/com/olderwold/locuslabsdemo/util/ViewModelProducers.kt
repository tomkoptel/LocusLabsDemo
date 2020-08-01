package com.olderwold.locuslabsdemo.util

import androidx.lifecycle.ViewModel

fun viewModelProducers(config: ViewModelProducers.Builder.() -> Unit): ViewModelProducers {
    return ViewModelProducers.Builder().apply(config).build()
}

data class ViewModelProducers constructor(
    private val viewModels: Map<Class<*>, ViewModel> = emptyMap()
) {
    class Builder {
        val viewModels = mutableMapOf<Class<*>, ViewModel>()

        inline fun <reified T : ViewModel> register(viewModel: T) {
            viewModels[T::class.java] = viewModel
        }

        fun build(): ViewModelProducers {
            return ViewModelProducers(viewModels)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel> get(modelClass: Class<T>): T? {
        return viewModels[modelClass] as T
    }
}
