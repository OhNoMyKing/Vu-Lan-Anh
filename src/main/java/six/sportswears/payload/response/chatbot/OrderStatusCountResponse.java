package six.sportswears.payload.response.chatbot;

public class OrderStatusCountResponse {
    private Integer orderStatus;
    private Long count; // Thay đổi từ Integer sang Long
    private String statusMessage;
    // Getters and Setters

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getCount() {  // Thay đổi kiểu trả về là Long
        return count;
    }

    public void setCount(Long count) {  // Thay đổi kiểu tham số là Long
        this.count = count;
    }
}


