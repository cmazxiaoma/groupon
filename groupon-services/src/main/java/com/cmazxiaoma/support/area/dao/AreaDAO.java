package com.cmazxiaoma.support.area.dao;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.support.area.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 地区
 */
@Repository
public class AreaDAO extends BaseMybatisDAO {

    private final String MAPPER_NAMESPACE = "com.cmazxiaoma.support.area.entity.AreaMapper";

    /**
     * 保存
     *
     * @param area
     */
    public void save(Area area) {
        super.save(MAPPER_NAMESPACE + ".insertSelective", area);
    }

    /**
     * 更新
     *
     * @param area
     */
    public void update(Area area) {
        super.save(MAPPER_NAMESPACE + ".updateByPrimaryKeySelective", area);
    }

    /**
     * 根据区域id查询
     *
     * @param id
     * @return
     */
    public Area getById(Long id) {
        return super.findOne(MAPPER_NAMESPACE + ".selectById", id);
    }


    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    public Area getByName(String name) {
        return super.findOne(MAPPER_NAMESPACE + ".selectByName", name);
    }

    /**
     * 根据父节点ID查询下级
     *
     * @param parentId
     * @return
     */
    public List<Area> getByParentId(Long parentId) {
        return super.findAll(MAPPER_NAMESPACE + ".selectByParentId", parentId);
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<Area> findAll() {
        return super.findAll(MAPPER_NAMESPACE + ".selectAll");
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<Area> findPage(Search search) {
        return super.findForPage(MAPPER_NAMESPACE + ".countPage", MAPPER_NAMESPACE + ".selectPage", search);
    }

}
