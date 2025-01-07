package six.sportswears.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import six.sportswears.payload.response.chatbot.OrderStatusCountResponse;
import six.sportswears.payload.response.chatbot.response.OrderStatusCountRequest;
import six.sportswears.repository.custom.ChatBotRepositoryCustom;
import six.sportswears.repository.custom.impl.ChatBotOrderRepositoryCustomImpl;
import six.sportswears.service.ChatBotOrderService;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatBotOrderServiceImpl implements ChatBotOrderService {
    private final ChatBotOrderRepositoryCustomImpl chatBotOrderRepositoryCustom;
    @Override
    public List<OrderStatusCountResponse> getOrderStatusCountByMonth(OrderStatusCountRequest request) {

        return chatBotOrderRepositoryCustom.getOrderStatusCountByMonth(
                request.getUserName(),
                request.getMonth(),
                request.getYear()
        );
    }
}
