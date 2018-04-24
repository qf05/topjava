Java Enterprise Online Project 
===============================

REST Requests:

1. Admin menu:
Path: /rest/admin/users

-get: / - get list all users: Response: 
[{"id":100001,"name":"Admin","email":"admin@gmail.com","password":"admin","enabled":true,"registered":"2018-04-24T08:28:50.394+0000","roles":["ROLE_ADMIN","ROLE_USER"],"caloriesPerDay":2000,"meals":null},{"id":100000,"name":"User","email":"user@yandex.ru","password":"password","enabled":true,"registered":"2018-04-24T08:28:50.394+0000","roles":["ROLE_USER"],"caloriesPerDay":2000,"meals":null}]

-get: /{id} - get user by id: Response(/100000): 
{"id":100000,"name":"User","email":"user@yandex.ru","password":"password","enabled":true,"registered":"2018-04-24T08:28:50.394+0000","roles":["ROLE_USER"],"caloriesPerDay":2000,"meals":null}

-get: /by - get user by email: Parameters: email=admin@gmail.com Response: 
{"id":100001,"name":"Admin","email":"admin@gmail.com","password":"admin","enabled":true,"registered":"2018-04-24T08:28:50.394+0000","roles":["ROLE_ADMIN","ROLE_USER"],"caloriesPerDay":2000,"meals":null}

-post: / - create user: Request body: {"name":"User2","email":"use2r@yandex.ru","password":"password","roles":["ROLE_USER"]} Response: 
{"id":100010,"name":"User2","email":"use2r@yandex.ru","password":"password","enabled":true,"registered":"2018-04-24T08:50:38.583+0000","roles":["ROLE_USER"],"caloriesPerDay":2000}

-put: /{id} - update user: Request body: {"id":100000,"name":"User3","email":"user3@yandex.ru","password":"password3","roles":["ROLE_USER"]} Response(/100000): void

-delete: /{id} - delete user: Response(/100000): void 


2. User menu:
Path: /rest/profile

-get: / - get this user: Response: 
{"id":100000,"name":"User","email":"user@yandex.ru","password":"password","enabled":true,"registered":"2018-04-24T09:02:35.561+0000","roles":["ROLE_USER"],"caloriesPerDay":2000,"meals":null}

-delete: / - delete this user:  Response: void

-put: / - update user: Request body: {"id":100000,"name":"User3","email":"user3@yandex.ru","password":"password3","roles":["ROLE_USER"]} Response: void


3. Meals menu:
Path: /rest/meals

-get: / - get all meals: Response:
[{"id": 100007,"dateTime": "2015-05-31T20:00:00","description": "Ужин","calories": 510,"exceed": true},
{"id": 100006,"dateTime": "2015-05-31T13:00:00","description": "Обед","calories": 1000,"exceed": true},
{"id": 100005,"dateTime": "2015-05-31T10:00:00","description": "Завтрак","calories": 500,"exceed": true},
{"id": 100004,"dateTime": "2015-05-30T20:00:00","description": "Ужин","calories": 500,"exceed": false},
{"id": 100003,"dateTime": "2015-05-30T13:00:00","description": "Обед","calories": 1000,"exceed": false},
{"id": 100002,"dateTime": "2015-05-30T10:00:00","description": "Завтрак","calories": 500,"exceed": false}]

-get: /{id} - get meal by id: Response(/100002): 
{"id": 100002,"dateTime": "2015-05-30T10:00:00","description": "Завтрак","calories": 500,"user": null}

-get: /filter - get meal between LocalDateTime: Parameters: start=2015-05-30T09:00:00&end=2015-05-30T17:00:00 Response:
[{"id": 100003,"dateTime": "2015-05-30T13:00:00","description": "Обед","calories": 1000,"exceed": false},
{"id": 100002,"dateTime": "2015-05-30T10:00:00","description": "Завтрак","calories": 500,"exceed": false}]

-get: /filter2 - get meal between LocalDate and LocalTime: Parameters: startDate=2015-05-30&startTime=09:00:00&endDate=2015-05-30&endTime=17:00:00 Response:
[{"id": 100003,"dateTime": "2015-05-30T13:00:00","description": "Обед","calories": 1000,"exceed": false},
{"id": 100002,"dateTime": "2015-05-30T10:00:00","description": "Завтрак","calories": 500,"exceed": false}]

-delete: /{id} - delete meal:  Response(/100002): void

-post: / - create meal: Request body: {"dateTime":"2018-04-21T20:00:00","description":"newMeal","calories":511} Response:
{"id": 100010,"dateTime": "2018-04-21T20:00:00","description": "newMeal","calories": 511,"user": null}

-put: / - create meal: Request body: {"id":100003,"dateTime":"2018-04-22T20:00:00","description":"update","calories":511} Response: void