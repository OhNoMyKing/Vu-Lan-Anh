package six.sportswears.payload.response.chatbot;

public class CartRequest {

    private String size;
    private Long quantity;
    private Long userId;    // Thêm userId
    private int productId; // Thêm productId

    // Getter và Setter cho size
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    // Getter và Setter cho quantity
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    // Getter và Setter cho userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter và Setter cho productId
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}

