package com.naufalzaul.openmarketid.config.seeder;

import com.naufalzaul.openmarketid.entity.Tax;
import com.naufalzaul.openmarketid.repository.TaxRepository;
import com.naufalzaul.openmarketid.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(3)
public class TaxSeeder implements CommandLineRunner {

    private final TaxRepository taxRepository;
    private final TaxService taxService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNING 3");
        if (taxRepository.count() == 0) {
            List<Tax> taxes = List.of(
                    new Tax(
                            null,
                            "PPN",
                            0.10,
                            true,
                            List.of()),
                    new Tax(
                            null,
                            "PPH",
                            2.5,
                            true,
                            List.of()),
                    new Tax(
                            null,
                            "Jasa",
                            5.0,
                            true,
                            List.of()),
                    new Tax(
                            null,
                            "Pajak Barang Mewah",
                            15.0,
                            true,
                            List.of()),
                    new Tax(
                            null,
                            "Bebas Pajak",
                            0.0,
                            false,
                            List.of())
            );

            taxRepository.saveAll(taxes);
            System.out.println("Tax data seeded.");
        } else {
            System.out.println("Tax data already exists, skipping seed.");
        }
    }
}
