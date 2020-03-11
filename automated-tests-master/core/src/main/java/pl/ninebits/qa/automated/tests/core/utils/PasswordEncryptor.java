package pl.ninebits.qa.automated.tests.core.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class PasswordEncryptor {

  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("invocation programName <securityToken> <plainTextPass>");
    }

    String securityToken = args[0];
    String plainTextPass = args[1];

    BasicTextEncryptor encryptor = new BasicTextEncryptor();
    encryptor.setPassword(securityToken);
    final String encrypted = encryptor.encrypt(plainTextPass);
    System.out.println("ENC(" + encrypted + ")");
    System.out.println(encryptor.decrypt(encrypted));
  }
}
