package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import org.openqa.selenium.Keys;

import java.security.Key;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
@Value
public class RefillPage {
    private SelenideElement heading = $(new Selectors.WithText("Пополнение карты"));
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement toField = $("[data-test-id='to'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage  = $("[data-test-id='error-notification']");


    RefillPage() {
        heading.shouldBe(Condition.visible);

    }

    RefillPage(String cardLastNumbers){
        toField.shouldHave(Condition.value("**** **** **** " + cardLastNumbers));
    }

    public DashboardPage setForm(int amount, String cardNumberFrom){
        amountField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        fromField.setValue(cardNumberFrom);
        transferButton.click();

        return new DashboardPage();

    }
    public DashboardPage setForm(double amount, String cardNumberFrom){
        amountField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        fromField.setValue(cardNumberFrom);
        transferButton.click();

        return new DashboardPage();

    }
    public RefillPage setFormForErrors(int amount, String cardNumberFrom){
        amountField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        fromField.setValue(cardNumberFrom);
        transferButton.click();
        return new  RefillPage();

    }



}
