package com.bol.kalaha.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
    plugin = {"pretty", "html:target/cucumber-report"},
    extraGlue = "com.bol.kalaha.integration")
public class CucumberIntegrationTest {
}
