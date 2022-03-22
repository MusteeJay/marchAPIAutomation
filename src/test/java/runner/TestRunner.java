package runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/feature_files",
        plugin = {"pretty", "html:target/ReportsDestination.html"},
        monochrome = true,
//  if the step definition class or package is in a location separate from the testRunner class, we need glue to get it
        glue = {"step_Definitions"},
        tags = "@RestTok"
)
public class TestRunner {
}
