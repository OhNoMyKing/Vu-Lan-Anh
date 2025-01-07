package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import six.sportswears.model.Coupon;
import six.sportswears.payload.response.chatbot.response.CouponDTO;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponToCouponDTO {
    public List<CouponDTO>  toCouponDTO(List<Coupon> couponList){
        List<CouponDTO> couponDTOList = new ArrayList<>();
        for(Coupon coupon : couponList){
            CouponDTO couponDTO = new CouponDTO();
            couponDTO.setCode(coupon.getCode());
            couponDTO.setDescription(coupon.getDescription());
            couponDTOList.add(couponDTO);
        }
        return  couponDTOList;
    }
}
