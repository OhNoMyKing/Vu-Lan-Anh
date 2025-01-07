package six.sportswears.payload.response.coupon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CouponCaculationResponse {
    @JsonProperty("result")
    private Double result;

    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("couponId")
    private Long couponId;
}
