package fr.paymybuddy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Define security filter chain for HTTP requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/register").permitAll(); // Permit access to registration page
                    auth.requestMatchers("/*").authenticated(); // Require authentication for other endpoints
                }).formLogin(form -> form.loginPage("/login") // Configure custom login page
                        .loginProcessingUrl("/login") // Set login processing URL
                        .defaultSuccessUrl("/home", true) // Redirect to home page after successful login
                        .permitAll())
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Configure logout URL
                                .permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .key("6WdhtY6PpxLdA2jzKfGJzZWeoF2A2QZl4OzBbpiqGpNnN1sk+8KqVYyPrJW/VysWm0o8M2Nq2+fPpD7t4mUmw==\n") // Set remember-me key
                        .tokenValiditySeconds(86400) // Set token validity period
                        .tokenRepository(persistentTokenRepository()) // Configure token repository
                        .userDetailsService(customUserDetailsService)) // Set custom user details service
                .build();
    }

    // Define an in-memory user details manager
    @Bean
    UserDetailsService users(){
        return new InMemoryUserDetailsManager();
    }

    // Define BCrypt password encoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Configure authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    // Configure persistent token repository using JDBC
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
