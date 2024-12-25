package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import six.sportswears.model.Orders;
import six.sportswears.payload.response.ListOrderResponse;
import six.sportswears.payload.response.OrdersResponse;


import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ListOrdersToListOrderResponse {
    OrdersToOrdersResponse ordersToOrdersResponse;
    public ListOrderResponse toListOrderResponse(List<Orders> ordersList, Long noPage) {
        ListOrderResponse listOrderResponse = new ListOrderResponse();
        List<OrdersResponse> ordersResponseList = new ArrayList<>();
        for(Orders x : ordersList) {
            ordersResponseList.add(ordersToOrdersResponse.toOrdersResponse(x));
        }

        Pageable pageable = PageRequest.of((int)(noPage -1L), 1);
        long start = pageable.getOffset();
        long end;
        if(start + pageable.getPageSize() > ordersResponseList.size()) {
            end = (long) ordersResponseList.size();
        } else {
            end = start + pageable.getPageSize();
        }
        ordersResponseList = ordersResponseList.subList(Math.toIntExact(start), Math.toIntExact(end));


        Page<OrdersResponse> ordersResponsePage = new PageImpl<>(ordersResponseList, pageable, ordersList.size());

        listOrderResponse.setOrdersResponseList(ordersResponseList);
        listOrderResponse.setCurrentPage(noPage);
        listOrderResponse.setTotalPage((long) ordersResponsePage.getTotalPages());
        return  listOrderResponse;
    }

}
