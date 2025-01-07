package six.sportswears.service;

import six.sportswears.payload.response.chatbot.response.RequestTopSportswearDto;
import six.sportswears.payload.response.chatbot.response.ResponseTopSportswearDto;

import java.util.List;

public interface ChatBotSportswearService {
    List<ResponseTopSportswearDto> getTopSportswear(RequestTopSportswearDto requestTopSportswearDto);
}
