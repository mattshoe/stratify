package io.github.mattshoe.shoebox.stratify.logger

class StratifyException(
    cause: Throwable
): Throwable(cause) {
    override val message = "[stratify] ${cause.message}"
}