package six.sportswears.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.CouponCondition;

import java.util.List;
@Repository
public interface CouponConditionRepository extends JpaRepository<CouponCondition,Long> {
    List<CouponCondition> findByCouponId(Long couponId);
}
