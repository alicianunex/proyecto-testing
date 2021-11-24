Feature: Borrar Fabricante
  Borrar un fabricantes desde la interfaz

  Scenario Outline: Borrar "<fabricante>" desde -list
    Given Crear nuevo fabricante "<fabricante>"
    When Hago click en borrar desde lista
    Then El fabricante se ha borrado de DB

    Examples:
      | fabricante                                      |
      | Examplename 1234 001 72 street ex cityL France  |
      | Name2 9876 002 45 first street city2 Germany    |

  Scenario Outline: Borrar "<fabricante>" desde -view
    Given Crear nuevo fabricante "<fabricante>"
    And Voy a Pagina view
    When Hago click en borrar desde ver
    Then El fabricante se ha borrado de DB

    Examples:
      | fabricante                                      |
      | Examplename 1234 001 72 street ex cityL France  |
      | Name2 9876 002 45 first street city2 Germany    |

  Scenario: Borrar fabricante desde borrar todos
    Given En Inicio hago click en borrar todos los fabricantes
    When Todos los fabricantes se han borrado de DB
    Then Se vuelven a generar los fabricantes






