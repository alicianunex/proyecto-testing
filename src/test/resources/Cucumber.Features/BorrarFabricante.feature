Feature: Borrar Fabricante
  Borrar un fabricantes desde la interfaz

  Scenario Outline: Borrar "<fabricante>" desde -list
    Given Crear nuevo fabricante "<fabricante>"
    When Hago click en borrar desde lista
    Then El fabricante se ha borrado de DB

    Examples:
      | fabricante                                      |
      | Examplename 1234 001 72 street ex cityL France  |
      | Name 657688 32 99976 cdsfr street cacs Germany  |

  Scenario Outline: Borrar "<fabricante>" desde -view
    Given Crear nuevo fabricante "<fabricante>"
    And Pagina de ver
    When Hago click en borrar desde ver
    Then El fabricante se ha borrado de DB

    Examples:
      | fabricante                                      |
      | Examplename 1234 001 72 street ex cityL France  |
      | Name 657688 32 99976 cdsfr street cacs Germany  |

  Scenario: Borrar fabricante desde borrar todos
    Given Pagina de inicio
    When Hago click en borrar todos los fabricantes
    Then Todos los fabricantes se han borrado de DB
    And Se vuelven a generar los fabricantes






