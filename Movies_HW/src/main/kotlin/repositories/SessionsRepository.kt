package repositories

import dao.JSONSessionsSerializer
import entities.Movie
import entities.Session
import sessionsFilePath
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SessionsRepository {
    private val path =  sessionsFilePath
    var sessionsArray: Array<Session>? = JSONSessionsSerializer().jsonDeserialize(path)

    fun clearSessions(moviesArray: Array<Movie>?, ticketsRepository: TicketsRepository, localDateTime: LocalDateTime) {
        val sessionsList: MutableList<Session>
        if (sessionsArray != null){
            var isChanged = false
            sessionsList = sessionsArray!!.toMutableList()
            for (session in sessionsArray!!){
                if (moviesArray != null) {
                    for (movie in moviesArray){
                        if (movie.id == session.movieId){
                            val endOfTheMovie = LocalDateTime.parse(session.date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).plusHours(
                                (movie.duration / 60u).toLong()).plusMinutes((movie.duration - movie.duration / 60u).toLong())
                            if (localDateTime > endOfTheMovie){
                                ticketsRepository.removeTickets(session.id)
                                sessionsList -= session
                                isChanged = true
                            }
                            break
                        }
                    }
                }
            }
            if (isChanged){
                val sessionsArr = sessionsList.toTypedArray()
                JSONSessionsSerializer().jsonSerialize(path, sessionsArr)
            }
        }
    }

    fun addSession(moviesRepository: MoviesRepository,id:String, movieName: String, date: String, cost: UInt) : String {
        val currMovieId = moviesRepository.findMovie(movieName)
        if (currMovieId == "") {
            return ("В каталоге нет такого фильма.")
        }
        if (sessionsArray != null) {
            for (session in sessionsArray!!){
                for (movie in moviesRepository.moviesArray!!){
                    if (movie.id == session.movieId){
                        val dateToLocalDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                        val startOfTheMovie = LocalDateTime.parse(session.date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                        val endOfTheMovie = startOfTheMovie.plusHours(
                            (movie.duration / 60u).toLong()).plusMinutes((movie.duration - movie.duration / 60u * 60u).toLong())
                        if (dateToLocalDateTime >= startOfTheMovie &&
                            dateToLocalDateTime <= endOfTheMovie.plusMinutes(15L)){
                            return "Это время уже занято."
                        }
                    }
                }
            }
            sessionsArray = sessionsArray!! + Session(id, currMovieId, date, cost)
        }
        else {
            sessionsArray = arrayOf(Session(id, currMovieId, date, cost))
        }
        JSONSessionsSerializer().jsonSerialize(path, sessionsArray!!)
        return "Сеанс успешно добавлен."
    }


    fun editSessionDate(ticketsRepository: TicketsRepository, date: String, newDate: String) : String {
        if (sessionsArray != null) {
            for (session in sessionsArray!!){
                if (session.date.lowercase(Locale.getDefault()) == date.lowercase(Locale.getDefault())){
                    session.date = newDate
                    ticketsRepository.editDate(session.id, newDate)
                    JSONSessionsSerializer().jsonSerialize(path ,sessionsArray!!)
                    return "Дата и время сеанса успешно изменены."
                }
            }
        }
        return "Сеанса с такой датой не существует!"
    }

    fun editSessionCost(date: String, newCost: UInt) : String {
        if (sessionsArray != null) {
            for (session in sessionsArray!!){
                if (session.date.lowercase(Locale.getDefault()) == date.lowercase(Locale.getDefault())){
                    session.cost = newCost
                    JSONSessionsSerializer().jsonSerialize(path ,sessionsArray!!)
                    return "Цена сеанса успешно изменена."
                }
            }
        }
        return "Сеанса с такой датой не существует!"
    }

    fun removeSession(ticketsRepository: TicketsRepository, date: String) : String {
        if (sessionsArray != null) {
            val sessionsList = sessionsArray!!.toMutableList()
            for (session in sessionsArray!!){
                if (session.date.lowercase(Locale.getDefault()) == date.lowercase(Locale.getDefault())){
                    if (LocalDateTime.parse( session.date,DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) <= LocalDateTime.now()){
                        return "Сеанс уже идёт"
                    }
                    if (ticketsRepository.ticketsArray != null){
                        for (ticket in ticketsRepository.ticketsArray!!){
                            if (ticket.sessionId == session.id){
                                ticketsRepository.returnTicket(ticket.id)
                            }
                        }
                    }
                    sessionsList -= session
                    sessionsArray = sessionsList.toTypedArray()
                    JSONSessionsSerializer().jsonSerialize(path ,sessionsArray!!)
                    return "Сеанс успешно удалён из списка."
                }
            }
        }
        return "Сеанса с такой датой не существует!"
    }

    fun showPlaces(moviesArray: Array<Movie>?, date: String) : String {
        if (sessionsArray != null) {
            for (session in sessionsArray!!){
                if (session.date.lowercase(Locale.getDefault()) == date.lowercase(Locale.getDefault())){
                    return (session.toString() + "Места:\n" + session.getPlaces())
                }
            }
        }
        return "Сеанса с такой датой не существует!"
    }

    fun showSessionsByName(moviesRepository: MoviesRepository, movieName: String) : String {
        val id = moviesRepository.findMovie(movieName)
        if (sessionsArray != null) {
            var result = ""
            for (session in sessionsArray!!){
                if (session.movieId.lowercase(Locale.getDefault()) == id.lowercase(Locale.getDefault())){
                    result += session.toString()
                }
            }
            if (result != "") {
                return result
            }
            return "Пока что нет сеансов по фильму: $movieName"
        }
        return "Пока что нет запланированных сессий!"
    }

    fun showSessionsByDate(date: String) : String {
        if (sessionsArray != null) {
            var result = ""
            for (session in sessionsArray!!){
                if (session.date.lowercase(Locale.getDefault()) == date.lowercase(Locale.getDefault())){
                    result += session.toString()
                }
            }
            if (result != "") {
                return result
            }
            return "Пока что нет сеансов в такую дату: $date"
        }
        return "Пока что нет запланированных сеансов!"
    }

    fun tagVisitor(sessionId: String, place: UInt){
        if (sessionsArray != null){
            for (session in sessionsArray!!){
                if (session.id == sessionId){
                    ++session.places[place.toInt() - 1]
                    JSONSessionsSerializer().jsonSerialize(path ,sessionsArray!!)
                }
            }
        }
    }
}