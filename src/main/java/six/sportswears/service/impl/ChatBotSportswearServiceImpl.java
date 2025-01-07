package six.sportswears.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import six.sportswears.payload.response.chatbot.response.RequestTopSportswearDto;
import six.sportswears.payload.response.chatbot.response.ResponseTopSportswearDto;
import six.sportswears.repository.custom.impl.ChatBotOrderRepositoryCustomImpl;
import six.sportswears.service.ChatBotSportswearService;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatBotSportswearServiceImpl implements ChatBotSportswearService {
    private final ChatBotOrderRepositoryCustomImpl chatBotOrderRepositoryCustom;
    @Override
    public List<ResponseTopSportswearDto> getTopSportswear(RequestTopSportswearDto requestTopSportswearDto) {
        return chatBotOrderRepositoryCustom.getTopSportswear(
                requestTopSportswearDto.getUserName(),
                requestTopSportswearDto.getMonth(),
                requestTopSportswearDto.getCoupon(),
                requestTopSportswearDto.getQuantity()
        );
    }
}
