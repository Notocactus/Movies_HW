package entities
import kotlinx.serialization.Serializable

@Serializable
class Movie(val id: String, var name: String, var duration: UInt) {
    override fun toString(): String {
        return "${name}. Продолжительность показа: ${duration / 60u}часа(ов) и ${duration - duration / 60u * 60u} минут.\n"
    }
}