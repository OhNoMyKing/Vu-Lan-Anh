package six.sportswears.payload.response.chatbot.response;

public class CheckoutResponse {
    private String paymentUrl;

    public CheckoutResponse(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    // Getter
    public String getPaymentUrl() {
        return paymentUrl;
    }
}
