import java.util.Scanner;
public class Book {

        private int id;
        private String title;
        private String author;
        private double price;

        // Constructors
        public Book() {}

        public Book(int id, String title, String author, double price) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.price = price;
        }

        // Getter và Setter
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        // Hàm nhập thông tin
        public void input() {
            Scanner sc = new Scanner(System.in); // Lưu ý xử lý trôi lệnh nếu cần
            System.out.print("Nhập mã sách: ");
            this.id = Integer.parseInt(sc.nextLine());
            System.out.print("Nhập tên sách: ");
            this.title = sc.nextLine();
            System.out.print("Nhập tác giả: ");
            this.author = sc.nextLine();
            System.out.print("Nhập đơn giá: ");
            this.price = Double.parseDouble(sc.nextLine());
        }

        // Hàm xuất thông tin (Sử dụng Text Block và formatted)
        public void output() {
            String msg = """
                BOOK [ID: %d, Tên: %s, Tác giả: %s, Giá: %.2f]
                """.formatted(id, title, author, price);
            System.out.print(msg);
        }

}
