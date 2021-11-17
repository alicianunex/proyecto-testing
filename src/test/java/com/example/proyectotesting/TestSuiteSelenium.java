package com.example.proyectotesting;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({"com.example.proyectotesting.controller.mvc"})
@TestMethodOrder(value = MethodOrderer.MethodName.class)
public class TestSuiteSelenium {}
