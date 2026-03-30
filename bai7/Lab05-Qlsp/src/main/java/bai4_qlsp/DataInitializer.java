package bai4_qlsp;

import bai4_qlsp.model.Category;
import bai4_qlsp.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}