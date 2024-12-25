package six.sportswears.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import six.sportswears.security.jwt.AuthEntryPointJwt;
import six.sportswears.security.jwt.AuthTokenFilter;
import six.sportswears.security.services.UserDetailsServiceImpl;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecurityConfig {
    UserDetailsServiceImpl userDetailsService;
    AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  // Tắt CSRF nếu bạn không sử dụng session-based authentication
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unauthorizedHandler))  // Định nghĩa hành động khi không xác thực
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Đảm bảo không sử dụng session (thường dùng với JWT)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/view/manager").hasRole("ADMIN")
                        .requestMatchers("/api/customer/cart/**").hasAnyRole("CUSTOMER", "MODERATOR", "ADMIN")
                        .requestMatchers("/api/view/home").permitAll()
                        .requestMatchers("/api/view/login").permitAll()
                        .requestMatchers("/api/web/search/**").permitAll()
                        .requestMatchers("/api/web/detail/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/website/assets/**").permitAll()
                        .requestMatchers("/website/search/**").permitAll()
                        .requestMatchers("/website/detail/**").permitAll()
                        .requestMatchers("/website/menu/**").permitAll()
                        .requestMatchers("/website/token/**").permitAll()
                        .requestMatchers("/website/custom/**").permitAll()
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/money/**").permitAll()
                        .anyRequest().authenticated());


        // Cung cấp authentication provider (có thể là JWT hoặc form login)
        http.authenticationProvider(authenticationProvider());

        // Thêm JWT filter trước filter UsernamePasswordAuthenticationFilter (hoặc các filter khác)
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
