# Health Care Enrollment API
This is a health care enrollment application.

The goal is for customers to enroll themselves by providing their name, birthdate, phone number and the number
of dependents the customer covers for.

The application uses a Spring boot framework which connects to mongodb. Spring Security is enabled therefore one must be a user
to access endpoints.

##Connect to you own mongodb
    spring.data.mongodb.host=localhost
    spring.data.mongodb.port=(your port number)
    spring.data.mongodb.database=(name of the database)
   
#Must create a user before adding an enrollee
Create a user with a username and password therefore the user has access to adding themselves as enrollee and
dependents

##Head over to postman and paste the following url to add a user
    http://localhost:8080/api/add/user
To specify the document for mongodb, select the `body` tab, click the option `raw` and set the data as `JSON`

##Example
    {
        "firstName": "Brian",
        "lastName": "Morales",
        "userRole": "ROLE_ADMIN"
        "userName": "bammor96",
        "password": "cr243sdfa"
    }
Must specify `ROLE_ADMIN` to enter data to mongodb.

Once you add the data, select `POST` operation, hit send.
#Enrollee API
Before adding the url go to the authorization tag, select `Basic Auth` and under `username` and `password`, add
the username and password of the user respectively.

##Get all enrollees
    http://localhost:8080/api/enrollees
This endpoint retrieves all enrolled
##Response
    [
        {
            "id": 2,
            "firstName": "jocy",
            "lastName": "mo",
            "activationStatus": true,
            "birthDate": "10/12/2020",
            "phoneNumber": "818-563-3253",
            "dependents": [
                {
                    "id": null,
                    "firstName": "david",
                    "lastName": "morales",
                    "birthDate": "10/12/2020"
                },
                {
                    "id": null,
                    "firstName": "brian",
                    "lastName": "morales",
                    "birthDate": "10/14/2020"
                },
                {
                    "id": null,
                    "firstName": "william",
                    "lastName": "morales",
                    "birthDate": "07/10/2020"
                }
            ]
        }
    ]

##Get dependents from desired enrollee
    http://localhost:8080/api/dependents/{enter firstname}/firstName/{enter lastname}/lastName
###Example:
    http://localhost:8080/api/dependents/jocy/firstName/mo/lastName
###Response
    [
        {
            "id": null,
            "firstName": "david",
            "lastName": "morales",
            "birthDate": "10/12/2020"
        },
        {
            "id": null,
            "firstName": "brian",
            "lastName": "morales",
            "birthDate": "10/14/2020"
        },
        {
            "id": null,
            "firstName": "william",
            "lastName": "morales",
            "birthDate": "07/10/2020"
        }
    ]

#Post Operations
Before adding a resource to mongo, must add `username` and `password` just like for the 
`GET` requests

Then select the `Body` tag and add the format.
#####Endpoint for adding an enrollee
    http://localhost:8080/api/add/enrollee
#####Body format
    {
        "firstName": "Dilma",
        "lastName": "Morales",
        "activationStatus": true,
        "birthDate": "04/25/1964",
        "phoneNumber": "818-423-6735",
        "dependents": [
            {
                "firstName": "david",
                "lastName": "morales",
                "birthDate": "10/12/2020"
            },
            {
                "firstName": "brian",
                "lastName": "morales",
                "birthDate": "10/14/2020"
            },
            {
                "firstName": "william",
                "lastName": "morales",
                "birthDate": "07/10/2020"
            }
        ]
    }
 #Patch Operations
 
 ###Adding/deleting or updating a dependent
        http://localhost:8080/api/patch/modify/enrollee/dependents
 #####Body format
    {
        "firstName": "jocy",
        "lastName": "mo",
        "dependents": [
            {
                "firstName": "Johnny",
                "lastName": "Navarro",
                "birthDate": "12/31/2006"
            }
        ]
    }
 Must specify `firstName` and `lastName` to know which enrollee to grab from the database
 #####Response
    {
        "id": 2,
        "firstName": "jocy",
        "lastName": "mo",
        "activationStatus": null,
        "birthDate": "10/12/2020",
        "phoneNumber": "ROLE_USER",
        "dependents": [
            {
                "firstName": "Johnny",
                "lastName": "Navarro",
                "birthDate": "12/31/2006"
            }
        ]
    }
###Modifying ActivationStatus
     http://localhost:8080/api/patch/enrollee/status
#####Body format
    {
        "firstName": "jocy",
        "lastName": "mo",
        "activationStatus": true
    }
#####Response
    {
        "id": 2,
        "firstName": "jocy",
        "lastName": "mo",
        "activationStatus": true,
        "birthDate": "10/12/2020",
        "phoneNumber": "ROLE_USER",
        "dependents": [
            {
                "id": null,
                "firstName": "Johnny",
                "lastName": "Navarro",
                "birthDate": "12/31/2006"
            }
        ]
    }
###Modifying phoneNumber
     http://localhost:8080/api/patch/enrollee/phoneNumber
#####Body format
    {
        "firstName": "jocy",
        "lastName": "mo",
        "phoneNumber": "818-432-6425"
    }
#####Response
    {
        "id": 2,
        "firstName": "jocy",
        "lastName": "mo",
        "activationStatus": true,
        "birthDate": "10/12/2020",
        "phoneNumber": "818-432-6425",
        "dependents": [
            {
                "id": null,
                "firstName": "Johnny",
                "lastName": "Navarro",
                "birthDate": "12/31/2006"
            }
        ]
    }
#Deleting an enrollee
Change the tag to `DELETE`

#####Endpoint
    http://localhost:8080/api/delete/enrollee/{firstName}/firstName/{lastName}/lastName
#####Example
    http://localhost:8080/api/delete/enrollee/jocy/firstName/mo/lastName
    

