package bai4_qlsp.model;

import lombok.*;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int productId;
    private String name;
    private String image;
    private long price;
    private int quantity;

    public long getTotalPrice() {
        return price * quantity;
    }
}
