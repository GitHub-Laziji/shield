package test.laziji.shield;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

    private static final String CAPTCHA_KEY = "CAPTCHA_KEY";

    @RequestMapping()
    public String index(){
        return "login";
    }

    @ResponseBody
    @RequestMapping("captcha")
    public String captcha(HttpSession session){
        HashCaptcha captcha = (HashCaptcha)session.getAttribute(CAPTCHA_KEY);
        if(captcha==null){
            captcha = new HashCaptcha();
        }
        session.setAttribute(CAPTCHA_KEY,captcha);
        return captcha.getValue();
    }

    @ResponseBody
    @RequestMapping("submit")
    public String submit(HttpSession session,LoginForm form) {
        HashCaptcha captcha = (HashCaptcha)session.getAttribute(CAPTCHA_KEY);
        if(captcha==null){
            return "ERROR";
        }
        session.removeAttribute(CAPTCHA_KEY);
        if (!captcha.verification(form.getCaptcha())) {
            return "ERROR";
        }
        return "SUCCESS";
    }
}
