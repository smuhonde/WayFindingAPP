package acz.co.zw.way_finder.service.api.area;

import acz.co.zw.way_finder.enums.AreaType;
import acz.co.zw.way_finder.enums.EmployeePosition;
import acz.co.zw.way_finder.model.Area;
import acz.co.zw.way_finder.model.Area2;
import acz.co.zw.way_finder.model.Employee;
import acz.co.zw.way_finder.model.EmployeePicture;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AreaService {
    // Methods for Area
    List<Area> getAllAreas();
    List<Area> getAreasByType(AreaType type);
    List<Area> getAllLounges();
    List<Area> getAllGates();
    Area findById(long id);
    List<Area> getAllOtherAreas();
    Area saveArea(Area area);
    Area findByIdOrThrow(long id);
    void deleteAreaById(long id);
    Page<Area> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
    List<Area> searchByAllFields(String searchTerm);
    Area findByAreaNumber(String areaNumber);

    // Methods for Area2
    List<Area2> getAllAreas2();
    List<Area2> getAreasByType2(AreaType type);
    List<Area2> getAllLounges2();
    List<Area2> getAllGates2();
    Area2 findById2(long id);
    List<Area2> getAllOtherAreas2();
    Area2 saveArea(Area2 area2);
    void deleteAreaById2(long id);
    Area2 findByIdOrThrow2(long id);
    Page<Area2> findPaginated2(int pageNo, int pageSize, String sortField, String sortDir);
    List<Area2> searchByAllFields2(String searchTerm);
    Area2 findByAreaNumber2(String areaNumber);

}
