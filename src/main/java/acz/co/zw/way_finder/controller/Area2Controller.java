package acz.co.zw.way_finder.controller;

import acz.co.zw.way_finder.enums.AreaType;
import acz.co.zw.way_finder.model.Area;
import acz.co.zw.way_finder.model.Area2; // Import Area2 instead of Area
import acz.co.zw.way_finder.service.api.area.AreaService; // Service for Area2
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:8111")
@RequestMapping
@Controller
public class Area2Controller {

    @Autowired
    private AreaService AreaService; // Use Area2Service for Area2

    // Display list of areas2 with pagination
    @GetMapping("/areas2")
    public String viewHomePage(Model model,
                               @RequestParam(value = "searchTerm", required = false) String searchTerm) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            List<Area2> areas2 = AreaService.searchByAllFields2(searchTerm); // Search in Area2

            for (Area2 area : areas2) {
                if (area.getAreaPicture() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(area.getAreaPicture());
                    area.setBase64Image(base64Image);
                }
            }
            model.addAttribute("listAreas2", areas2);
            model.addAttribute("searchTerm", searchTerm);
            return "index2"; // Assuming different template for Area2
        } else {
            return findPaginated(1, "areaNumber", "asc", model);
        }
    }

    @GetMapping("/displayall2")
    public String listAreas(Model model) {
        List<Area2> areas2 = AreaService.getAllAreas2(); // Get all Area2
        for (Area2 area : areas2) {
            if (area.getAreaPicture() != null) {
                String base64Image = Base64.getEncoder().encodeToString(area.getAreaPicture());
                area.setBase64Image(base64Image);
            }
        }
        model.addAttribute("listAreas2", areas2);
        return "displayall2"; // Assuming different template for Area2
    }

    @GetMapping("/areas2/showNewAreaForm")
    public String showNewAreaForm(Model model) {
        Area2 area2 = new Area2(); // Use Area2 instead of Area
        model.addAttribute("area2", area2);
        model.addAttribute("areaTypes", AreaType.values());
        return "new_area2"; // Assuming different template for Area2
    }

    @PostMapping("/areas2/saveArea")
    public String saveArea(@Valid @ModelAttribute("area2") Area2 area2,
                           BindingResult result,
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           Model model) {
        // Validate the form inputs
        if (result.hasErrors()) {
            model.addAttribute("areaTypes", AreaType.values());
            return "new_area2"; // Return to the Area2 form with errors
        }

        // If no new image is uploaded, keep the existing image
        if (image == null || image.isEmpty()) {
            Area2 existingArea2 = AreaService.findById2(area2.getId());
            if (existingArea2 != null) {
                area2.setAreaPicture(existingArea2.getAreaPicture());
            }
        } else {
            try {
                byte[] imageData = image.getBytes();
                area2.setAreaPicture(imageData);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Failed to upload image.");
                return "new_area2"; // Return to Area2 form with error message
            }
        }

        AreaService.saveArea(area2); // Save Area2
        return "redirect:/displayall2"; // Redirect to Area2 listing page
    }

    @GetMapping("/areas2/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Area2 area2 = AreaService.findByIdOrThrow2(id); // Fetch Area2 by ID
        model.addAttribute("area", area2);
        model.addAttribute("areaTypes", AreaType.values());
        return "update_area2"; // Assuming different template for Area2
    }

    @GetMapping("/areas2/deleteArea/{id}")
    public String deleteArea(@PathVariable(value = "id") long id) {
        AreaService.deleteAreaById2(id); // Delete Area2 by ID
        return "redirect:/displayall2"; // Redirect to Area2 listing page
    }



    @GetMapping("/areas2/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Area2> page = AreaService.findPaginated2(pageNo, pageSize, sortField, sortDir); // Paginated search for Area2
        List<Area2> listAreas2 = page.getContent();
        for (Area2 area : listAreas2) {
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
        model.addAttribute("listAreas2", listAreas2);
        return "index2"; // Return Area2 template
    }

    // New method to handle image requests based on areaNumber for Area2
    @GetMapping("/api/areas2/{areaNumber}/image")
    @ResponseBody
    public byte[] getImageByAreaNumber(@PathVariable String areaNumber) {
        Area2 area2 = AreaService.findByAreaNumber2(areaNumber); // Fetch Area2 by areaNumber
        if (area2 != null && area2.getAreaPicture() != null) {
            return area2.getAreaPicture();
        }
        return new byte[0]; // Return an empty array if no image is found
    }

    @GetMapping("/loungeDisplayForm2")
    public ModelAndView showLounges2() {
        ModelAndView mav = new ModelAndView("loungeDisplayForm");
        List<Area2> lounges = AreaService.getAllLounges2();  // Assumes a method to get all lounges

        // Log lounges to ensure data is being fetched
        if (lounges.isEmpty()) {
            System.out.println("No lounges found.");
        } else {
            System.out.println("Number of lounges: " + lounges.size());
        }

        mav.addObject("listLounges", lounges);
        return mav;
    }

    @GetMapping("/gatesDisplayForm2")
    public ModelAndView showAllGatesDirectionsForm() {
        ModelAndView mav = new ModelAndView("gatesDisplayForm");
        List<Area2> gates = AreaService.getAllGates2();
        mav.addObject("listGates", gates);
        return mav;
    }

    @GetMapping("/otherAreasDisplayForm2")
    public ModelAndView showOtherAreas() {
        ModelAndView mav = new ModelAndView("otherAreaDisplayForm");
        List<Area2> others = AreaService.getAllOtherAreas2();
        mav.addObject("listOtherAreas", others);
        return mav;
    }

    @GetMapping("/AllAreasDisplayFormDisplay2")
    public ModelAndView showAreas() {
        ModelAndView mav = new ModelAndView("AllAreasDisplayFormDisplay1");
        List<Area2> areas = AreaService.getAllAreas2();
        mav.addObject("listAreas", areas);
        return mav;
    }

}
