package six.sportswears.payload.response.chatbot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTopSportswearDto {
    private Long sportswearId;
    private String sportswearName;
    private String sportswearImage;
    private Long totalQuantity;
    private Double totalRevenue;
}
