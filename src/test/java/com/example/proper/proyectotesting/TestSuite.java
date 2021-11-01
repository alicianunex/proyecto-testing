package com.example.proper.proyectotesting;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({"com.example.proper.proyectotesting.entities","com.example.proper.proyectotesting.service"})
public class TestSuite {}
