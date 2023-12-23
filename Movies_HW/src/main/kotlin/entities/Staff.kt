package entities

import kotlinx.serialization.Serializable

@Serializable
class Staff(val login: String, val password: Int) {
}