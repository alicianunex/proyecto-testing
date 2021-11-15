Proceso para ahora: 
1. Crear Issue en GitHub
2. Implementar cambio solicitado
3. Realizar commit y push poniendo el número de la issue en 

Capa servicio 
* ProductServiceImpl --> Alan
* ManufacturerServiceImpl --> Jorge
* DirectionServiceImpl --> Karim
* CategoryServiceImpl --> Ioan
* ManufacturerServiceImpl método custom para filtrar fabricantes por pais --> Ismael
    1. Declarar método en interfaz ManufacturerRepository 
    2. Declarar método en interfaz ManufacturerService 
    3. Implementar método en interfaz ManufacturerServiceImpl
* DirectionServiceImpl método custom para filtrar por ciudad y país a la vez --> Miguel
    1. Declarar método en interfaz DirectionRepository 
    2. Declarar método en interfaz DirectionService
    3. Implementar método en interfaz DirectionServiceImpl
* CategoryServiceImpl método custom para filtrar por color --> Álvaro
    1. Declarar método en interfaz CategoryRepository
    2. Declarar método en interfaz CategoryService
    3. Implementar método en interfaz CategoryServiceImpl

Capa controlador
* ProductRestController --> alan
  * findAll()
  * findOne()
  * findByPriceBetween()
  * findByManufacturer()
  * create()
  * update()
  * deleteById()
  * deleteAll()
* ManufacturerRestController --> Alicia
* DirectionRestController --> María Ángeles
* CategoryRestController --> David
* 
