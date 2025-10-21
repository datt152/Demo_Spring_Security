package iuh.fit;

import iuh.fit.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("123"))
                .roles(Role.ADMIN.name())
                .build();

        UserDetails customer = User.builder()
                .username("customer")
                .password(passwordEncoder().encode("111"))
                .roles(Role.CUSTOMER.name())
                .build();

        return new InMemoryUserDetailsManager(admin, customer);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public pages, static resources
                        .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**").permitAll()

                        // Quản trị sản phẩm: chỉ ADMIN
                        .requestMatchers("/products/new", "/products/edit/**", "/products/delete/**").hasRole("ADMIN")

                        // Danh sách + chi tiết sản phẩm: cho khách (GUEST) truy cập
                        .requestMatchers("/products", "/products/*").permitAll()

                        // Đơn hàng: CUSTOMER hoặc ADMIN
                        .requestMatchers("/orders/**").hasAnyRole("CUSTOMER","ADMIN")

                        // Các request còn lại cần đăng nhập
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")          // Trang login custom
                        .defaultSuccessUrl("/home")   // Sau login quay về home
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                // Cấp quyền mặc định cho anonymous là ROLE_GUEST
                .anonymous(anon -> anon.authorities("ROLE_GUEST"));

        return http.build();
    }
}