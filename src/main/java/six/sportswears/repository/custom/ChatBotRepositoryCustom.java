package six.sportswears.repository.custom;

import org.springframework.stereotype.Repository;
import six.sportswears.payload.response.chatbot.OrderStatusCountResponse;
import six.sportswears.payload.response.chatbot.response.ResponseTopSportswearDto;

import java.util.List;
@Repository
public interface ChatBotRepositoryCustom {
    List<OrderStatusCountResponse> getOrderStatusCountByMonth(String userName, Integer month, Integer year);
    List<ResponseTopSportswearDto> getTopSportswear(String userName, Integer month,String coupon, Integer limit);
}
