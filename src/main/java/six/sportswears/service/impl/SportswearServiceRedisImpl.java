package six.sportswears.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import six.sportswears.payload.request.SearchRequest;
import six.sportswears.payload.response.sportswear.ListSportswearResponse;
import six.sportswears.service.SportswearServiceRedis;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class SportswearServiceRedisImpl implements SportswearServiceRedis {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    SimpMessagingTemplate messagingTemplate;
    //
    private String getKeyFrom(SearchRequest searchRequest){
        Long noPage = searchRequest.getNoPage();
        String keyword = searchRequest.getKey();
        String categoryName = searchRequest.getCategoryName();
        String key = String.format("all_products:%d:%s:%s",noPage ,keyword,categoryName);
        return key;
    }
    @Override
    public ListSportswearResponse getAllSportswearDisplayForCustomer(SearchRequest searchRequest) throws JsonProcessingException {
        String keySearch = searchRequest.getKey();
        Long noPage = searchRequest.getNoPage();
        String categoryName = searchRequest.getCategoryName();
        //tao key cho request
        String key = this.getKeyFrom(searchRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        ListSportswearResponse listSportswearResponse =
                json != null ? redisObjectMapper.readValue(json, ListSportswearResponse.class) : null;
        return listSportswearResponse;
    }

    @Override
    public void clear(SearchRequest searchRequest) {
        String key = this.getKeyFrom(searchRequest);
        redisTemplate.delete(key);
    }
    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        messagingTemplate.convertAndSend("/topic/sportswearUpdated","Product updated");
    }
    @Override
    public void saveAllProducts(SearchRequest searchRequest, ListSportswearResponse listSportswearResponse) throws JsonProcessingException {
        String key = this.getKeyFrom(searchRequest);
        String json = redisObjectMapper.writeValueAsString(listSportswearResponse);
        redisTemplate.opsForValue().set(key,json);
    }
}
