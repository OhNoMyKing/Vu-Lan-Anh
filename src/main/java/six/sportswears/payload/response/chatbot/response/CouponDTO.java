package six.sportswears.payload.response.chatbot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private String code;
    private  String description;
}
