package acz.co.zw.way_finder.controller;

import acz.co.zw.way_finder.enums.AreaType;
import acz.co.zw.way_finder.model.Area;
import acz.co.zw.way_finder.model.Area2;
import acz.co.zw.way_finder.service.api.area.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;


@CrossOrigin(origins = "http://localhost:8111")
@RequestMapping
@Controller
public class AreaController {

    @Autowired
    private AreaService areaService;

    // Display list of areas with pagination
    @GetMapping("/")
    public String viewHomePage(Model model,
                               @RequestParam(value = "searchTerm", required = false) String searchTerm) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            List<Area> areas = areaService.searchByAllFields(searchTerm);

            for (Area area : areas) {
                if (area.getAreaPicture() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(area.getAreaPicture());
                    area.setBase64Image(base64Image);
                }
            }
            model.addAttribute("listAreas", areas);
            model.addAttribute("searchTerm", searchTerm);
            return "index";
        } else {
            return findPaginated(1, "areaNumber", "asc", model);
        }
    }

    @GetMapping("/displayall")
    public String listAreas(Model model) {
        List<Area> areas = areaService.getAllAreas();
        for (Area area : areas) {
            if (area.getAreaPicture() != null) {
                String base64Image = Base64.getEncoder().encodeToString(area.getAreaPicture());
                area.setBase64Image(base64Image);
            }
        }
        model.addAttribute("listAreas", areas);
        return "displayall";
    }

    @GetMapping("/areas/showNewAreaForm")
    public String showNewAreaForm(Model model) {
        Area area = new Area();
        model.addAttribute("area", area);
        model.addAttribute("areaTypes", AreaType.values());
        return "new_area";
    }

    @PostMapping("/areas/saveArea")
    public String saveArea(@Valid @ModelAttribute("area") Area area,
                           BindingResult result,
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           Model model) {
        // Validate the form inputs
        if (result.hasErrors()) {
            // If there are validation errors, return to the form with the current data
            model.addAttribute("areaTypes", AreaType.values());
            return "new_area";  // Assuming this is the view name for the form
        }

        // If no new image is uploaded, keep the existing image
        if (image == null || image.isEmpty()) {
            // Retrieve the existing area by its ID
            Area existingArea = areaService.findById(area.getId());
            if (existingArea != null) {
                // Preserve the existing image if present
                area.setAreaPicture(existingArea.getAreaPicture());
            }
        } else {
            // Handle new image upload
            try {
                byte[] imageData = image.getBytes();
                area.setAreaPicture(imageData);
            } catch (IOException e) {
                e.printStackTrace();
                // Add an error message to the model
                model.addAttribute("errorMessage", "Failed to upload image.");
                return "new_area";  // Return to form with error message
            }
        }

        // Save the updated area (with the new or existing image)
        areaService.saveArea(area);

        // Redirect to the areas listing page after successful save


        return "redirect:/displayall";
    }

    @GetMapping("/areas/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Area area = areaService.findByIdOrThrow(id);
        model.addAttribute("area", area);
        model.addAttribute("areaTypes", AreaType.values());
        return "update_area";
    }

    @GetMapping("/areas/deleteArea/{id}")
    public String deleteArea(@PathVariable(value = "id") long id) {
        areaService.deleteAreaById(id);
        return "redirect:/displayall";
    }


    @GetMapping("/loungeDisplayForm")
    public ModelAndView showLounges() {
        ModelAndView mav = new ModelAndView("loungeDisplayForm");
        List<Area> lounges = areaService.getAllLounges();  // Assumes a method to get all lounges

        // Log lounges to ensure data is being fetched
        if (lounges.isEmpty()) {
            System.out.println("No lounges found.");
        } else {
            System.out.println("Number of lounges: " + lounges.size());
        }

        mav.addObject("listLounges", lounges);
        return mav;
    }

    @GetMapping("/loungeDisplayTable")
    public ModelAndView showLoungesTable() {
        ModelAndView mav = new ModelAndView("loungeDisplayTable");
        List<Area> lounges = areaService.getAllLounges(); // Assumes a method to get all lounges
        mav.addObject("listLounges", lounges);
        return mav;
    }
    @GetMapping("/AllGatesDirections")
    public ModelAndView showAllGatesDirections() {
        ModelAndView mav = new ModelAndView("AllGatesDirections");
        List<Area> gates = areaService.getAllGates();
        mav.addObject("listGates", gates);
        return mav;
    }

    @GetMapping("/gatesDisplayTable")
    public ModelAndView showAllGatesDirectionsTable() {
        ModelAndView mav = new ModelAndView("gatesDisplayTable");
        List<Area> gates = areaService.getAllGates();
        mav.addObject("listGates", gates);
        return mav;
    }

    @GetMapping("/gatesDisplayForm")
    public ModelAndView showAllGatesDirectionsForm() {
        ModelAndView mav = new ModelAndView("gatesDisplayForm");
        List<Area> gates = areaService.getAllGates();
        mav.addObject("listGates", gates);
        return mav;
    }

    @GetMapping("/otherAreasDisplayForm")
    public ModelAndView showOtherAreas() {
        ModelAndView mav = new ModelAndView("otherAreaDisplayForm");
        List<Area> others = areaService.getAllOtherAreas();
        mav.addObject("listOtherAreas", others);
        return mav;
    }

    @GetMapping("/AllAreasDisplayFormDisplay1")
    public ModelAndView showAreas() {
        ModelAndView mav = new ModelAndView("AllAreasDisplayFormDisplay1");
        List<Area> areas = areaService.getAllAreas();
        mav.addObject("listAreas", areas);
        return mav;
    }


    @GetMapping("/otherAreasDisplayTable")
    public ModelAndView showOtherAreasTable() {
        ModelAndView mav = new ModelAndView("otherAreaDisplayTable");
        List<Area> others = areaService.getAllOtherAreas();
        mav.addObject("listOtherAreas", others);
        return mav;
    }

    @GetMapping("/imageGrid")
    public ModelAndView showImageGrid(@RequestParam(name = "areaId", required = false) Long areaId) {
        ModelAndView mav = new ModelAndView("imageGrid");
        if (areaId != null) {
            Area area = areaService.findByIdOrThrow(areaId);
            mav.addObject("area", area);
        }
        return mav;
    }

    @GetMapping("/areas/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Area> page = areaService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Area> listAreas = page.getContent();
        for (Area area : listAreas) {
            if (area.getAreaPicture() != null) {
                String base64Image = Base64.getEncoder().encodeToString(area.getAreaPicture());
                area.setBase64Image(base64Image);
            }
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listAreas", listAreas);
        return "index";
    }

    @GetMapping("/slideshow")
    public ModelAndView showSlideshow() {
        return new ModelAndView("slideshow");
    }

    // New method to handle image requests based on areaNumber
    @GetMapping("/api/areas/{areaNumber}/image")
    @ResponseBody
    public byte[] getImageByAreaNumber(@PathVariable String areaNumber) {
        Area area = areaService.findByAreaNumber(areaNumber);
        if (area != null && area.getAreaPicture() != null) {
            return area.getAreaPicture();
        }
        return new byte[0]; // Return an empty array if no image is found
    }
}