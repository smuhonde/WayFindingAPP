package acz.co.zw.way_finder.controller;
import acz.co.zw.way_finder.enums.EmployeePosition;
//import acz.co.zw.way_finder.model.Area;
//import acz.co.zw.way_finder.model.Area2;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:8111")
@RequestMapping
@Controller
public class EmployeeController {


}
