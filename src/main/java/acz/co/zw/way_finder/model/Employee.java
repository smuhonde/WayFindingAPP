package acz.co.zw.way_finder.model;
import acz.co.zw.way_finder.enums.EmployeePosition;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Area Name is required")
    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Area Type is required")
    @Column(name = "employee_position", nullable = false)
    private EmployeePosition employeePosition;

    @Lob
    @Column(name = "employee_picture")
    private byte[] employeePicture;

    @NotBlank(message = "Pass Number is required")
    @Column(name = "pass_number", nullable = false)
    private String employeePassNumber;

    @Column(name = "employee_nationalidnumber", unique = true, nullable = false, length = 50)
    private String employeeNationalID;

    private String DateOfBirth;

    @Transient
    private String base64Image;

    // Getters and Setters

    public @NotBlank(message = "Employee Name is required") String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(@NotBlank(message = "Employee Name is required") String employeeName) {
        this.employeeName = employeeName;
    }

    public @NotNull(message = "Employee Position is required") EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(@NotNull(message = "Employee Position is required") EmployeePosition employeePosition) {
        this.employeePosition = employeePosition;
    }

    public byte[] getEmployeePicture() {
        return employeePicture;
    }

    public void setEmployeePicture(byte[] employeePicture) {
        this.employeePicture = employeePicture;
    }

    public @NotBlank(message = "Pass Number is required") String getEmployeePassNumber() {
        return employeePassNumber;
    }

    public void setEmployeePassNumber(@NotBlank(message = "Pass Number is required") String employeePassNumber) {
        this.employeePassNumber = employeePassNumber;
    }

    public String getEmployeeNationalID() {
        return employeeNationalID;
    }

    public void setEmployeeNationalID(String employeeNationalID) {
        this.employeeNationalID = employeeNationalID;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

