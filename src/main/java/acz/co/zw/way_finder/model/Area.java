package acz.co.zw.way_finder.model;

import acz.co.zw.way_finder.enums.AreaType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "areas")
public class Area extends BaseEntity {

    @NotBlank(message = "Area Name is required")
    @Column(name = "area_name", nullable = false)
    private String areaName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Area Type is required")
    @Column(name = "area_type", nullable = false)
    private AreaType areaType;

    @Lob
    @Column(name = "area_picture")
    private byte[] areaPicture;

    @NotBlank(message = "Area Direction is required")
    @Column(name = "area_direction", nullable = false)
    private String areaDirection;

    @Column(name = "area_number", unique = true, nullable = false, length = 50)
    private String areaNumber;

    @Transient
    private String base64Image;

    private String coordinates;


    // Getters and Setters
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public byte[] getAreaPicture() {
        return areaPicture;
    }

    public void setAreaPicture(byte[] areaPicture) {
        this.areaPicture = areaPicture;
    }

    public String getAreaDirection() {
        return areaDirection;
    }

    public void setAreaDirection(String areaDirection) {
        this.areaDirection = areaDirection;
    }

    public String getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }


    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
