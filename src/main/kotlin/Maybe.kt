sealed class Maybe<T> {
    operator fun <R> times(other: (T) -> Maybe<R>): Maybe<R> = when(this) {
        is Just -> other(this.t)
        is None -> None()
    }
    infix fun orElse(other: () -> Maybe<T>): Maybe<T> = when(this) {
        is Just -> this
        is None -> other()
    }
    infix fun orElse(other: Maybe<T>): Maybe<T> = when(this){ 
        is Just -> this
        is None -> other
    }
}

fun <T> Maybe<Maybe<T>>.flatten(): Maybe<T> = when(this) {
    is Just -> this.t
    is None -> None()
}

fun <T> Maybe<T>.fromJust(): T = (this as Just<T>).t

fun <T> maybeIf(conditional: Boolean, value: T): Maybe<T> = if(conditional) Just(value) else None()

fun <T> maybe(conditional: Boolean = true, conditionalBlock: () -> Boolean = { true }, block: () -> T): Maybe<T> {
    return if(!conditional || !conditionalBlock()) {
        None()
    } else Just(block())
}

class Just<T>(val t: T): Maybe<T>() {
    override fun toString() = "Just($t)"
}
class None<T> : Maybe<T>() {
    override fun toString() = "None()"
}
