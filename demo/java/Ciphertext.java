
public class Ciphertext {

    private int range;
    private String randomString;
    private int randomNumber;
    private String publicText;
    private String privateText;

    public Ciphertext(){
        this(10000);
    }

    public Ciphertext(int range){
        this.range =range;
        init();
    }

    public String getPublicText() {
        return publicText;
    }

    public String getPrivateText() {
        return privateText;
    }

    private void init(){
        randomString = Utils.getUUID();
        randomNumber = Utils.randomInt(range);
        publicText =  randomString + Utils.md5(randomString+randomNumber);
        privateText = Utils.md5(randomNumber+randomString);
    }
}