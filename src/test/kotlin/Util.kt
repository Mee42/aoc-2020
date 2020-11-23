


infix fun <T> T.eq(other: T) {
    if(this != other) error("assertion invalid. Got $this expecting $other")
}