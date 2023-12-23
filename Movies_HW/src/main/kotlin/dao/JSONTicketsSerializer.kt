package dao

import entities.Ticket
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JSONTicketsSerializer : JSONSerializer<Ticket> {
    override fun jsonSerialize(path: String, array: Array<Ticket>) {
        if (!File(path).exists()) {
            val file = File(path)
            file.createNewFile()
        }
        File(path).writeText(Json.encodeToString<Array<Ticket>>(array))
    }

    override fun jsonDeserialize(path: String): Array<Ticket>?{
        if (File(path).exists()){
            val array = Json.decodeFromString<Array<Ticket>>(File(path).readText(Charsets.UTF_8))
            return array
        }
        return null
    }
}