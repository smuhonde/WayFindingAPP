package acz.co.zw.way_finder.repository;
import acz.co.zw.way_finder.service.api.area.AreaService;
import acz.co.zw.way_finder.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import acz.co.zw.way_finder.enums.EmployeePosition;
import java.util.Optional;
import java.util.List;
import static org.apache.coyote.http11.Constants.a;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByAreaNumber(String employeePassNumber);
    Optional<Employee> findByAreaName(String employeeName);
    @Query("SELECT MAX(a.areaNumber) FROM Area a WHERE a.areaNumber LIKE :prefix%")
    String findMaxEmployeePassNumberByPrefix(@Param("prefix") String prefix);

    List<Employee> findByEmployeeNameContainingIgnoreCase(String searchTerm);

    @Query("SELECT a FROM Area a WHERE LOWER(a.areaName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(a.areaNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(a.areaType) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Employee> searchByAllFields(@Param("searchTerm") String searchTerm);

}
