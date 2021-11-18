
El proyecto final consistirá en la creación de una web basada en el patrón Modelo Vista Controlador (MVC) con acceso a base de datos :

• Capa de datos: la aplicación utilizará el orm JPA para establecer la relación entre las tablas de la base de datos con las
clases Java bajo el paradigma de la Programación Orientada a Objetos POO. Se desarrollarán modelos para cubrir al menos una relación de cada tipo: one-to-one, one-to-many, many-to-one, many-to-many.

• Capa de control: se desarrollarán los controladores y servicios necesarios para procesar las peticiones HTTP que lleguen al backend de la aplicación así como realizar la lógica de negocio necesaria para realizar operaciones CRUD (crear, recuperar, actualizar y borrar) datos en la base de datos.

• Capa de visualización: se desarrollará la interfaz de usuario que permita interactuar con la información ofrecida por
la aplicación web y que será procesada por el backend para interactuar con la base de datos.

Una vez implementada la aplicación se elaborarán suites de tests unitarios, de integración y de interfaz gráfica para la
aseguración de la calidad en la misma. Por último, las suites de testing desarrolladas serán automatizadas utilizando
herramientas de integración continua.

Proceso

1. Test Unitario con JUnit 4.13 de los servicios utilizando Mockito 4.00 si es necesario
2. Test de Integracion con JUnit 4.13
3. Test web de los metodos REST con JUnit 4.13
4. Web Test de metodos MVC con Selenium Webdriver 3.14
5. Integracion de analisis de codigo
6. Integracion de automatizacion
7. Preparar informes de resultados
8. Presentacion de resultados


1 Test Unitario con JUnit 4.13 de los servicios utilizando Mockito 4.00 si es necesario

DOD. Cobertura > 100%-metodos 80%-lineas.

    1.1 Tests de Category
    1.2 Tests de Direction
    1.3 Tests de Manufacturer
    1.4 Tests de Product
    1.5 Tests de Patterns
        1.5.1 Behavioral
        1.5.2 Creational
        1.5.3 Structural
    // 1.6 Test de los metodos basicos (Entities)
    
2. Test de Integracion con JUnit 4.13

DOD. Comprobar que los repositorios funcionan de forma adecuada.

    2.1 Tests de Category
    2.2 Tests de Direction
    2.3 Tests de Manufacturer
    2.4 Tests de Product
    2.5 Tests de Patterns

3. Test web de los metodos REST con JUnit 4.13

DOD. Las llamadas REST devuelven respuestas adecuadas.

    3.1 Tests de Category
    3.2 Tests de Direction
    3.3 Tests de Manufacturer
    3.4 Tests de Product

4. Web Test de metodos MVC con Selenium Webdriver 3.14

DOD. Las paginas web se muestran correctamente y se puede interactuar con los elementos de la forma prevista. 
DOD. La interaccion de los HTML con la base de datos es correcta.

    4.1 Tests de Product/list
    4.2 Tests de Product/edit
    4.3 Tests de Product/edit-manufacturer
    4.4 Tests de Product/view
    4.5 Tests de Manufacturer/list
    4.6 Tests de Manufacturer/edit
    4.7 Tests de Manufacturer/view

5. Integracion de analisis de codigo

DOD. Los reportes se generan de manera correcta y se almacenan diariamente.

    5.1 MAVEN reporting tools
        5.1.1 JaCoCo
        5.1.2 Spotsbugs
        5.1.3 PMD
        5.1.4 Surefire
        5.1.5 Checkstyle
    5.2 GitHub Actions
    5.3 SonarCloud
    5.4 Jenkins
    
6. Integracion de automatizacion

DOD. El proyecto es ejecutado por las herramientas correctamente

    6.1 GitHub Actions
        6.1.1 Navegador fantasma
        6.1.2 Heroku server (https://proyecto-testinggrupo2.herokuapp.com/products)
    6.2 Jenkins
        6.2.1 Pipelines
        6.2.2 BlueOcean
    
7. Integracion de Cucumber
    7.1 Crear Features
    7.2 Crear Steps
    7.3 Integrar el punto 4. Selenium Webdriver en Pages

DOD. Cucumber esta integrado correctamente

    7.1 Integracion basica
    7.2 Cucumber Reports

8. Preparar informes de resultados

DOD. Se crea un historico con la evolucion del proyecto a lo largo del tiempo incluyendo todos los datos relevantes

    8.1 Revisar analisis de codigo
        8.1.1 Ver cambios a lo largo del tiempo
        8.1.2 Revisar y/o completar la cobertura hasta 100%-lineas
        8.1.4 Destacar momento de grandes mejoras
        8.1.5 ¿ HTML con todos los reportes ?
        
9. Presentacion de resultados

DOD. El objetivo del proyecto se ha cumplido, y el resultado se ha transmitido correctamente

    9.1 Preparar presentacion
        9.1.1 Crear diapositivas con los informes del punto 6
        9.1.2 ¿ Video ?
    9.2 Presentar
        9.2.1 Exposicion oral, 5 min cada persona
            9.2.1.1 Metodologia de trabajo / Solo 2 personas, reorganizacion
            9.2.1.2 Evolucion semanal
            9.2.1.3 Dificultades/Retos
            9.2.1.4 Resultados finales
            9.2.1.5 Posibilidades de Mantenimiento de tests a largo plazo
            9.2.1.6 Metodologia efectiva / resultados satisfactorios
    9.Notas (Recomendaciones originales)
        1. Presentación del problema / necesidad
        2. Equipo y roles
        3. Herramientas de metodología y metodología
        4. Pantallas de la aplicación (mostrar capturas y explicar brevemente las funcionalidades): login, pantalla principal, pantalla detalle, etc.
        5 . Conclusiones
            

----- ORIGINAL -----

Proceso : 
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
