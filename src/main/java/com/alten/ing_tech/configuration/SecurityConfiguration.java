package com.alten.ing_tech.configuration;


import com.alten.ing_tech.common.errors.CustomAuthenticationFailureHandler;
import com.alten.ing_tech.common.utils.Constants;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/api-hub","/swagger-ui/**","/swagger-ui.html").permitAll()
                        .requestMatchers("/add-product").hasAnyRole(Constants.SUPER_ADMIN, Constants.ADMIN)
                        .requestMatchers("/change-price").hasRole(Constants.SUPER_ADMIN)
                        .requestMatchers("/delete-product/**").hasRole(Constants.SUPER_ADMIN)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui.html", true)
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .permitAll()
                )
                .logout(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails defaultUser = User.builder()
                .username("superAdmin")
                .password(passwordEncoder().encode("superAdmin"))
                .roles(Constants.SUPER_ADMIN)
                .build();

        UserDetails permittedUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(Constants.ADMIN)
                .build();

        UserDetails intruder = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles(Constants.USER)
                .build();

        return new InMemoryUserDetailsManager(defaultUser, permittedUser, intruder);
    }

}
