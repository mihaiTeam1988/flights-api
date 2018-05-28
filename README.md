# Shop API

This API exposes the endpoints of a virtual shop and could be used to save and query products and create or cancel offers.
Expired offers are cancelled automatically.

### Build & run

In the root of the project:
* Build the project: ```./gradlew build```
* Run the unit tests: ```./gradlew test```
* Run the project: ```./gradlew bootRun```
* Access the swagger UI: ```http://localhost:8080/shop/swagger-ui.html```

### Endpoints
    /offers       
        /GET  - get all offers with filtering param for status
        /POST - create offer
    /offers/{id}
        /GET  - get offer with id
        /PUT  - cancel offer with id
    /products
        /GET  - get all products
        /POST - create product

### Tools and Frameworks
* Java8
* Spring Boot v2.0.2
* Gradle - build management
* H2 - in memory database
* JUnit and Mockito
* Swagger - API documentation

### Assumptions
- because an offer cannot logically exist without a product, this has been introduced in the context even though
the statement requires only endpoints for the offers
- Assumptions related to offers:
    - an offer starts and ends at midnight (12am), timezone is set to the running system's timezone. A scheduled jobs is
    set to run at 12:00:01 AM and cancel the offers with the end date in the past.  
    - offer *uniqueness* is determined by the **description**
    - offers can have the following statuses: **ACTIVE** (when it is created) and **EXPIRED** (after it is cancelled). This 
    information is returned in the JSON payload and is used to reflect the state of the offer
- Assumptions related to products:
    - product *uniqueness* is determined by the **name**
- authentication and authorisation have been ignored for simplicity
- for simplicity, it is assumed that the app is running on a single server. In this way the scheduled job execution does 
not need to be synchronized with jobs running on other servers in a cluster. In that case, a cluster management tool like 
Zookeeper should have been implemented.

### Caveats
- because it is an in-memory database which is initialised at startup the data used to 
populate the DB at startup will is used for repository unit test as well

