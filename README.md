``curl -d '{ "firstName" : "Daenerys", "lastName" : "Targaryen"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/customers | json_pp``

``curl  -X GET http://localhost:8080/api/v1/customers | json_pp``

``curl -X GET http://localhost:8080/api/v1/customers/881b0517-849f-4e33-8592-9fbcec47c4cb | json_pp``

``curl -d '{ "firstName" : "Daenerys", "lastName" : "Targaryen"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/api/v1/customers/eb8ddfdb-17bd-4b6f-a969-f540e1b2cfd7 | json_pp
``