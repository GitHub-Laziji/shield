package test.laziji.shield;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class HashCaptcha {
    private String privateKey;
    private String value;

    public HashCaptcha() {
        this(10000);
    }

    public HashCaptcha(int range) {
        Random random = new Random();
        String randomString = DigestUtils.md5Hex(random.nextInt() + "");
        int randomNumber = random.nextInt(range);
        value = randomString + DigestUtils.md5Hex(randomString + randomNumber);
        privateKey = DigestUtils.md5Hex(randomNumber + randomString);
    }

    public boolean verification(String value) {
        return value != null && value.equals(privateKey);
    }

    public String getValue() {
        return value;
    }
}
