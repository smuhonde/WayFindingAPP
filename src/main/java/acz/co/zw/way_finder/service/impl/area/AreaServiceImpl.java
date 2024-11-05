package acz.co.zw.way_finder.service.impl.area;
import acz.co.zw.way_finder.enums.EmployeePosition;
import acz.co.zw.way_finder.enums.AreaType;
import acz.co.zw.way_finder.model.Area;
import acz.co.zw.way_finder.model.Area2;
import acz.co.zw.way_finder.model.Employee;
import acz.co.zw.way_finder.repository.AreaRepository;
import acz.co.zw.way_finder.repository.Area2Repository;
import acz.co.zw.way_finder.repository.EmployeeRepository;
import acz.co.zw.way_finder.service.api.area.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private Area2Repository area2Repository;


    @Autowired
    private EmployeeRepository employeeRepository;

    // ------------------------ Methods for Area ------------------------

    @Override
    public List<Area> getAllAreas() {
        return areaRepository.findAll().stream() // Convert the list to a stream
                .peek(this::encodeImageData) // Perform an action on each area
                .collect(Collectors.toList()); // Collect the result back into a list
    }


    @Override
    public List<Area> getAreasByType(AreaType type) {
        return areaRepository.findAll().stream()
                .filter(area -> type.equals(area.getAreaType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Area> getAllLounges() {
        return areaRepository.findAll().stream()
                .filter(area -> AreaType.Lounges.equals(area.getAreaType()))
                .peek(this::encodeImageData)
                .collect(Collectors.toList());
    }

    @Override
    public List<Area> getAllGates() {
        return areaRepository.findAll().stream()
                .filter(area -> AreaType.Gates.equals(area.getAreaType()))
                .peek(this::encodeImageData)
                .collect(Collectors.toList());
    }

    @Override
    public Area findById(long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found with id: " + id));
    }

    @Override
    public List<Area> getAllOtherAreas() {
        return areaRepository.findAll().stream()
                .filter(area -> AreaType.Other.equals(area.getAreaType()))
                .peek(this::encodeImageData)
                .collect(Collectors.toList());
    }

    @Override
    public Area saveArea(Area area) {
        if (area.getId() > 0) { // Check if it's an existing area
            Area existingArea = areaRepository.findById(area.getId()).orElse(null);
            if (existingArea != null) {
                area.setAreaNumber(existingArea.getAreaNumber()); // Preserve the existing AreaNumber
                if (area.getAreaPicture() == null) {
                    // If no new image was uploaded, keep the old one
                    area.setAreaPicture(existingArea.getAreaPicture());
                }
            } else {
                throw new RuntimeException("Area not found with id: " + area.getId());
            }
        } else {
            if (area.getAreaNumber() == null || area.getAreaNumber().isEmpty()) {
                String generatedNumber = generateUniqueAreaNumber(area.getAreaType());
                area.setAreaNumber(generatedNumber);
            }
        }
        return areaRepository.save(area);
    }

    @Override
    public Area findByIdOrThrow(long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found with id: " + id));
    }

    @Override
    public void deleteAreaById(long id) {
        areaRepository.deleteById(id);
    }

    @Override
    public Page<Area> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, sort);
        return areaRepository.findAll(pageRequest);
    }

    @Override
    public List<Area> searchByAllFields(String searchTerm) {
        return areaRepository.searchByAllFields(searchTerm);
    }

    @Override
    public Area findByAreaNumber(String areaNumber) {
        return areaRepository.findByAreaNumber(areaNumber).orElseThrow(() -> new RuntimeException("Area not found with areaNumber: " + areaNumber));
    }

    private void encodeImageData(Area area) {
        if (area.getAreaPicture() != null) {
            String encodedImage = Base64.getEncoder().encodeToString(area.getAreaPicture());
            area.setBase64Image(encodedImage);
        }
    }

    private String generateUniqueAreaNumber(AreaType areaType) {
        String prefix;
        switch (areaType) {
            case Lounges:
                prefix = "LN";
                break;
            case Gates:
                prefix = "GT";
                break;
            default:
                prefix = "OT";
                break;
        }

        String maxAreaNumber = areaRepository.findMaxAreaNumberByPrefix(prefix);
        if (maxAreaNumber == null) {
            return prefix + String.format("%010d", 1);
        }

        int currentNumber = Integer.parseInt(maxAreaNumber.substring(2));
        return prefix + String.format("%010d", currentNumber + 1);
    }

    // ------------------------ Methods for Area2 ------------------------


    @Override
    public List<Area2> getAllAreas2() {
        return area2Repository.findAll().stream() // Convert the list to a stream
                .peek(this::encodeImageData2) // Perform an action on each area
                .collect(Collectors.toList()); // Collect the result back into a list
    }



    @Override
    public List<Area2> getAreasByType2(AreaType type) {
        return area2Repository.findAll().stream()
                .filter(area2 -> type.equals(area2.getAreaType()))
                .collect(Collectors.toList()); // Filter Area2 by type
    }


    @Override
    public List<Area2> getAllLounges2() {
        return area2Repository.findAll().stream()
                .filter(area2 -> AreaType.Lounges.equals(area2.getAreaType()))
                .peek(this::encodeImageData2)
                .collect(Collectors.toList()); // Get all lounges of Area2
    }

    @Override
    public List<Area2> getAllGates2() {
        return area2Repository.findAll().stream()
                .filter(area2 -> AreaType.Gates.equals(area2.getAreaType()))
                .peek(this::encodeImageData2)
                .collect(Collectors.toList()); // Get all gates of Area2
    }

    @Override
    public Area2 findById2(long id) {
        return area2Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area2 not found with id: " + id)); // Find Area2 by ID
    }

    @Override
    public List<Area2> getAllOtherAreas2() {
        return area2Repository.findAll().stream()
                .filter(area2 -> AreaType.Other.equals(area2.getAreaType()))
                .peek(this::encodeImageData2)
                .collect(Collectors.toList()); // Get all other types of Area2
    }

    @Override
    public Area2 saveArea(Area2 area2) {
        if (area2.getId() > 0) { // Check if it's an existing area
            Area2 existingArea2 = area2Repository.findById(area2.getId()).orElse(null);
            if (existingArea2 != null) {
                area2.setAreaNumber(existingArea2.getAreaNumber()); // Preserve the existing AreaNumber
                if (area2.getAreaPicture() == null) {
                    // If no new image was uploaded, keep the old one
                    area2.setAreaPicture(existingArea2.getAreaPicture());
                }
            } else {
                throw new RuntimeException("Area2 not found with id: " + area2.getId());
            }
        } else {
            if (area2.getAreaNumber() == null || area2.getAreaNumber().isEmpty()) {
                String generatedNumber = generateUniqueAreaNumber2(area2.getAreaType());
                area2.setAreaNumber(generatedNumber);
            }
        }
        return area2Repository.save(area2); // Save Area2
    }

    @Override
    public void deleteAreaById2(long id) {
        area2Repository.deleteById(id); // Delete Area2 by ID
    }

    @Override
    public Page<Area2> findPaginated2(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, sort); // Pagination request
        return area2Repository.findAll(pageRequest); // Return paginated Area2
    }

    @Override
    public List<Area2> searchByAllFields2(String searchTerm) {
        return area2Repository.searchByAllFields(searchTerm); // Search Area2 by fields
    }

    @Override
    public Area2 findByAreaNumber2(String areaNumber) {
        return area2Repository.findByAreaNumber(areaNumber)
                .orElseThrow(() -> new RuntimeException("Area2 not found with areaNumber: " + areaNumber)); // Find Area2 by AreaNumber
    }

    @Override
    public Area2 findByIdOrThrow2(long id) {
        return area2Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found with id: " + id));
    }

    
    private void encodeImageData2(Area2 area2) {
        if (area2.getAreaPicture() != null) {
            String encodedImage = Base64.getEncoder().encodeToString(area2.getAreaPicture());
            area2.setBase64Image(encodedImage); // Encode image data
        }
    }

    private String generateUniqueAreaNumber2(AreaType areaType) {
        String prefix;
        switch (areaType) {
            case Lounges:
                prefix = "LN";
                break;
            case Gates:
                prefix = "GT";
                break;
            default:
                prefix = "OT";
                break;
        }

        String maxAreaNumber = area2Repository.findMaxAreaNumberByPrefix(prefix);
        if (maxAreaNumber == null) {
            return prefix + String.format("%010d", 1);
        }

        int currentNumber = Integer.parseInt(maxAreaNumber.substring(2));
        return prefix + String.format("%010d", currentNumber + 1); // Generate unique area number
    }


}
