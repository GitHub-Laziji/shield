
@RestController
@RequestMapping("/")
public class UserLoginController {

    @RequestMapping("ciphertext")
    public String ciphertext(HttpSession session){
        Ciphertext ciphertext = (Ciphertext)session.getAttribute("LOGIN_CIPHERTEXT_KEY");
        if(ciphertext==null){
            ciphertext = new Ciphertext();
        }
        session.setAttribute(LOGIN_CIPHERTEXT_KEY);
        return ciphertext.getPublicText();
    }

    @RequestMapping("login")
    public String login(HttpSession session,@RequestBody LoginForm form) {
        Ciphertext ciphertext = (Ciphertext)session.getAttribute("LOGIN_CIPHERTEXT_KEY");
        if(ciphertext==null){
            return "ERROR";
        }
        if (!ciphertext.getPrivateText().equals(form.getCiphertext())) {
            return "ERROR";
        }
        // TODO 

        return "SUCCESS";
    }
}