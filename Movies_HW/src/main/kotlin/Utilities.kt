import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


const val sessionsFilePath = "src/main/data/sessions.json"
const val moviesFilePath = "src/main/data/movies.json"
const val ticketsFilePath = "src/main/data/tickets.json"
const val staffFilePath = "src/main/data/staff.json"

const val authoMenu = "Список команд:\n" +
        "1) Войти\n" +
        "2) Зарегистрироваться в системе\n" +
        "0) Выйти из приложения\n" +
        "Введите номер команды: "

const val mainMenu = "С чем мы будем работать?\n" +
        "1) С фильмами\n" +
        "2) С сеансами\n" +
        "3) С билетами\n" +
        "4) Показать все фильмы\n" +
        "0) Выйти"

const val movieMenu = "1) Добавить фильм \n" +
        "2) Добавить сеанс \n" +
        "3) Редактировать название фильма \n" +
        "4) Редактировать длительность фильма \n" +
        "5) Удалить фильм \n" +
        "6) Показать сеансы (по фильму) \n" +
        "0) Вернуться к главному меню"

const val sessionMenu = "1) Продать билет \n" +
        "2) Редактировать дату сеанса \n" +
        "3) Редактировать цену сеанса \n" +
        "4) Удалить сеанс \n" +
        "5) Показ мест \n" +
        "6) Показать сеансы (по дате) \n" +
        "0) Вернуться к главному меню"

const val ticketMenu = "1) Возврат билета \n" +
        "2) Отметить посещение \n" +
        "0) Вернуться к главному меню"

const val menu = "Список команд: \n" +
        "1) Продать билет \n" +
        "2) Возврат билета \n" +
        "3) Отметить посещение \n" +
        "4) Добавить фильм \n" +
        "5) Редактировать название фильма \n" +
        "6) Редактировать длительность фильма \n" +
        "7) Удалить фильм \n" +
        "8) Добавить сеанс \n" +
        "9) Редактировать дату сеанса \n" +
        "10) Редактировать цену сеанса \n" +
        "11) Удалить сеанс \n" +
        "12) Показ мест \n" +
        "13) Показать сеансы (по фильму) \n" +
        "14) Показать сеансы (по дате) \n" +
        "15) Показать все фильмы \n" +
        "0) Выйти \n" +
        "Введите номер команды: "

fun isDateValid(date: String?): Boolean {
    val myFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    myFormat.isLenient = false
    try {
        myFormat.parse(date)
        val dateToLocalDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        return dateToLocalDateTime > LocalDateTime.now()
    } catch (e: Exception) {
        return false
    }
}

fun isDurationValid(input: String?): Boolean {
    val myFormat = SimpleDateFormat("HH:mm")
    myFormat.isLenient = false
    try {
        myFormat.parse(input)
        return true
    } catch (e: Exception) {
        return false
    }
}

fun isUInt(input: String?): Boolean {
    try {
        input!!.toUInt()
        return true
    } catch (e: NumberFormatException) {
        return false
    }
}
