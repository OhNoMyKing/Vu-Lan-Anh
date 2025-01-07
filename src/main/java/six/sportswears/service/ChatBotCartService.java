package six.sportswears.service;

import six.sportswears.payload.response.chatbot.CartRequest;
import six.sportswears.payload.response.chatbot.ChatBotCartResponse;

public interface ChatBotCartService {
    ChatBotCartResponse getCartChatBot(Long userId);
    String addToCartChatBot(CartRequest cartRequest);
    Double caculateTotalCartChatBot(Long userId);
}
