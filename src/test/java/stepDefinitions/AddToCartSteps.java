package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;
import org.junit.Assert;
import utils.ConfigReader;
import Page.EbayHomePage;
import utils.ReportGenerator;

public class AddToCartSteps {


    WebDriver driver = Hooks.driver;
    long timeout = Long.parseLong(ConfigReader.get("timeout"));
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    EbayHomePage ebayHomePage = new EbayHomePage(driver);


    @Given("user is on the eBay homepage")
    public void user_is_on_the_e_bay_homepage() {
        driver.get(ConfigReader.get("base.url"));
    }

    @When("user searches for {string}")
    public void user_searches_for(String string) {
        ebayHomePage.searchBox.sendKeys(string);
        ebayHomePage.searchButton.click();
    }

    @When("selects the first product")
    public void selects_the_first_product() {
       wait.until(ExpectedConditions.elementToBeClickable(ebayHomePage.firstProduct)).click();
    }

    @When("adds the product to the cart")
    public void adds_the_product_to_the_cart() {
        String mainWindow = driver.getWindowHandle();

        // Wait until a new window is opened
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        // Wait until the page loaded and then click on add to cart option
        wait.until(ExpectedConditions.elementToBeClickable(ebayHomePage.addTocart)).click();

        // Wait until added to cart confirmation appears
        wait.until(ExpectedConditions.visibilityOf(ebayHomePage.addedTocart));

        // Switch back to the main window
        driver.switchTo().window(mainWindow);
    }

    @Then("the product should be added to the cart")
    public void the_product_should_be_added_to_the_cart() {
        driver.navigate().refresh();

        // Wait until cart count element is visible
        wait.until(ExpectedConditions.visibilityOf(ebayHomePage.cartcount));

        // Retry until cart count is non-empty and numeric
        wait.until(driver -> {
            String text = ebayHomePage.cartcount.getText().trim();
            return !text.isEmpty() && text.matches("\\d+");
        });
        //Get the cart count
        String count = ebayHomePage.cartcount.getText().trim();

        try {
            int cartItemCount = Integer.parseInt(count);
            Assert.assertTrue("Cart should have at least 1 item.", cartItemCount > 0);

            ReportGenerator.generateCartReport(cartItemCount, "cart_report.xlsx");

        } catch (NumberFormatException e) {
            throw new AssertionError("Cart count is not a valid number: " + count);
        }

    }
}
