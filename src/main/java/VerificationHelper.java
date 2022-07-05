import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.NoSuchElementException;

public class VerificationHelper {
    private WebDriver driver;

    public VerificationHelper(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        var title = driver.getTitle();
        return title;
    }

    public boolean isDisplay(WebElement element) {
        boolean status;
        try {
            status = element.isDisplayed();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }

    public boolean isSelect(WebElement element) {
        boolean status;
        try {
            status = element.isSelected();

        } catch (Exception e) {
            e.printStackTrace();
            status = true;
        }
        return status;
    }

    public boolean isEnable(WebElement element) {
        boolean status;
        try {
            status = element.isEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }

    public String getTextFromElement(WebElement element) {
        boolean status = isDisplay(element);
        String str;
        if (status) {
            str = element.getText();
        } else {
            str = null;
        }
        return str;
    }

    public void sendKeysToElement(WebElement element, String value) {
        boolean status = isDisplay(element);
        if (status && !value.isEmpty()) {
            element.sendKeys(value);
        }

    }

    public String getElementAttribute(WebElement element, String attributeValue) {
        boolean status = isDisplay(element);
        String att = null;
        if (status) {
            att = element.getAttribute(attributeValue);
        }
        return att;
    }

    public static void main(String[] args) {
        WebDriver driver = WebDriverManager.chromedriver().create();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        VerificationHelper verificationHelper = new VerificationHelper(driver);

        //getPageTitle
        String title = verificationHelper.getPageTitle();
        System.out.println("Page title: " + title);
        driver.get("https://itera-qa.azurewebsites.net/home/automation");
        WebElement maleGender = driver.findElement(By.id("male"));
        String elementName = maleGender.getAccessibleName();
        //maleGender Status
        boolean maleDisplayedStatus = verificationHelper.isDisplay(maleGender);
        boolean maleSelectedStatus = verificationHelper.isSelect(maleGender);
        boolean maleEnabledStatus = verificationHelper.isEnable(maleGender);

        //Print Status to Screen
        System.out.println(elementName + " Display Status: " + maleDisplayedStatus);
        System.out.println(elementName + " Selected Status: " + maleSelectedStatus);
        System.out.println(elementName + "Enabled Status: " + maleEnabledStatus);

        //Click On maleGender Element only if it's isSelected Method Return False
        if (!maleSelectedStatus) {
            maleGender.click();
        }
        //Print MaleGender Selected Status After Clicking.
        boolean maleSelectedStatusAfter = verificationHelper.isSelect(maleGender);
        System.out.println("Male Selected Status After Clicking is: " + maleSelectedStatusAfter);

        //Element with Disabled Status Check.
        WebElement other = driver.findElement(By.id("other"));
        boolean otherEnabledStatus = verificationHelper.isEnable(other);
        System.out.println(other.getAccessibleName() + " Enabled Status is: " + otherEnabledStatus);

        //GetText From Element
        WebElement nameLabel = driver.findElement(By.cssSelector("label[for='Name']"));
        String text = verificationHelper.getTextFromElement(nameLabel);
        System.out.println(nameLabel.getAccessibleName() + " Inner Text: " + text);

        //SendKeysToElement
        WebElement name = driver.findElement(By.id("name"));
        verificationHelper.sendKeysToElement(name, "User1");

        //Get Attribute Of WebElement
        String att = verificationHelper.getElementAttribute(name, "placeholder");
        System.out.println(name.getAccessibleName() + " PlaceHolder Value is: " + att);

    }

}
