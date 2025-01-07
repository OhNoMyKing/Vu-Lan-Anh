package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import six.sportswears.payload.response.order.OrdersResponse;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListOrderResponse {
    private List<OrdersResponse> ordersResponseList;
    private Long currentPage;
    private Long totalPage;
}
