package six.sportswears.service;

import six.sportswears.payload.response.chatbot.OrderStatusCountResponse;
import six.sportswears.payload.response.chatbot.response.OrderStatusCountRequest;

import java.util.List;

public interface ChatBotOrderService {
    List<OrderStatusCountResponse> getOrderStatusCountByMonth(OrderStatusCountRequest request);
}
