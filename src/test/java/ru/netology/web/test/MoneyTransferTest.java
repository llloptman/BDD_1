package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    DataHelper.Cards firstCard = DataHelper.getFirstCard();
    DataHelper.Cards secondCard = DataHelper.getSecondCard();


    @BeforeAll
    static void skipToDashboard() {
        open("http://localhost:9999");
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2(){
        val dashboardPage = new DashboardPage();

        //Перевод 500 рублей с первой карты на вторую
        int firstCardStartBalance = dashboardPage.getFirstCardBalance();
        int secondCardStartBalance = dashboardPage.getSecondCardBalance();
        val refillPage1 = dashboardPage.refillFirstCard();
        refillPage1.setForm(500, DataHelper.getSecondCard().getCardNumber());
        assertEquals(Math.abs(dashboardPage.getFirstCardBalance()), Math.abs(firstCardStartBalance + 500));
        assertEquals(Math.abs(dashboardPage.getSecondCardBalance()), Math.abs(secondCardStartBalance - 500));

        //Перевод 1000 рублей со второй на первую
        firstCardStartBalance = dashboardPage.getFirstCardBalance();
        secondCardStartBalance = dashboardPage.getSecondCardBalance();
        val refillPage2 = dashboardPage.refillSecondCard();
        refillPage2.setForm(1000, DataHelper.getFirstCard().getCardNumber());
        assertEquals(Math.abs(dashboardPage.getFirstCardBalance()), Math.abs(firstCardStartBalance - 1000));
        assertEquals(Math.abs(dashboardPage.getSecondCardBalance()), Math.abs(secondCardStartBalance + 1000));
    }


    @AfterEach
    void restoreBalance() {
        val dashboardPage = new DashboardPage();
        dashboardPage.restoreBalanceTo10k();
    }
}

