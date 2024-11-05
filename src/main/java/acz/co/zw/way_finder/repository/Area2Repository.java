package acz.co.zw.way_finder.repository;

import acz.co.zw.way_finder.model.Area2; // Make sure to import Area2
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import acz.co.zw.way_finder.enums.AreaType;
import java.util.Optional;
import java.util.List;
import static org.apache.coyote.http11.Constants.a;


@Repository
public interface Area2Repository extends JpaRepository<Area2, Long> {
    Optional<Area2> findByAreaNumber(String areaNumber); // Change return type to Area2
    Optional<Area2> findByAreaName(String areaName); // Change return type to Area2

    @Query("SELECT MAX(a.areaNumber) FROM Area2 a WHERE a.areaNumber LIKE CONCAT(:prefix, '%')")
    String findMaxAreaNumberByPrefix(@Param("prefix") String prefix);

    List<Area2> findByAreaNameContainingIgnoreCase(String searchTerm); // Change return type to Area2

    @Query("SELECT a FROM Area2 a WHERE LOWER(a.areaName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(a.areaNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(a.areaType) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Area2> searchByAllFields(@Param("searchTerm") String searchTerm); // Change return type to Area2
}
