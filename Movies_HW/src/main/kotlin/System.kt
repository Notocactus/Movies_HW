import repositories.MoviesRepository
import repositories.SessionsRepository
import repositories.StaffRepository
import repositories.TicketsRepository
import java.time.LocalDateTime

class System {
    private var countOfCreatedObjects: Int = 0
    private val moviesRepository = MoviesRepository()
    private val sessionsRepository = SessionsRepository()
    private val ticketsRepository = TicketsRepository()
    private val staffRepository = StaffRepository()

    fun enter(login: String, password: String): Boolean {
        return staffRepository.enter(login, password)
    }

    fun register(login: String, password: String): Boolean {
        return staffRepository.register(login, password)
    }

    fun clearSessions(localDateTime: LocalDateTime) {
        sessionsRepository.clearSessions(moviesRepository.moviesArray, ticketsRepository, localDateTime)
    }

    private fun generateId(): String {
        return (LocalDateTime.now().year.toString() + LocalDateTime.now().monthValue.toString() +
                LocalDateTime.now().hour.toString() + LocalDateTime.now().minute.toString() +
                LocalDateTime.now().second.toString() +
                LocalDateTime.now().dayOfMonth.toString() + (countOfCreatedObjects++).toString())

    }

    fun addMovie(name: String, duration: UInt): String {
        return moviesRepository.addMovie(generateId(), name, duration)
    }

    fun editMovie(movieName: String, newName: String): String {
        return moviesRepository.editMovie(movieName, newName)
    }

    fun editMovie(movieName: String, duration: UInt): String {
        return moviesRepository.editMovie(sessionsRepository, ticketsRepository, movieName, duration)
    }

    fun removeMovie(name: String): String {
        return moviesRepository.removeMovie(sessionsRepository, ticketsRepository, name)
    }

    fun addSession(movieName: String, date: String, cost: UInt): String {
        return sessionsRepository.addSession(moviesRepository, generateId(), movieName, date, cost)
    }

    fun editSessionDate(date: String, newDate: String): String {
        return sessionsRepository.editSessionDate(moviesRepository, ticketsRepository, date, newDate)
    }

    fun editSessionCost(date: String, newCost: UInt): String {
        return sessionsRepository.editSessionCost(date, newCost)
    }

    fun removeSession(date: String): String {
        return sessionsRepository.removeSession(ticketsRepository, date)
    }

    fun sellTicket(date: String, place: UInt): String { // Надо исправить работу с файлом
        return ticketsRepository.sellTicket(sessionsRepository, generateId(), date, place)
    }

    fun returnTicket(ticketId: String): String {
        return ticketsRepository.returnTicket(sessionsRepository, ticketId)
    }

    fun tagVisitor(ticketId: String): String {
        return ticketsRepository.tagVisitor(moviesRepository.moviesArray, sessionsRepository, ticketId)
    }

    fun showPlaces(date: String): String {
        return sessionsRepository.showPlaces(moviesRepository.moviesArray, date)
    }

    fun showSessionsByName(movieName: String) : String {
        return sessionsRepository.showSessionsByName(moviesRepository, movieName)
    }

    fun showSessionsByDate(date: String) : String {
        return sessionsRepository.showSessionsByDate(moviesRepository.moviesArray, date)
    }


    fun showMovies(): String {
        return moviesRepository.showMovies()
    }
}