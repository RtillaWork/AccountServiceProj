package account.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("passwordEncoderImpl")
public class PasswordEncoderImpl {
    public static final int BCRYPT_STRENGTH = 13;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
//        return NoOpPasswordEncoder.getInstance();
    }
}