package dao

import entities.Staff
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JSONStaffSerializer : JSONSerializer<Staff> {
    override fun jsonSerialize(path: String, array: Array<Staff>) {
        if (!File(path).exists()) {
            val file = File(path)
            file.createNewFile()
        }
        File(path).writeText(Json.encodeToString<Array<Staff>>(array))
    }

    override fun jsonDeserialize(path: String): Array<Staff>?{
        if (File(path).exists()){
            val array = Json.decodeFromString<Array<Staff>>(File(path).readText(Charsets.UTF_8))
            return array
        }
        return null
    }
}