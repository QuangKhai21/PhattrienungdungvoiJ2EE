package bai4_qlsp; // Dòng này phải khớp với thư mục chứa file

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:src/main/resources/static/images/") // Cho phép đọc trực tiếp từ src khi dev
                .addResourceLocations("file:target/classes/static/images/");  // Và đọc từ target
    }
}