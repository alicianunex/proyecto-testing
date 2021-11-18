
Feature: Guardar Fabricante
  Desde la pagina de inicio se quiere guardar en BD un fabricante nuevo

  Scenario Outline: Guardar "<fabricante>"
    Given pagina de inicio
    When Hago click en guardar "<fabricante>"
    Then El fabricante se añade a DB "<fabricante>"

    Examples:
      | fabricante                                         |
      | Examplename 1234 d001 72 streetex cityL France     |
      | Name 657688 fdsf32 srfes cdsfr34 cacs Germany      |

