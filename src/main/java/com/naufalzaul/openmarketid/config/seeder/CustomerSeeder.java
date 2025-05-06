package com.naufalzaul.openmarketid.config.seeder;

import com.naufalzaul.openmarketid.constant.UserRole;
import com.naufalzaul.openmarketid.entity.Customer;
import com.naufalzaul.openmarketid.entity.User;
import com.naufalzaul.openmarketid.repository.CustomerRepository;
import com.naufalzaul.openmarketid.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Order(2)
public class CustomerSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNING 2");
        if (customerRepository.count() == 1) {
            LocalDate birthDate1 = LocalDate.of(1990, 1, 1);
            LocalDate birthDate2 = LocalDate.of(1992, 3, 20);
            LocalDate birthDate3 = LocalDate.of(1985, 6, 15);

            List<Customer> customers = List.of(
                    createCustomer(
                            "user1",
                            "Andi Wijaya",
                            "Jakarta",
                            birthDate1
                    ),
                    createCustomer(
                            "user2",
                            "Budi Santoso",
                            "Surabaya",
                            birthDate3
                    ),
                    createCustomer(
                            "user3",
                            "Citra Dewi",
                            "Bandung",
                            birthDate2
                    ),
                    createCustomer(
                            "user4",
                            "Citra Dewi",
                            "Bandung",
                            birthDate2
                    )
            );

            customerRepository.saveAll(customers);
            System.out.println("Customer data seeded.");
        } else {
            System.out.println("Customer data already exists, skipping seed.");
        }
    }

    private Customer createCustomer(String id, String name, String birthplace, LocalDate birthdate) {
        String random = name.replace(" ", "").toLowerCase() + id;
        User userSeeder = User.builder()
                .name(name)
                .email( random+ "@gmail.com")
                .password(passwordEncoder.encode(random))
                .roles(Set.of(UserRole.USER))
                .build();

        User user = userRepository.save(userSeeder);

        return Customer.builder()
                .name(name)
                .birthPlace(birthplace)
                .birthDate(birthdate)
                .createdBy("Administrator")
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
