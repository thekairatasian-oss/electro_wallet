package electro_wallet.util;

import java.util.Random;

public class AccountNumberGenerator {

    private AccountNumberGenerator() {}

    public static String generate() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(9)+1);
        for (int i = 0; i < 15; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
