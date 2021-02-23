package app.web;

import app.dto.FindUserDTO;
import app.dto.ForgotPasswordDTO;
import app.entities.User;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AccountController {


    @Autowired
    public AccountController() {

    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        return "forgotPassword";
    }

    @PostMapping("/sendMessagePassword")
    public String sendResetPassword(@Valid ForgotPasswordDTO forgotPasswordDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "forgotPassword";
        }
        return "sendEmailSucces";
    }
}
