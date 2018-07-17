
public class Ciphertext {

    private int range;
    private String randomString;
    private int randomNumber;
    private String privateText;
    private String privateKey;

    public Ciphertext(){
        this(10000);
    }

    public Ciphertext(int range){
        this.range =range;
        init();
    }

    public String getPrivateText() {
        return privateText;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    private void init(){
        randomString = Utils.getUUID();
        randomNumber = Utils.randomInt(range);
        privateText =  randomString + Utils.md5(randomString+randomNumber);
        privateKey = Utils.md5(randomNumber+randomString);
    }
}