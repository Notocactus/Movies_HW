package repositories

import dao.JSONMoviesSerializer
import entities.Movie
import moviesFilePath
import java.util.*

class MoviesRepository {
    private val path = moviesFilePath
    var moviesArray: Array<Movie>? = JSONMoviesSerializer().jsonDeserialize(path)

    fun findMovie(name: String): Movie? {
        if (moviesArray != null) {
            for (movie in moviesArray!!) {
                if (movie.name.lowercase(Locale.getDefault()) == name.lowercase(Locale.getDefault()) || movie.id.lowercase(Locale.getDefault()) == name.lowercase(
                        Locale.getDefault())) {
                    return movie
                }
            }
        }
        return null
    }

    fun addMovie(id: String, name: String, duration: UInt): String {
        if (moviesArray != null) {
            for (movie in moviesArray!!) {
                if (movie.name.lowercase(Locale.getDefault()) == name.lowercase(Locale.getDefault())) {
                    return ("Такой фильм уже существует! Добавление отменено.")
                }
            }
            moviesArray = moviesArray!! + (Movie(id, name, duration))
        } else {
            moviesArray = arrayOf(Movie(id, name, duration))
        }
        JSONMoviesSerializer().jsonSerialize(path, moviesArray!!)
        return "Фильм успешно добавлен."
    }

    fun editMovie(movieName: String, newName: String): String {
        if (moviesArray != null) {
            for (movie in moviesArray!!) {
                if (movie.name.lowercase(Locale.getDefault()) == movieName.lowercase(Locale.getDefault())) {
                    movie.name = newName
                    JSONMoviesSerializer().jsonSerialize(path, moviesArray!!)
                    return "Название фильма успешно изменено."
                }
            }
        }
        return "Фильма с таким названием не существует!"
    }

    fun editMovie(
        sessionsRepository: SessionsRepository,
        ticketsRepository: TicketsRepository,
        movieName: String,
        duration: UInt
    ): String {
        if (moviesArray != null) {
            for (movie in moviesArray!!) {
                if (movie.name.lowercase(Locale.getDefault()) == movieName.lowercase(Locale.getDefault())) {
                    if (sessionsRepository.sessionsArray != null) {
                        for (session in sessionsRepository.sessionsArray!!) {
                            if (session.movieId == movie.id) {
                                sessionsRepository.removeSession(ticketsRepository, session.date)
                            }
                        }
                    }
                    movie.duration = duration
                    JSONMoviesSerializer().jsonSerialize(path, moviesArray!!)
                    return "Продолжительность фильма успешно изменена. Сеансы по данному фильму были отменены, а билеты возвращены"
                }
            }
        }
        return "Фильма с таким названием не существует!"
    }

    fun removeMovie(
        sessionsRepository: SessionsRepository,
        ticketsRepository: TicketsRepository,
        name: String
    ): String {
        if (moviesArray != null) {
            for (movie in moviesArray!!) {
                if (movie.name.lowercase(Locale.getDefault()) == name.lowercase(Locale.getDefault())) {
                    if (sessionsRepository.sessionsArray != null) {
                        for (session in sessionsRepository.sessionsArray!!) {
                            if (session.movieId == movie.id) {
                                sessionsRepository.removeSession(ticketsRepository, session.date)
                            }
                        }
                    }
                    val moviesList = moviesArray!!.toMutableList()
                    moviesList -= movie
                    moviesArray = moviesList.toTypedArray()
                    JSONMoviesSerializer().jsonSerialize(path, moviesArray!!)
                    return "Фильм успешно удалён из списка."
                }
            }
        }
        return "Фильма с таким названием не существует!"
    }

    fun showMovies(): String {
        if (moviesArray != null) {
            var moviesString = ""
            var count = 1
            for (movie in moviesArray!!) {
                moviesString += ("${count}) " + movie.toString())
                count += 1
            }
            return moviesString
        } else {
            return "Фильмов в прокате пока нет"
        }
    }
}