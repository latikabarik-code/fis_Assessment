package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.ConfigReader;
import utils.ReportGenerator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BitcoinAPISteps {
    Response response;
    private Map<String, Object> bitcoinReportData = new LinkedHashMap<>();

    @Given("I send a request to the bitcoin API")
    public void send_request_to_api() {
        String endpoint = ConfigReader.get("bitcoin.api.url");
        response = RestAssured
                .given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Then("I should see USD, GBP, and EUR prices")
    public void validate_currencies() {
        Object usd = response.path("market_data.current_price.usd");
        Object gbp = response.path("market_data.current_price.gbp");
        Object eur = response.path("market_data.current_price.eur");

        Assert.assertNotNull(usd);
        Assert.assertNotNull(gbp);
        Assert.assertNotNull(eur);

        bitcoinReportData.put("USD Price", usd);
        bitcoinReportData.put("GBP Price", gbp);
        bitcoinReportData.put("EUR Price", eur);

    }

    @Then("the market cap and total volume are present")
    public void validate_market_cap_and_volume() {
        Object marketCapUsd = response.path("market_data.market_cap.usd");
        Object totalVolumeUsd = response.path("market_data.total_volume.usd");

        Assert.assertNotNull(marketCapUsd);
        Assert.assertNotNull(totalVolumeUsd);

        bitcoinReportData.put("Market Cap USD", marketCapUsd);
        bitcoinReportData.put("Total Volume USD", totalVolumeUsd);
    }

    @Then("price change in last 24 hours is available")
    public void validate_price_change_24h() {
        Object priceChange24h = response.path("market_data.price_change_percentage_24h");
        Assert.assertNotNull(priceChange24h);
        System.out.println("Price Change 24h: " + priceChange24h);

        bitcoinReportData.put("Price Change 24h %", priceChange24h);
    }

    @Then("the homepage URL is not empty")
    public void validate_homepage_url() {
        List<String> homepageList = response.path("links.homepage");
        Assert.assertNotNull(homepageList);
        Assert.assertFalse(homepageList.isEmpty());
        String homepageUrl = homepageList.get(0);
        Assert.assertTrue(homepageUrl != null && !homepageUrl.isEmpty());
        System.out.println("Homepage URL: " + homepageUrl);

        bitcoinReportData.put("Homepage URL", homepageUrl);
    }

    @Then("generate bitcoin API report")
    public void generate_bitcoin_api_report() {
        System.out.println("Report Data: " + bitcoinReportData);
        ReportGenerator.generateBitcoinReport(bitcoinReportData, "bitcoin_report.xlsx");
    }
}