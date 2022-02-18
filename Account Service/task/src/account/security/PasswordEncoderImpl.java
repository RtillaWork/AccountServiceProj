package account.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl{

    @Bean
    public PasswordEncoder PasswordEncoderImpl() {
        return new BCryptPasswordEncoder();
    }
}
