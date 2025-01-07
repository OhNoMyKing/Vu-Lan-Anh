package six.sportswears.service;

import six.sportswears.payload.response.chatbot.response.CouponDTO;

import java.util.List;

public interface ICouponService {
    double caculateCouponValue(String couponCode, double totalAmount);
    Long getCouponIdByCode(String couponCode);
    List<CouponDTO> getAllCoupons();
}
