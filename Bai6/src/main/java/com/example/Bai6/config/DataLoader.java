package com.example.bai5_tongvutanphat_2280602321.config;

import com.example.bai5_tongvutanphat_2280602321.model.Account;
import com.example.bai5_tongvutanphat_2280602321.model.Role;
import com.example.bai5_tongvutanphat_2280602321.repository.AccountRepository;
import com.example.bai5_tongvutanphat_2280602321.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (accountRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
            Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();

            Account admin = new Account();
            admin.setLogin_name("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoles(new HashSet<>(Set.of(adminRole)));
            accountRepository.save(admin);

            Account user1 = new Account();
            user1.setLogin_name("user1");
            user1.setPassword(passwordEncoder.encode("123456"));
            user1.setRoles(new HashSet<>(Set.of(userRole)));
            accountRepository.save(user1);

            System.out.println(">>> Đã tạo tài khoản: admin/123456 (ADMIN), user1/123456 (USER)");
        } else {

            accountRepository.findByLoginName("admin").ifPresent(acc -> {
                acc.setPassword(passwordEncoder.encode("123456"));
                accountRepository.save(acc);
                System.out.println(">>> Đã cập nhật mật khẩu admin/123456");
            });
            accountRepository.findByLoginName("user1").ifPresent(acc -> {
                acc.setPassword(passwordEncoder.encode("123456"));
                accountRepository.save(acc);
            });
        }
    }
}
