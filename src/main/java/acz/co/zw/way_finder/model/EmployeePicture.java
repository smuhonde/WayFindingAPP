package acz.co.zw.way_finder.model;

import org.apache.commons.codec.binary.Base64;
import javax.persistence.Lob;

public class EmployeePicture extends BaseEntity{
    @Lob
    private byte[] imageData;

    public EmployeePicture() {
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getBase64EncodedImage() {
        return Base64.encodeBase64String(imageData);
    }
}
