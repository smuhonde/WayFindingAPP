package acz.co.zw.way_finder.repository;

import acz.co.zw.way_finder.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import acz.co.zw.way_finder.enums.AreaType;
import java.util.Optional;
import java.util.List;
import static org.apache.coyote.http11.Constants.a;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByAreaNumber(String areaNumber);
    Optional<Area> findByAreaName(String areaName);
    @Query("SELECT MAX(a.areaNumber) FROM Area a WHERE a.areaNumber LIKE :prefix%")
    String findMaxAreaNumberByPrefix(@Param("prefix") String prefix);

    List<Area> findByAreaNameContainingIgnoreCase(String searchTerm);

    @Query("SELECT a FROM Area a WHERE LOWER(a.areaName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(a.areaNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(a.areaType) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Area> searchByAllFields(@Param("searchTerm") String searchTerm);

}
