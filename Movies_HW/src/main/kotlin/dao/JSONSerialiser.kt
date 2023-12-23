package dao

interface JSONSerializer<T> {
    fun jsonSerialize(path: String, array: Array<T>) {}

    fun jsonDeserialize(path:String) : Array<T>? {
        return null
    }
}