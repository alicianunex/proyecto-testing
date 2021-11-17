package com.example.proyectotesting.Features;

@Guardar
Feature: Guardar Producto
Objetivo Guardar un producto
Para comprobar funcionalidad

    @Guardar
    Scenario Outline:
        Given Un producto a guardar
        When Se pulse el boton guardar
        Then Se guarda el producto

        Examples:
            | productsize | product | productsize |
            |    2  |             1 |           3 |
            |    3  |             1 |           4 |
