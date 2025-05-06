package com.naufalzaul.openmarketid.config.seeder;

import com.naufalzaul.openmarketid.constant.UserRole;
import com.naufalzaul.openmarketid.entity.Customer;
import com.naufalzaul.openmarketid.entity.User;
import com.naufalzaul.openmarketid.repository.CustomerRepository;
import com.naufalzaul.openmarketid.repository.UserRepository;
import com.naufalzaul.openmarketid.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Order(1)
public class AdministratorSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNING 1");
        if (userRepository.count() == 0 && customerRepository.count() == 0) {
            String name = "Administrator";
            User adminSeeder = User.builder()
                    .name(name)
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(UserRole.ADMIN))
                    .build();

            User user = userRepository.save(adminSeeder);

            Customer customerSeeder = Customer.builder()
                    .name(name)
                    .birthDate(LocalDate.of(1990, 1, 1))
                    .birthPlace("Indonesia")
                    .createdBy(name)
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .build();

            customerRepository.save(customerSeeder);

            System.out.println("User seeder executed.");
        }
    }
}
