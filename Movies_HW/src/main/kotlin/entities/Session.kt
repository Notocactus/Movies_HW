package entities
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Session(val id: String, var movieId: String, var date: String, var cost: UInt) {
    var places: Array<Byte> = Array(100) { 0 }

    fun getPlaces() : String {
        var res = ""
        for (i in 0..99) {
            res += if (places[i].toInt() == 0) {
                (i+1).toString() + ": свободно, "
            } else{
                (i+1).toString() + ": занято, "
            }
            if ((i + 1).rem(10) == 0){
                res += "\n"
            }
        }
        return res
    }

    fun toString(moviesArray: Array<Movie>?): String {
        var movieName = ""
        if (moviesArray != null){
            for (movie in moviesArray){
                if (movie.id.lowercase(Locale.getDefault()) == movieId.lowercase(Locale.getDefault())){
                    movieName = movie.name
                }
            }
        }
        return ("Сеанс фильма '$movieName' в дату: $date стоит $cost. \n")
    }
}