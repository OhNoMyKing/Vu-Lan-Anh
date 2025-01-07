package six.sportswears.payload.response.vnpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PaymentResponse {
    private Long id;

    private String paymentMethod;

    private Integer paymentStatus;
}