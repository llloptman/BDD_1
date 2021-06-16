package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.Integer.valueOf;

public class DashboardPage {
    public DataHelper.Cards firstCard = DataHelper.getFirstCard();
    public DataHelper.Cards secondCard = DataHelper.getSecondCard();


    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement firstCardRefillButton = $("[data-test-id='" + firstCard.getCardId() + "'] button");
    private SelenideElement secondCardRefillButton = $("[data-test-id='" + secondCard.getCardId() + "'] button");

    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public RefillPage refillFirstCard() {
        firstCardRefillButton.click();
        return new RefillPage(DataHelper.getCardLastNumbers(firstCard.getCardNumber()));
    }

    public RefillPage refillSecondCard() {
        secondCardRefillButton.click();
        return new RefillPage(DataHelper.getCardLastNumbers(secondCard.getCardNumber()));
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = cards.last().text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void restoreBalanceTo10k() {
        int firstCardBalance = getFirstCardBalance();
        int secondCardBalance = getSecondCardBalance();

        if (firstCardBalance > secondCardBalance) {
            int restAmount = Math.abs(10000 - firstCardBalance);
            refillSecondCard();
            new RefillPage().setForm(restAmount, firstCard.getCardNumber());
        } else {
            int restAmount = Math.abs(10000 - secondCardBalance);
            refillFirstCard();
            new RefillPage().setForm(restAmount, secondCard.getCardNumber());
        }
    }
}
