package Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class EbayHomePage {
    WebDriver driver;

    public EbayHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id ="gh-ac")
    public WebElement searchBox;

    @FindBy(xpath = "//span[@class='gh-search-button__label']")
    public WebElement searchButton;

    @FindBy(xpath = "(//a[contains(@class, 'su-link')])[1]//span[contains(@class, 'su-styled-text')]")
    public WebElement firstProduct;

    @FindBy(xpath = "//span[@class='ux-call-to-action__text' and contains(text(),'Add to cart')]")
    public WebElement addTocart;

    @FindBy(xpath = "//span[contains(text(), 'Added to cart')]")
    public WebElement addedTocart;

    @FindBy(xpath = "//span[@class='gh-cart__icon']")
    public WebElement cartcount;


}
