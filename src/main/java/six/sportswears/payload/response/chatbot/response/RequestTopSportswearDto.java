package six.sportswears.payload.response.chatbot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTopSportswearDto {
    private String userId;
    private int quantity;
    private String userName;
    private int month;
    private String coupon;
}
