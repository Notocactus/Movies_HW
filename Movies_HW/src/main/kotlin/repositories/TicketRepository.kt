package repositories

import dao.JSONSessionsSerializer
import dao.JSONTicketsSerializer
import entities.Movie
import entities.Ticket
import sessionsFilePath
import ticketsFilePath
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TicketsRepository {
    private val path = ticketsFilePath
    var ticketsArray = JSONTicketsSerializer().jsonDeserialize(path)

    fun sellTicket(sessionsRepository: SessionsRepository, id: String, date: String, place: UInt): String {
        if (sessionsRepository.sessionsArray != null) {
            for (session in sessionsRepository.sessionsArray!!) {
                if (session.date == date) {
                    if (LocalDateTime.parse(
                            session.date,
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                        ) <= LocalDateTime.now()
                    ) {
                        return "Сеанс уже идёт"
                    }
                    if (session.places[place.toInt() - 1] == 0.toByte()) {
                        ++(session.places[place.toInt() - 1])
                        ticketsArray = if (ticketsArray != null) {
                            ticketsArray!! + (Ticket(id, session.id, session.cost, place, date))
                        } else {
                            arrayOf(Ticket(id, session.id, session.cost, place, date))
                        }
                        JSONTicketsSerializer().jsonSerialize(path, ticketsArray!!)
                        JSONSessionsSerializer().jsonSerialize(sessionsFilePath, sessionsRepository.sessionsArray!!)
                        return ("Билет продан")
                    } else {
                        return "Место занято, выберите новое"
                    }
                }
            }
        }
        return ("Такого сеанса не существует. Воспользуйтесь функцией ещё раз с существующим сеансом")
    }

    fun returnTicket(sessionsRepository: SessionsRepository, ticketId: String): String {
        if (ticketsArray != null) {
            val tickets = ticketsArray!!.toMutableList()
            for (ticket in tickets) {
                if (ticket.id == ticketId) {
                    for (session in sessionsRepository.sessionsArray!!) {
                        if (session.id == ticket.sessionId) {
                            if (LocalDateTime.parse(session.date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                                ) <= LocalDateTime.now()) {
                                return "Сеанс уже идёт"
                            }
                            sessionsRepository.removeSeat(session.id, ticket.place)
                            tickets -= ticket
                            ticketsArray = tickets.toTypedArray()
                            JSONTicketsSerializer().jsonSerialize(path, ticketsArray!!)
                            return "Билет возвращён"
                        }
                    }

                }
            }
        }
        return ("Билета с данным ID не существует, повторите попытку с корректным билетом")
    }

    fun tagVisitor(movieArray: Array<Movie>?, sessionsRepository: SessionsRepository, ticketId: String): String {
        if (ticketsArray != null) {
            for (ticket in ticketsArray!!) {
                if (ticket.id == ticketId) {
                    val sessionID = ticket.sessionId
                    for (session in sessionsRepository.sessionsArray!!) {
                        if (sessionID == session.id) {
                            val movieId = session.movieId
                            for (movie in movieArray!!) {
                                if (movieId == movie.id) {
                                    var dateStart = LocalDateTime.parse(
                                        session.date,
                                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                                    )
                                    val dateEnd = dateStart.plusMinutes(movie.duration.toLong())
                                    dateStart = dateStart.minusMinutes(15)
                                    val currDate = LocalDateTime.now()
                                    if (currDate < dateStart) {
                                        return "Отметка происходит за 15 минут до начала фильма. Попробуйте позже"
                                    }
                                    if (currDate >= dateEnd)
                                        return "Данный сеанс закончился, вы не успели."
                                }
                                if (session.places[ticket.place.toInt() - 1] == 2.toByte()) {
                                    return "Человек уже отмечен"
                                }
                                if (session.places[ticket.place.toInt() - 1] == 1.toByte()) {
                                    sessionsRepository.tagVisitor(sessionID, ticket.place)
                                    return "Человек успешно отмечен"
                                }
                            }
                        }
                    }
                }
            }
        }
        return ("Билета с данным ID не существует, повторите попытку с корректным билетом")
    }

    fun removeTickets(sessionId: String) {
        var isChanged = false
        if (ticketsArray != null) {
            val tickets = ticketsArray!!.toMutableList()
            for (ticket in tickets) {
                if (ticket.sessionId == sessionId) {
                    tickets -= ticket
                    isChanged = true
                    break
                }
            }
            if (isChanged) {
                ticketsArray = tickets.toTypedArray()
                JSONTicketsSerializer().jsonSerialize(path, ticketsArray!!)
            }
        }
    }

    fun editDate(sessionId: String, newDate: String) {
        if (ticketsArray != null) {
            var isChanged = false
            for (ticket in ticketsArray!!) {
                if (ticket.sessionId == sessionId) {
                    ticket.date = newDate
                    isChanged = true
                }
            }
            if (isChanged) {
                JSONTicketsSerializer().jsonSerialize(path, ticketsArray!!)
            }
        }
    }
}