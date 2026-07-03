package com.example.test2.controller;

import com.example.test2.entity.UserAccount;
import com.example.test2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(Principal principal,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "registered", required = false) String registered,
                            Model model) {
        if (principal != null) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", "ユーザー名またはパスワードが違います。");
        }
        if (logout != null) {
            model.addAttribute("message", "ログアウトしました。");
        }
        if (registered != null) {
            model.addAttribute("message", "登録が完了しました。ログインしてください。");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Principal principal, Model model) {
        if (principal != null) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "ユーザー名を入力してください。");
            return "register";
        }
        if (password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "パスワードを入力してください。");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "パスワードと確認用パスワードが一致しません。");
            return "register";
        }
        if (userRepository.findByUsername(username.trim()).isPresent()) {
            model.addAttribute("error", "そのユーザー名は既に使用されています。");
            return "register";
        }

        UserAccount user = new UserAccount();
        user.setUsername(username.trim());
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles("ROLE_USER");
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
