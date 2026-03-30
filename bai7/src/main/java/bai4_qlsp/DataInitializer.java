package bai4_qlsp;

import bai4_qlsp.model.Account;
import bai4_qlsp.model.Category;
import bai4_qlsp.model.Role;
import bai4_qlsp.repository.AccountRepository;
import bai4_qlsp.repository.CategoryRepository;
import bai4_qlsp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(CategoryRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Category(0, "Điện thoại"));
                repository.save(new Category(0, "Laptop"));
                repository.save(new Category(0, "Máy tính bảng"));
                System.out.println("Đã khởi tạo danh mục mẫu vào Database.");
            }
        };
    }

    @Bean
    CommandLineRunner initAccountAndRole(AccountRepository accountRepository,
                                        RoleRepository roleRepository,
                                        PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role roleAdmin = new Role();
                roleAdmin.setName("ROLE_ADMIN");
                roleRepository.save(roleAdmin);

                Role roleUser = new Role();
                roleUser.setName("ROLE_USER");
                roleRepository.save(roleUser);

                if (accountRepository.count() == 0) {
                    Account admin = new Account();
                    admin.setLogin_name("admin");
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    Set<Role> adminRoles = new HashSet<>();
                    adminRoles.add(roleAdmin);
                    admin.setRoles(adminRoles);
                    accountRepository.save(admin);

                    Account user = new Account();
                    user.setLogin_name("user");
                    user.setPassword(passwordEncoder.encode("123456"));
                    Set<Role> userRoles = new HashSet<>();
                    userRoles.add(roleUser);
                    user.setRoles(userRoles);
                    accountRepository.save(user);

                    System.out.println("Đã khởi tạo tài khoản: admin/admin123, user/123456");
                }
            }
        };
    }
}