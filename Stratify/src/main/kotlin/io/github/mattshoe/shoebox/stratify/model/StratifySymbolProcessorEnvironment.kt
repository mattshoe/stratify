package io.github.mattshoe.shoebox.stratify.model

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

data class StratifySymbolProcessorEnvironment(
    internal val kspEnvironment: SymbolProcessorEnvironment
)