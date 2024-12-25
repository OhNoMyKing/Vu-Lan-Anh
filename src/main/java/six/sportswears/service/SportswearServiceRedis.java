package six.sportswears.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import six.sportswears.payload.request.SearchRequest;
import six.sportswears.payload.response.ListSportswearResponse;

public interface SportswearServiceRedis {
    ListSportswearResponse getAllSportswearDisplayForCustomer(SearchRequest searchRequest) throws JsonProcessingException;
    void clear(SearchRequest searchRequest);
    void clear();
    void saveAllProducts(SearchRequest searchRequest, ListSportswearResponse listSportswearResponse) throws JsonProcessingException;
}
