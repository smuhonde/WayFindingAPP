package acz.co.zw.way_finder.service.impl.area;

import acz.co.zw.way_finder.model.Employee;
import acz.co.zw.way_finder.repository.EmployeeRepository;
import acz.co.zw.way_finder.service.api.area.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .peek(this::encodeImageData)
                .collect(Collectors.toList());
    }

    private void encodeImageData(Employee employee) {
        if (employee.getEmployeePicture() != null) {
            String encodedImage = Base64.getEncoder().encodeToString(employee.getEmployeePicture());
            employee.setBase64Image(encodedImage);
        }
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employee.getId() != null && employee.getId() > 0) {
            Employee existingEmployee = employeeRepository.findById(employee.getId()).orElse(null);
            if (existingEmployee != null) {
                employee.setEmployeePassNumber(existingEmployee.getEmployeePassNumber());
                if (employee.getEmployeePicture() == null) {
                    employee.setEmployeePicture(existingEmployee.getEmployeePicture());
                }
            } else {
                throw new RuntimeException("Employee not found with id: " + employee.getId());
            }
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(long id) { // Updated method name
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) { // Updated method name
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, sort);
        return employeeRepository.findAll(pageRequest);
    }
}
