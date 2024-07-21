package io.github.mattshoe.shoebox.stratify

import com.google.devtools.ksp.processing.Dependencies

data class GeneratedFile(
    val dependencies: Dependencies? = null,
    val fileName: String,
    val packageName: String,
    val output: String
)