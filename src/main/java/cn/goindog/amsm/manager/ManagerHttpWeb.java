package cn.goindog.amsm.manager;

import cn.goindog.amsm.ServerFields;
import cn.goindog.amsm.utils.AESUtil;
import cn.goindog.amsm.utils.PropertiesUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ManagerHttpWeb {
    @RequestMapping("/")
    public String index(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Cookie[] cookies = req.getCookies();
        String username = "default";
        String password_encoded = "default";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "username": username = cookie.getValue(); break;
                    case "password": password_encoded = cookie.getValue(); break;
                }
            }
            String password_decoded = AESUtil.decrypt(PropertiesUtils.password_encode_key, password_encoded);
            if (ServerFields.userDetailUtils.verify(username, password_decoded)) return "index.html";
            else return "redirect:/login";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/verify")
    public String verify(HttpServletResponse response, @RequestParam String username, @RequestParam String password) throws Exception {
        if (ServerFields.userDetailUtils.verify(username, password)) {
            Cookie verified = new Cookie("verified", "true");
            Cookie user_name = new Cookie("username", username);
            Cookie passwd = new Cookie("password", AESUtil.encrypt(PropertiesUtils.password_encode_key, password));

            response.addCookie(verified);
            response.addCookie(user_name);
            response.addCookie(passwd);
            return "redirect:/";
        } else {
            return "redirect:/login?verify_failed";
        }
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String username = "default";
            String password_encoded = "default";
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "username": username = cookie.getValue(); break;
                    case "password": password_encoded = cookie.getValue(); break;
                }
            }
            String password_decoded = AESUtil.decrypt(PropertiesUtils.password_encode_key, password_encoded);
            if (ServerFields.userDetailUtils.verify(username, password_decoded)) return "redirect:/";
            else return "login/index.html";
        } else {
            return "login/index.html";
        }
    }
}
