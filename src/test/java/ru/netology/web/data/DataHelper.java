package ru.netology.web.data;

import lombok.Value;
import ru.netology.web.page.DashboardPage;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }

  @Value
  public static class Cards{
    private String cardId;
    private String cardNumber;
  }
  public static Cards getFirstCard(){return new Cards("92df3f1c-a033-48e6-8390-206f6b1f56c0","5559 0000 0000 0001");}
  public static Cards getSecondCard(){return new Cards("0f3f5c2a-249e-4c3d-8287-09f7a039391d","5559 0000 0000 0002");}
  public static Cards getInvalidCard(){return new Cards("0f3f5c2a-249e-4c3d-8287-09f7a039391d","5559 0000 0000 0003");}
  public static String getCardLastNumbers (String cardNumber){
    return cardNumber.substring(cardNumber.length()-4,cardNumber.length());

  }
}
