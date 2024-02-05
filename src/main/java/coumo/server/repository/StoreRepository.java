package coumo.server.repository;

import coumo.server.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT s.* FROM store AS s " +
            "WHERE MBRContains(ST_SRID(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(', ?1, ' ', ?2, ', ', ?3, ' ', ?4, ')')), 4326), ST_SRID(s.point, 4326)) AND s.store_type <> 'NONE'",
            countQuery = "SELECT count(*) FROM store AS s " +
                    "WHERE MBRContains(ST_SRID(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(', ?1, ' ', ?2, ', ', ?3, ' ', ?4, ')')), 4326), ST_SRID(s.point, 4326)) AND s.store_type <> 'NONE'",
            nativeQuery = true)
    Page<Store> findNearByStores(Double x1, Double y1, Double x2, Double y2, Pageable pageable);

    @Query(value = "SELECT s.* FROM store AS s " +
            "WHERE MBRContains(ST_SRID(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(', ?1, ' ', ?2, ', ', ?3, ' ', ?4, ')')), 4326), ST_SRID(s.point, 4326)) AND s.store_type <> 'NONE' AND s.store_type = ?5",
            countQuery = "SELECT count(*) FROM store AS s " +
                    "WHERE MBRContains(ST_SRID(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(', ?1, ' ', ?2, ', ', ?3, ' ', ?4, ')')), 4326), ST_SRID(s.point, 4326)) AND s.store_type <> 'NONE' AND s.store_type = ?5",
            nativeQuery = true)
    Page<Store> findNearByStores(Double x1, Double y1, Double x2, Double y2, String category, Pageable pageable);

}