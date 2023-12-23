package dao

import entities.Movie
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JSONMoviesSerializer : JSONSerializer<Movie> {
    override fun jsonSerialize(path: String, array: Array<Movie>) {
        if (!File(path).exists()) {
            val file = File(path)
            file.createNewFile()
        }
        File(path).writeText(Json.encodeToString<Array<Movie>>(array))
    }
    override fun jsonDeserialize(path: String): Array<Movie>?{
        if (File(path).exists()){
            val array = Json.decodeFromString<Array<Movie>>(File(path).readText(Charsets.UTF_8))
            return array
        }
        return null
    }
}