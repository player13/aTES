### Регистрация нового попуга
#### Админ авторизуется на сервере авторизации (SSO)
![01_admin-login.png](01_admin-login.png)
#### Регистрирует пользака (да-да, прямо с паролем)
![02_auth-add-new-employee.png](02_auth-add-new-employee.png)
#### Отправляются cud и business эвенты для нового пользователя
![04_user-created-cud-event.png](04_user-created-cud-event.png)

### Добавление задачи на доску
#### Новый попуг уже может залогиниться
![05_new-employee-login.png](05_new-employee-login.png)
#### И посмотреть кто он :)
![06_current-user.png](06_current-user.png)
#### А потом добавить задачу на доску
![07_create-new-task.png](07_create-new-task.png)
#### Отправляются cud и business эвенты для новой задачи
![08_task-created-cud-event.png](08_task-created-cud-event.png)

### Назначение задач
#### Но тут злобный админ жмет казино-кнопку (переназначение всех задач)
![09_admin-shuffle-tasks.png](09_admin-shuffle-tasks.png)
#### Незаконченные задачи переназначаются и отправляется куча эвентов о назначении задачи
![10_task-assigned-business-events.png](10_task-assigned-business-events.png)
#### А новый попуг вдруг обнаруживает что на него уже назначили задачу
![11_assigned-tasks.png](11_assigned-tasks.png)
#### И деньги ведь забрать должны
![12_employee-disappointed.png](12_employee-disappointed.png)

### Исполнение задач
#### Ну ладно, придется исполнять
![13_complete-task.png](13_complete-task.png)
#### Отправляются эвенты что задача исполнена
![14_task-completed-business-event.png](14_task-completed-business-event.png)
#### А где деньги за то что попуг поработал?
![15_a-netu.png](15_a-netu.png)
