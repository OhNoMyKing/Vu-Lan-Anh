package six.sportswears.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.request.CreateSportswearRequest;
import six.sportswears.payload.request.SearchRequest;
import six.sportswears.payload.response.*;
import six.sportswears.payload.response.chatbot.SportswearResponseDetails;
import six.sportswears.payload.response.sportswear.ListSportswearResponse;
import six.sportswears.payload.response.sportswear.ListSportswearRevenueResponseInAMonth;
import six.sportswears.payload.response.sportswear.SportswearResponse;
import six.sportswears.payload.response.sportswear.SportswearRevenueResponse;

import java.util.List;

@Service
@Transactional
public interface SportswearService {
    ResponseEntity<MessageResponse> createSportswearByAdmin(CreateSportswearRequest createSportswearRequest);

    ListSportswearResponse getAllSportswearDisplayForCustomer(SearchRequest searchRequest);
//    List<SportswearResponse> getAllSportswearForCustomerBySearching(SearchRequest searchRequest);

   ResponseEntity<SportswearResponse> getSportswearByID(Long id);
//    List<SportswearResponse> getRelatedSportswearDisplay(Long currentProductId);

    ResponseEntity<MessageResponse> deleteSportswearByID(Long id);

    //admin
    ResponseEntity<ListSportswearResponse> getAllSportswearDisplayForAdmin(SearchRequest searchRequest);
    ResponseEntity<SportswearRevenueResponse> getSportswearRevenueResponseByIDAndTime(Long id, String time);
    ResponseEntity<ListSportswearRevenueResponseInAMonth> getListSportswearRevenueResponseInAMonth(Long id, Integer year);
    ResponseEntity<ListSportswearRevenueResponseInAMonth> getAllSportswearRevenueResponse(Integer time);
    ResponseEntity<ListLabelAndValue> getListLabelAndValue(String month, Integer year);

    Long getProductsCount();
    List<SportswearResponseDetails> getRemainingProducts();
    List<Sportswear> findProductByInfo(Long shirtNumber,String teamName,String playerName);
}
