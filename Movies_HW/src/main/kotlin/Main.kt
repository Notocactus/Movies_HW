import java.time.LocalDateTime

fun main() {
    val system = System()
    var isEnter = false
    var command: String
    while (!isEnter) {
        println(authoMenu)
        command = readln()
        when (command) {
            "0" -> return
            "1" -> {
                println("Введите логин: ")
                val login = readln()
                println("Введите пароль: ")
                val password = readln()
                if (system.enter(login, password)) {
                    println("Авторизация прошла успешно")
                    isEnter = true
                } else {
                    println("Пользователя с подобными данными не существует")
                }
            }

            "2" -> {
                println("Введите логин: ")
                val login = readln()
                println("Введите пароль: ")
                val password = readln()
                if (system.register(login, password)) {
                    println("Регистрация прошла успешно")
                    isEnter = true
                } else {
                    println("Пользователь с такими данными уже существует")
                }
            }

            else -> {
                println(
                    "\nВведена неверная команда. Пожалуйста, выберите одну из представленных вариантов. " +
                            "\nНажмите Enter чтобы вернуться к меню. "
                )
                readln()
            }
        }
    }
    while (true) {
        system.clearSessions(LocalDateTime.now())

        println(mainMenu)
        command = readln()
        when (command) {
            "0" -> return

            "1" -> {
                println("Введите название фильма: ")
                val movieName: String = readln()
                println(movieMenu)
                command = readln()
                when (command) {
                    "0" -> continue

                    "1" -> {
                        println("Введите длительность фильма в минутах: ")
                        var input: String = readln()
                        while (!isUInt(input)) {
                            println("Введите корректную длительность фильма в минутах: ")
                            input = readln()
                        }
                        val duration: UInt = input.toUInt()
                        println(system.addMovie(movieName, duration))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "2" -> {
                        println("Введите дату и время сеанса (DD/MM/YYYY HH:MM): ")
                        var sessionDate: String = readln()
                        while (!isDateTimeValid(sessionDate)) {
                            println("Пожалуйста введите корректную дату: ")
                            sessionDate = readln()
                        }
                        println("Введите цену билета на сеанс: ")
                        var input = readln()
                        while (!isUInt(input)) {
                            println("Введите верную цену: ")
                            input = readln()
                        }
                        val cost = input.toUInt()
                        println(system.addSession(movieName, sessionDate, cost))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "3" -> {
                        println("Введите новое название фильма: ")
                        val newName = readln()
                        println(system.editMovie(movieName, newName))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "4" -> {
                        println("Введите новую длительность фильма: ")
                        var input = readln()
                        while (!isDurationValid(input) && !isUInt(input)) {
                            println("Введите корректную длительность фильма в минутах: ")
                            input = readln()
                        }
                        val newDur = input.toUInt()
                        println(system.editMovie(movieName, newDur))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "5" -> {
                        println(system.removeMovie(movieName))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "6" -> {
                        println(system.showSessionsByName(movieName))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    else -> {
                        println(
                            "\nВведена неверная команда. Пожалуйста, выберите одну из представленных вариантов. " +
                                    "\nНажмите Enter чтобы вернуться к главному меню. "
                        )
                        readln()
                        continue
                    }
                }
            }

            "2" -> {
                println("Введите дату и время сеанса (DD/MM/YYYY HH:MM): ")
                var sessionDate: String = readln()
                while (!isDateTimeValid(sessionDate)) {
                    println("Пожалуйста введите корректную дату: ")
                    sessionDate = readln()
                }
                println(sessionMenu)
                command = readln()
                when (command) {
                    "0" -> continue

                    "1" -> {
                        println("Введите номер места")
                        var place: String = readln()
                        var placeNum: UInt
                        while (true) {
                            if (isUInt(place)) {
                                placeNum = place.toUInt()
                                if (placeNum >= 1u && placeNum <= 100u) {
                                    break
                                }
                            }
                            println("Введите корректное место (1, 100): ")
                            place = readln()
                            continue
                        }
                        println(system.sellTicket(sessionDate, placeNum))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "2" -> {
                        println("Введите новую дату и время (DD/MM/YYYY HH:MM) сеанса: ")
                        var newDate: String = readln()
                        while (!isDateTimeValid(newDate)) {
                            println("Пожалуйста введите корректную дату: ")
                            newDate = readln()
                        }
                        println(system.editSessionDate(sessionDate, newDate))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "3" -> {
                        println("Введите цену: ")
                        var input = readln()
                        while (!isUInt(input)) {
                            println("Введите верную цену: ")
                            input = readln()
                        }
                        val cost = input.toUInt()
                        println(system.editSessionCost(sessionDate, cost))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "4" -> {
                        println(system.removeSession(sessionDate))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "5" -> {
                        println(system.showPlaces(sessionDate))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    else -> {
                        println(
                            "\nВведена неверная команда. Пожалуйста, выберите одну из представленных вариантов. " +
                                    "\nНажмите Enter чтобы вернуться к главному меню. "
                        )
                        readln()
                        continue
                    }
                }
            }

            "3" -> {
                println(ticketMenu)
                command = readln()
                when (command) {
                    "1" -> {
                        println("Введите id билета, который хотите вернуть: ")
                        println(system.returnTicket(readln()))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    "2" -> {
                        println("Введите id билета посетителя: ")
                        println(system.tagVisitor(readln()))
                        println("\nНажмите Enter чтобы вернуться к меню. ")
                        readln()
                    }

                    else -> {
                        println(
                            "\nВведена неверная команда. Пожалуйста, выберите одну из представленных вариантов. " +
                                    "\nНажмите Enter чтобы вернуться к главному меню. "
                        )
                        readln()
                        continue
                    }
                }
            }

            "4" -> {
                println("Полный список фильмов: ")
                println(system.showMovies())
                println("\nНажмите Enter чтобы вернуться к меню. ")
                readln()
            }

            "5" -> {
                println("Введите дату (DD/MM/YYYY): ")
                var sessionDate : String = readln()
                while (!isDateValid(sessionDate)) {
                    println("Пожалуйста введите корректную дату: ")
                    sessionDate = readln()
                }
                println(system.showSessionsByDate(sessionDate))
            }

            else -> {
                println(
                    "\nВведена неверная команда. Пожалуйста, выберите одну из представленных вариантов. " +
                            "\nНажмите Enter чтобы вернуться к главному меню. "
                )
                readln()
            }
        }
    }
}