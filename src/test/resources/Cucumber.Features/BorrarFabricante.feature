Feature: Borrar Fabricante
  Borrar un fabricantes desde la interfaz

  Scenario Outline: Borrar "<fabricante>" desde -list
    Given Crear nuevo fabricante "<fabricante>"
    When Hago click en borrar desde lista
    Then El fabricante se ha borrado de DB

    Examples:
      | fabricante                                         |
      | Examplename 1234 d001 72 streetex cityL France     |
      | Name 657688 fdsf32 srfes cdsfr34 cacs Germany      |

  Scenario Outline: Borrar "<fabricante>" desde -view
    Given Crear nuevo fabricante "<fabricante>"
    And Pagina de ver "<fabricante>"
    When Hago click en borrar desde ver
    Then El fabricante se ha borrado de DB

    Examples:
      | fabricante                                         |
      | Examplename 1234 d001 72 streetex cityL France     |
      | Name 657688 fdsf32 srfes cdsfr34 cacs Germany      |

  Scenario Outline: Borrar "<fabricante>" desde borrar todos
    Given pagina de inicio
    When Hago click en borrar todos los fabricantes
    Then Todos los fabricantes se han borrado de DB "<fabricante>"

    Examples:
      | fabricante                                         |
      | Examplename 1234 d001 72 streetex cityL France     |
      | Name 657688 fdsf32 srfes cdsfr34 cacs Germany      |



