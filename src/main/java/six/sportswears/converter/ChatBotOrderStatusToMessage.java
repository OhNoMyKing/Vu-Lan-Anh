package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import six.sportswears.payload.response.chatbot.OrderStatusCountResponse;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatBotOrderStatusToMessage {
    public List<OrderStatusCountResponse> convertToStatusMessages(List<OrderStatusCountResponse> responses) {
        List<OrderStatusCountResponse> convertedResponses = new ArrayList<>();
        for (OrderStatusCountResponse response : responses) {
            OrderStatusCountResponse updatedResponse = convertToStatusMessage(response);
            convertedResponses.add(updatedResponse);
        }
        return convertedResponses;
    }

    private OrderStatusCountResponse convertToStatusMessage(OrderStatusCountResponse response) {
        String statusMessage = getOrderStatusMessage(response.getOrderStatus());
        response.setStatusMessage(statusMessage);
        return response;
    }

    private String getOrderStatusMessage(Integer orderStatus) {
        if (orderStatus == 1) {
            return "Đơn hàng đã được tiếp nhận";
        } else if (orderStatus == 2) {
            return "Đơn hàng đã được đóng gói";
        } else if (orderStatus == 3) {
            return "Đơn hàng đang được giao";
        } else if (orderStatus == 4) {
            return "Đơn hàng đã giao thành công";
        } else if (orderStatus == 5) {
            return "Đơn hàng đã giao và đã được đánh giá";
        } else {
            return "Trạng thái không xác định";
        }
    }
}
