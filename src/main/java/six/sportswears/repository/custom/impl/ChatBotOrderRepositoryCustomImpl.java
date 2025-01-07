package six.sportswears.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.response.chatbot.OrderStatusCountResponse;
import six.sportswears.payload.response.chatbot.response.ResponseTopSportswearDto;
import six.sportswears.repository.SportswearRepository;
import six.sportswears.repository.custom.ChatBotRepositoryCustom;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatBotOrderRepositoryCustomImpl implements ChatBotRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    private SportswearRepository sportswearRepository;
    @Override
    public List<OrderStatusCountResponse> getOrderStatusCountByMonth(String userName, Integer month, Integer year) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                " o.order_status AS orderStatus, "+
                " COUNT(*) AS count "  +
                "FROM orders o " +
                "INNER JOIN " +
                " user u ON o.user_id = u.id "+
                "WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();
        if(userName != null){
            sql.append(" AND u.user_name = ? ");
            params.add(userName);
        }
        if(month != null){
            sql.append(" AND MONTH(o.order_date) = ? ");
            params.add(month);
        }
        if(year != null){
            sql.append(" AND YEAR(o.order_date) = ? ");
            params.add(year);
        }
        sql.append(" GROUP BY o.order_status ");
        // In câu SQL với tham số đã được gắn vào
        String finalSql = sql.toString();
        for (int i = 0; i < params.size(); i++) {
            finalSql = finalSql.replaceFirst("\\?", "'" + params.get(i) + "'");
        }

        // In câu truy vấn cuối cùng đã thay thế tham số
        System.out.println("Final SQL Query: " + finalSql);
        Query query = entityManager.createNativeQuery(sql.toString());
        for(int i=0;i<params.size();i++){
            query.setParameter(i+1, params.get(i));
        }

        List<Object[]> resultList = query.getResultList();
        List<OrderStatusCountResponse> responseList = new ArrayList<>();

        for (Object[] result : resultList) {
            OrderStatusCountResponse response = new OrderStatusCountResponse();
            response.setOrderStatus((Integer) result[0]);
            response.setCount((Long) (result[1]));
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public List<ResponseTopSportswearDto> getTopSportswear(String userName, Integer month, String coupon, Integer limit) {
        StringBuilder sql = new StringBuilder(
            " SELECT "+
            " s.id AS sportswear_id, "  +
            " s.main_image AS main_image, "+
            " s.name AS sportswear_name, " +
            " SUM(oi.quantity_ordered) AS total_quantity, "+
            " SUM(oi.quantity_ordered * s.price) AS total_price " +
            " FROM orders o " +
            " INNER JOIN user u ON o.user_id = u.id " +
            " INNER JOIN coupon c ON o.coupon_id = c.id " +
            " INNER JOIN order_item oi ON o.id = oi.orders_id " +
            " INNER JOIN sportswear s ON oi.sportswear_id = s.id " +
            " WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();
        if(userName != null){
            sql.append(" AND u.user_name = ? " );
            params.add(userName);
        }
        if(coupon != null){
            sql.append(" AND c.code = ? " );
            params.add(coupon);
        }
        if(month != null){
            sql.append(" AND MONTH(o.order_date) = ? " );
            params.add(month);
        }
        sql.append(" GROUP BY s.id, s.name");
        sql.append(" ORDER BY total_price DESC");
        if(limit != null){
            sql.append(" LIMIT ? ");
            params.add(limit);
        }
        String finalSql = sql.toString();
        // Thay thế tham số với điều kiện phù hợp (không dùng dấu nháy đơn cho số)
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof String) {
                finalSql = finalSql.replaceFirst("\\?", "'" + param + "'");
            } else {
                finalSql = finalSql.replaceFirst("\\?", param.toString());
            }
        }
        System.out.println("Final SQL Query: " + finalSql);
        Query query = entityManager.createNativeQuery(sql.toString());
        for(int i=0;i<params.size();i++){
            query.setParameter(i+1, params.get(i));
        }
        List<Object[]> resultList = query.getResultList();
        List<ResponseTopSportswearDto> responseTopSportswearDtos = new ArrayList<>();
        for(Object[] result : resultList){
            ResponseTopSportswearDto responseTopSportswearDto = new ResponseTopSportswearDto();
            responseTopSportswearDto.setSportswearId((Long) result[0]);
            Sportswear sportswear = sportswearRepository.findById((Long) result[0]).orElseThrow(() -> new RuntimeException("Sportswear không tồn tại."));
            //
            responseTopSportswearDto.setSportswearImage(sportswear.getMain_image());
            //
            responseTopSportswearDto.setSportswearName((String) result[2]);
            //
            BigDecimal totalQuantity = (BigDecimal) result[3];  // Lấy giá trị BigDecimal
            responseTopSportswearDto.setTotalQuantity(totalQuantity.longValue());  // Chuyển đổi BigDecimal sang Long

            //
            responseTopSportswearDto.setTotalRevenue((Double) result[4]);
            responseTopSportswearDtos.add(responseTopSportswearDto);
        }
        return responseTopSportswearDtos;
    }
}
