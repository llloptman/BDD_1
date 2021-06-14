package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    DataHelper.Cards firstCard = DataHelper.getFirstCard();
    DataHelper.Cards secondCard = DataHelper.getSecondCard();
    DataHelper.Cards invalidCard = DataHelper.getInvalidCard();


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
    void shouldTransferMoneyBetweenOwnCards1_2(){
        val dashboardPage = new DashboardPage();

        //Перевод 500 рублей с первой карты на вторую
        int firstCardStartBalance = dashboardPage.getFirstCardBalance();
        int secondCardStartBalance = dashboardPage.getSecondCardBalance();
        val refillPage1 = dashboardPage.refillFirstCard();
        refillPage1.setForm(500, secondCard.getCardNumber());
        assertEquals(Math.abs(firstCardStartBalance + 500),Math.abs(dashboardPage.getFirstCardBalance()));
        assertEquals(Math.abs(secondCardStartBalance - 500),Math.abs(dashboardPage.getSecondCardBalance()));
    }
    @Test
    void shouldTransferMoneyBetweenOwnCards2_1(){
        val dashboardPage = new DashboardPage();

        //Перевод 1000 рублей со второй карты на первую
        int firstCardStartBalance = dashboardPage.getFirstCardBalance();
        int secondCardStartBalance = dashboardPage.getSecondCardBalance();
        val refillPage2 = dashboardPage.refillSecondCard();
        refillPage2.setForm(1000, firstCard.getCardNumber());
        assertEquals(Math.abs(firstCardStartBalance - 1000),Math.abs(dashboardPage.getFirstCardBalance()));
        assertEquals(Math.abs(secondCardStartBalance + 1000),Math.abs(dashboardPage.getSecondCardBalance()));
    }
    @Test
    void shouldntTransferFromOtherCard(){
        val dashboardPage = new DashboardPage();
        int firstCardStartBalance = dashboardPage.getFirstCardBalance();
        int secondCardStartBalance = dashboardPage.getSecondCardBalance();
        val refillPage2 = dashboardPage.refillSecondCard();
        refillPage2.setFormForErrors(1000, invalidCard.getCardNumber());
        refillPage2.getErrorMessage().shouldBe(Condition.visible, Duration.ofSeconds(5));
    }
    @Test
    void shouldntTransferMoreThenBalance(){
        val dashboardPage = new DashboardPage();
        int firstCardStartBalance = dashboardPage.getFirstCardBalance();
        int secondCardStartBalance = dashboardPage.getSecondCardBalance();
        val refillPage2 = dashboardPage.refillSecondCard();
        refillPage2.setForm(firstCardStartBalance + 1000, firstCard.getCardNumber());
        refillPage2.getErrorMessage().shouldBe(Condition.visible, Duration.ofSeconds(5));
    }
    @Test
    void shouldTransferMoneyWithKopek() {
        val dashboardPage = new DashboardPage();

        //Перевод 500 рублей с первой карты на вторую
        int firstCardStartBalance = dashboardPage.getFirstCardBalance();
        int secondCardStartBalance = dashboardPage.getSecondCardBalance();
        val refillPage1 = dashboardPage.refillFirstCard();
        refillPage1.setForm(500.51, secondCard.getCardNumber());
        assertEquals(Math.abs(firstCardStartBalance + 500.51),Math.abs(dashboardPage.getFirstCardBalance()));
        assertEquals(Math.abs(secondCardStartBalance - 500.51),Math.abs(dashboardPage.getSecondCardBalance()));
    }

    @AfterEach
    void restoreBalance() {
        open("http://localhost:9999");
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val dashboardPage = new DashboardPage();
        dashboardPage.restoreBalanceTo10k();
    }
}

