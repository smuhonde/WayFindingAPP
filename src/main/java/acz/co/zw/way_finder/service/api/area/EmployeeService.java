package acz.co.zw.way_finder.service.api.area;

import acz.co.zw.way_finder.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();
    Employee saveEmployee(Employee employee);
    void deleteEmployeeById(long id); // Updated method name
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDir); // Updated method name
}
