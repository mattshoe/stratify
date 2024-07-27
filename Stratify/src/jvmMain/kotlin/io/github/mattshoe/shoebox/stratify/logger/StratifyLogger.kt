package io.github.mattshoe.shoebox.stratify.logger

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode

class StratifyLogger(
    private val environmentLogger: KSPLogger
): KSPLogger {
    override fun error(message: String, symbol: KSNode?) {
        environmentLogger.error(
            stratifyMessage(message),
            symbol
        )
    }

    override fun exception(e: Throwable) {
        environmentLogger.exception(
            StratifyException(e)
        )
    }

    override fun info(message: String, symbol: KSNode?) {
        environmentLogger.info(
            stratifyMessage(message),
            symbol
        )
    }

    override fun logging(message: String, symbol: KSNode?) {
        environmentLogger.logging(
            stratifyMessage(message),
            symbol
        )
    }

    override fun warn(message: String, symbol: KSNode?) {
        environmentLogger.warn(
            stratifyMessage(message),
            symbol
        )
    }

    private fun stratifyMessage(text: String): String {
        return "[stratify] $text"
    }
}

