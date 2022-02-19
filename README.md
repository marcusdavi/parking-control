# parking-control
API utilizando Spring Boot criada a partir do minicurso de Michelli Brito no Youtube


## 1. Running

1. Import Maven Projects (IDE Eclipse ou STS);

2. Maven Build: clean install

3. Run as SpringBoot Application

4. Base URL:
* http://localhost:8080/api/v1


## 2. Endpoints

### a) Parking Spot
**1. GET /parking-spot - List with all parking spots. It have pagination and sorting

**2. GET /parking-spot/{id}** - Detail the parking spot with id equal to {id}

**3. DELETE /parking-spot/{id}** - Delete the category with id equal to {id}. Only categories unused can be deleted.

**4. POST /parking-spot** - Create a parking spot.
*RequestBody:*
```
{
    "parkingSpotNumber": "003E",
    "licensePlateCar": "AAA6366",
    "brandCar": "Fiat",
    "modelCar": "Uno",
    "colorCar": "Prata",
    "responsibleName": "Joao Santos",
    "apartment": "501",
    "block": "B"
}
```

**5. PUT /parking-spot{id} - update a parking spot with id equal to {id}
*RequestBody:*
```
{
    "parkingSpotNumber": "003E",
    "licensePlateCar": "AAA6366",
    "brandCar": "Fiat",
    "modelCar": "Uno",
    "colorCar": "Prata",
    "responsibleName": "Joao Santos",
    "apartment": "501",
    "block": "B"
}
```
