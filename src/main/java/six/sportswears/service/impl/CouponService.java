package six.sportswears.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import six.sportswears.converter.CouponToCouponDTO;
import six.sportswears.model.Coupon;
import six.sportswears.model.CouponCondition;
import six.sportswears.payload.response.chatbot.response.CouponDTO;
import six.sportswears.repository.CouponConditionRepository;
import six.sportswears.repository.CouponRepository;
import six.sportswears.service.ICouponService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class CouponService implements ICouponService {
    private final CouponRepository couponRepository;
    private final CouponConditionRepository couponConditionRepository;
    CouponToCouponDTO couponToCouponDTO;
    private double caculateDiscount(Coupon coupon, double totalAmount){
        List<CouponCondition> conditions = couponConditionRepository.findByCouponId(coupon.getId());
        double discount = 0.0;
        double updateTotalAmount = totalAmount;
        for(CouponCondition condition : conditions){
            String attribute = condition.getAttribute();
            String operator = condition.getOperator();
            String value = condition.getValue();
            double percentDiscount = Double.valueOf(
                    String.valueOf(condition.getDiscountAmount()));
            if(attribute.equals("minimum_amount")){
                if(operator.equals(">") && updateTotalAmount > Double.parseDouble(value)){
                    discount += updateTotalAmount * percentDiscount / 100;
                }
            } else if(attribute.equals("applicable_date")) {
                LocalDate applicableDate = LocalDate.parse(value);
                LocalDate currentDate = LocalDate.now();
                if (operator.equalsIgnoreCase("BETWEEN") && currentDate.isEqual(applicableDate)) {
                    discount += updateTotalAmount * percentDiscount / 100;
                }
            }
        }
        return  discount;
    }
    public Long getCouponIdByCode(String couponCode){
        Coupon coupon = couponRepository.findByCode(couponCode).orElseThrow(() -> new IllegalArgumentException("Coupon not found"));
        return coupon.getId();
    }

    @Override
    public List<CouponDTO> getAllCoupons() {
        List<Coupon>  coupons = couponRepository.findAll();
        List<CouponDTO> couponDTOs = couponToCouponDTO.toCouponDTO(coupons);
        return couponDTOs;
    }

    @Override
    public double caculateCouponValue(String couponCode, double totalAmount) {
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found"));
        if(!coupon.isActive()){
            throw new IllegalArgumentException("Coupon is not active");
        }
        double discount = caculateDiscount(coupon, totalAmount);
        double finalAmount = totalAmount - discount;
        return finalAmount;
    }

}
