package entities

import kotlinx.serialization.Serializable

@Serializable
class Ticket(val id: String, var sessionId: String, var cost: UInt, var place: UInt, var date: String) {
}