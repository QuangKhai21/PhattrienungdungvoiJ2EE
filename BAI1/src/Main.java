import java.util.stream.Collectors;
import java.util.*;

// Nguyễn Trần Quang Khải  2280601376
public class Main {
    public static void main(String[] args) {

                List<Book> listBook = new ArrayList<>();
                Scanner sc = new Scanner(System.in);
                int chon = 0;

                do {
                    System.out.println("\n--- Quản Lý Sách ---");
                    System.out.println("1. Thêm sách");
                    System.out.println("2. Xóa sách");
                    System.out.println("3. Thay đổi sách");
                    System.out.println("4. Xuất danh sách");
                    System.out.println("5. Tìm sách có chữ 'Lập trình'");
                    System.out.println("6. Tìm tối đa K cuốn có giá <= P");
                    System.out.println("7. Tìm sách theo danh sách tác giả");
                    System.out.println("0. Thoát");
                    System.out.print("Chọn chức năng: ");
                    chon = Integer.parseInt(sc.nextLine());

                    switch (chon) {
                        case 1 -> {
                            Book b = new Book();
                            b.input();
                            listBook.add(b);
                        }
                        case 2 -> {
                            System.out.print("Nhập mã sách cần xóa: ");
                            int id = Integer.parseInt(sc.nextLine());
                            listBook.removeIf(b -> b.getId() == id); // Xóa trực tiếp bằng Lambda
                            System.out.println("Đã xóa!");
                        }
                        case 3 -> {
                            System.out.print("Nhập mã sách cần sửa (ID): ");
                            int id = Integer.parseInt(sc.nextLine());
                            listBook.stream()
                                    .filter(b -> b.getId() == id)
                                    .findFirst()
                                    .ifPresentOrElse(Book::input,
                                            () -> System.out.println("Không tìm thấy mã sách này!"));
                        }
                        case 4 -> listBook.forEach(Book::output); // Sử dụng Method Reference

                        case 5 -> {
                            // Tìm kiếm không phân biệt hoa thường
                            listBook.stream()
                                    .filter(b -> b.getTitle().toLowerCase().contains("lập trình"))
                                    .forEach(Book::output);
                        }

                        case 6 -> {
                            // Kết hợp filter và limit
                            System.out.print("Nhập số lượng tối đa (K): ");
                            int k = Integer.parseInt(sc.nextLine());
                            System.out.print("Nhập mức giá tối đa (P): ");
                            double p = Double.parseDouble(sc.nextLine());

                            listBook.stream()
                                    .filter(b -> b.getPrice() <= p)
                                    .limit(k)
                                    .forEach(Book::output);
                        }

                        case 7 -> {
                            // Tìm kiếm theo danh sách tác giả
                            System.out.print("Nhập các tác giả (ví dụ: Nam Cao, Tô Hoài): ");
                            String inputStr = sc.nextLine();
                            // Tách chuỗi và đưa vào Set để tìm kiếm nhanh hơn
                            Set<String> authors = Arrays.stream(inputStr.split(",\\s*"))
                                    .collect(Collectors.toSet());

                            listBook.stream()
                                    .filter(b -> authors.contains(b.getAuthor()))
                                    .forEach(Book::output);
                        }

                    }
                } while (chon != 0);
            }
        }