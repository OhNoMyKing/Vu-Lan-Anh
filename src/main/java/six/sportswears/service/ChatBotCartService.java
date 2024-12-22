package six.sportswears.service;

import six.sportswears.payload.response.ChatBotCartResponse;

public interface ChatBotCartService {
    ChatBotCartResponse getCartChatBot(Long userId);
}
