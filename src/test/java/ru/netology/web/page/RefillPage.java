package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.security.Key;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Value
public class RefillPage {
    private SelenideElement heading = $(new Selectors.WithText("Пополнение карты"));
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement toField = $("[data-test-id='to'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification']");
    private DashboardPage dashboardPage = new DashboardPage();


    RefillPage() {
        heading.shouldBe(Condition.visible);

    }

    RefillPage(String cardLastNumbers) {
        toField.shouldHave(Condition.value("**** **** **** " + cardLastNumbers));
    }

    public DashboardPage setForm(int amount, String cardNumberFrom) {
        amountField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        fromField.setValue(cardNumberFrom);
        transferButton.click();

        return new DashboardPage();

    }

    public DashboardPage setForm(double amount, String cardNumberFrom) {
        amountField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        fromField.setValue(cardNumberFrom);
        transferButton.click();

        return new DashboardPage();

    }

    public void setFormForErrors(double amount, String cardNumberFrom) {
        amountField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        fromField.setValue(cardNumberFrom);
        transferButton.click();
        errorMessage.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void checkBalance(double firstCardStartBalance, double secondCardStartBalance, double amount, String cardNumberFrom) {
        if (cardNumberFrom == DataHelper.getFirstCard().getCardNumber()) {
            assertTrue(dashboardPage.getFirstCardBalance() > 0);
            assertEquals(Math.abs(firstCardStartBalance - amount), Math.abs(dashboardPage.getFirstCardBalance()));
            assertEquals(Math.abs(secondCardStartBalance + amount), Math.abs(dashboardPage.getSecondCardBalance()));
        } else if (cardNumberFrom == DataHelper.getSecondCard().getCardNumber()) {
            assertTrue(dashboardPage.getSecondCardBalance() > 0);
            assertEquals(Math.abs(firstCardStartBalance + amount), Math.abs(dashboardPage.getFirstCardBalance()));
            assertEquals(Math.abs(secondCardStartBalance - amount), Math.abs(dashboardPage.getSecondCardBalance()));
        }
    }


}
