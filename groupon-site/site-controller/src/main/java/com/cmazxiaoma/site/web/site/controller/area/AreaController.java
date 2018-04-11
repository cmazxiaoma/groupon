package com.cmazxiaoma.site.web.site.controller.area;

import com.cmazxiaoma.site.web.site.controller.BaseSiteController;
import com.cmazxiaoma.support.area.entity.Area;
import com.cmazxiaoma.support.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 地区选择
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseSiteController {

    @Autowired
    private AreaService areaService;

    @RequestMapping("/index")
    public String index(Model model) {
        Map<String, List<Area>> group = areaService.getAreaGroup();
        model.addAttribute("areas", group);
        return "/area/area";
    }

}
