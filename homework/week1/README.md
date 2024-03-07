# Event Storming
![ates1_event-storming2.png](ates1_event-storming2.png)

# Домены
* Авторизация
* Задачи
* Лицевые счета

![ates1_domain-model.png](ates1_domain-model.png)

# Коммуникации
## Синхронные:
* Бизнес-команды для сервиса авторизации
* Бизнес-команды для сервиса задач
* Сам процесс авторизации

![ates1_service-and-communications1.png](ates1_service-and-communications1.png)

## Асинхронные
* CUD-события для Сотрудников
  * В составе данных нужна роль
* Бизнес-событие регистрации Сотрудника

![ates1_service-and-communications2.png](ates1_service-and-communications2.png)

* Бизнес-событие назначения Задачи
* Бизнес-событие выполнения Задачи
* Бизнес-событие изменения баланса

![ates1_service-and-communications3.png](ates1_service-and-communications3.png)
