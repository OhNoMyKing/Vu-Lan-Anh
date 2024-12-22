package six.sportswears.payload.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ChatBotCartResponse {
    private List<CartItemResponse> cartItemResponses;
    private Double totalAmount;
    private Long totalQuantity;

    private String userName;
}
