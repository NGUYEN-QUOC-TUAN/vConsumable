package moonpo.consumable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    @GetMapping("/index")
    public String home() {
        return "index";
        // Return file 'index.html' in folder '/templates'
    }

    @GetMapping("/vendor")
    public String vendorHome() {
        return "/pages/vendor-main";
        // Return file 'vendor-main.html' in folder '/templates/pages'
    }

    @GetMapping("/category")
    public String categoryHome() {
        return "/pages/category-main";
        // Return file 'category-main.html' in folder '/templates/pages'
    }

    @GetMapping("/department")
    public String departmentHome() {
        return "/pages/department-main";
        // Return file 'department-main.html' in folder '/templates/pages'
    }

    @GetMapping("/unit")
    public String unitHome() {
        return "/pages/unit-measure-main"; // Return file 'unit-measure-main.html' in folder '/templates/pages'
    }

    @GetMapping("/material")
    public String materialHome() {
        return "/pages/material-main"; //Return file 'unit-measure-main.html' in folder '/templates/pages'
    }
}


