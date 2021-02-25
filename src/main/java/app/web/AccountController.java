package app.web;

import app.dto.FindUserDTO;
import app.dto.ForgotPasswordDTO;
import app.entities.PasswordResetToken;
import app.entities.User;
import app.repositories.PasswordResetTokenRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AccountController {

    private final UserRepository userRepository;
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public AccountController(UserRepository userRepository,
                             PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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
        //Send message email
        User user = userRepository.findByEmail(forgotPasswordDTO.getEmail());
        if(user==null)
        {
            model.addAttribute("error", "Дана пошта відсутня");
            return "forgotPassword";
        }
        PasswordResetToken passwordResetToken;
        String token = UUID.randomUUID().toString();
        Optional<PasswordResetToken> optional = passwordResetTokenRepository.findById(user.getId());

        if (optional.isPresent()) {
            passwordResetToken = optional.get();
            passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis()+PasswordResetToken.getEXPIRATION()));
            passwordResetToken.setToken(token);
        }
        else
            passwordResetToken=new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
//        userService.createPasswordResetTokenForUser(user, token);

//        mailSender.send(constructResetTokenEmail(getAppUrl(request),
//                request.getLocale(), token, user));

        return "sendEmailSucces";
    }
}
