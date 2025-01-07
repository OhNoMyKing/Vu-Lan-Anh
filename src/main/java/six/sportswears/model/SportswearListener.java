package six.sportswears.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import six.sportswears.payload.request.SearchRequest;

import six.sportswears.service.SportswearServiceRedis;

@Slf4j
@Component
@NoArgsConstructor
@EntityListeners(SportswearListener.class) // Đảm bảo Sportswear sử dụng listener này
public class SportswearListener {

    private static SportswearServiceRedis sportswearServiceRedis;

    @Autowired
    public void setSportswearServiceRedis(SportswearServiceRedis sportswearServiceRedis) {
        SportswearListener.sportswearServiceRedis = sportswearServiceRedis;
    }

    private SearchRequest createSearchRequest(Sportswear sportswear) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey(sportswear.getName()); // Giả sử có thuộc tính 'name' trong Sportswear
        searchRequest.setNoPage(1L); // Cấu hình trang mặc định
        searchRequest.setCategoryName(sportswear.getSportswearCategory().getName()); // Giả sử có thuộc tính 'categoryName' trong Sportswear
        return searchRequest;
    }

    // Lắng nghe sự kiện trước khi entity được persist vào database
    @PrePersist
    public void prePersist(Sportswear sportswear) {
        sportswearServiceRedis.clear();
        log.info("prePersist: {}", sportswear); // Log thông tin về Sportswear trước khi persist
    }

    @PostPersist
    public void postPersist(Sportswear sportswear) {
        log.info("postPersist: {}", sportswear);
        sportswearServiceRedis.clear();
//        if (sportswearServiceRedis != null) {
//            SearchRequest searchRequest = createSearchRequest(sportswear);
//            sportswearServiceRedis.clear();
//        }

    }

    @PreUpdate
    public void preUpdate(Sportswear sportswear) {
        log.info("preUpdate: {}", sportswear);
    }
    @PostUpdate
    public void postUpdate(Sportswear sportswear){
        log.info("postUpdate");
        sportswearServiceRedis.clear();
    }
}
