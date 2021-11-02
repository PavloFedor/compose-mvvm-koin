package com.pavlo.fedor.compose.flow.base

import org.koin.core.parameter.ParametersHolder

class ArgsProvider(parametersHolder: ParametersHolder) : ParametersHolder(parametersHolder.values.toMutableList())