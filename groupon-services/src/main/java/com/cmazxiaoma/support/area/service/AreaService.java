package com.cmazxiaoma.support.area.service;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.support.area.dao.AreaDAO;
import com.cmazxiaoma.support.area.entity.Area;
import com.cmazxiaoma.support.area.entity.AreaType;
import com.cmazxiaoma.util.Pinyin4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * AreaService
 */
@Service
public class AreaService {

    @Autowired
    private AreaDAO areaDAO;

    /**********************网站**************************/

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    public Area getByName(String name) {
        return this.areaDAO.getByName(name);
    }

    /**
     * 根据区域id查询
     *
     * @param id
     * @return
     */
    public Area getById(Long id) {
        return this.areaDAO.getById(id);
    }

    /**
     * 根据父节点ID查询下级
     *
     * @param parentId
     * @return
     */
    public List<Area> getByParentId(Long parentId) {
        return this.areaDAO.getByParentId(parentId);
    }

    /**
     * 获得按地名首字母分组的地名信息(不包括省一级)
     *
     * @return
     */
    public Map<String, List<Area>> getAreaGroup() {
        List<Area> all = this.areaDAO.findAll();

        Map<String, List<Area>> group = new TreeMap<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });

        all.stream().filter(area -> Objects.equals(area.getType().name(), AreaType.CITY.name()))
                .forEach(area -> {
                    String spell = Pinyin4jUtil.converterToFirstSpell(area.getName());
                    String key = String.valueOf(spell.toUpperCase().charAt(0));

                    if (!group.containsKey(key)) {
                        group.put(key, new ArrayList<>());
                    }

                    group.get(key).add(area);
                });


        return group;
    }


    /**********************后台**************************/
    /**
     * 保存
     *
     * @param area
     */
    public void save(Area area) {
        this.areaDAO.save(area);
    }

    /**
     * 更新
     *
     * @param area
     */
    public void update(Area area) {
        this.areaDAO.update(area);
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<Area> getAll() {
        List<Area> areas = this.areaDAO.findAll();
        List<Area> roots = areas.stream().filter(area -> (area.getParentId() == 0)).collect(Collectors.toList());
        List<Area> subs = areas.stream().filter(area -> (area.getParentId() != 0)).collect(Collectors.toList());
        roots.forEach(root -> buildSubs(root, subs));
        return roots;
    }

    /**
     * 递归构建
     *
     * @param parent
     * @param subs
     */
    private void buildSubs(Area parent, List<Area> subs) {
        List<Area> children = subs.stream().filter(sub ->
                (Objects.equals(sub.getParentId(), parent.getId()))
        ).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(children)) {
            parent.setChildren(children);
            children.forEach(child -> buildSubs(child, subs));
        }
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<Area> getPage(Search search) {
        return this.areaDAO.findPage(search);
    }

    /**********************混用**************************/

}
