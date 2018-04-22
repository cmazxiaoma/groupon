package com.cmazxiaoma.admin.area.controller;

import com.cmazxiaoma.admin.base.controller.AjaxResult;
import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.admin.common.tree.EasyUITreeNode;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.merchant.entity.Merchant;
import com.cmazxiaoma.support.area.entity.Area;
import com.cmazxiaoma.support.area.entity.AreaType;
import com.cmazxiaoma.support.area.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 地区
 */
@Controller
@RequestMapping("/area")
@Slf4j
public class AreaController extends BaseAdminController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/area/area_tree";
    }

    // 加载地区树
    @RequestMapping(value = "/buildAreaTree", method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUITreeNode> buildProductCategoryTree() {
        List<EasyUITreeNode> nodes = new ArrayList<>();

        List<Area> list = areaService.getAll();
        for (Area area : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setChecked(false);
            node.setId(area.getId());
            node.setText(area.getName());
            node.setState("closed");
            initChildren(node, area.getChildren(), area.getType());
            nodes.add(node);
        }
        return nodes;
    }

    private void initChildren(EasyUITreeNode parent, List<Area> areas, AreaType areaType) {
        List<EasyUITreeNode> childrenVO = new ArrayList<>();
        if (null == areas) {
            if (Objects.equals(AreaType.PROVINCE, areaType)) {
                EasyUITreeNode childVO = new EasyUITreeNode();
                childVO.setChecked(false);
                childVO.setId(parent.getId());
                childVO.setParentId(parent.getId());
                childVO.setText(parent.getText());
                childrenVO.add(childVO);
                parent.setChildren(childrenVO);
            }
            return;
        }
        for (Area area : areas) {
            EasyUITreeNode childVO = new EasyUITreeNode();
            childVO.setChecked(false);
            childVO.setId(area.getId());
            childVO.setParentId(parent.getId());
            childVO.setText(area.getName());
            initChildren(childVO, area.getChildren(), area.getType());
            childrenVO.add(childVO);
        }
        parent.setChildren(childrenVO);
    }

    /**
     * 加载地区列表
     * @param search
     * @return
     */
    @RequestMapping(value = "/listArea", method = RequestMethod.POST)
    @ResponseBody
    public PagingResult<Area> listArea(Search search) {
        return this.areaService.getPage(search);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult saveOrUpdate(Area area) {
        try {
            if (StringUtils.isEmpty(area.getId())) {
                areaService.save(area);
            } else {
                areaService.update(area);
            }
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, "area保存成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_SUCCESS, "area保存失败");
        }
    }


    @RequestMapping(value = "/edit")
    public String editArea(@RequestParam(value = "id") Long id,
                           @RequestParam(value = "parentId") Long parentId) {
        // 获取area信息
        if (id != 0) {
            // 获取area信息
            Area area = this.areaService.getById(id);
            modelMap.addAttribute("area", area);
        } else {
            Area area = new Area();
            area.setParentId(parentId);
            modelMap.addAttribute("area", area);
        }
        return "/area/area_edit";
    }
}
