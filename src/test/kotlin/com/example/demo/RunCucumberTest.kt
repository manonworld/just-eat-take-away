package com.example.demo

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite
import org.junit.runner.RunWith

@Suite
@RunWith(Cucumber::class)
@CucumberOptions(
    features = arrayOf("src/test/resources/kotlin/com/example/demo/features"),
    glue = arrayOf("com.example.demo")
)
class RunCucumberTest