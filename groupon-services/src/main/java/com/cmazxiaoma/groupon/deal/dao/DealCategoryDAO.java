package com.cmazxiaoma.groupon.deal.dao;

import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.groupon.deal.constant.DealConstant;
import com.cmazxiaoma.groupon.deal.entity.DealCategory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DealCategoryDAO extends BaseMybatisDAO {
    /**
     * Mapper映射命名空间
     */
    private static final String MAPPER_NAMESPACE = "com.cmazxiaoma.groupon.deal.entity.DealCategoryMapper";

    /**
     * 查询所有未删除的商品类别
     *
     * @return
     */
    public List<DealCategory> getAllWithoutDeleted() {
        return super.findAll(MAPPER_NAMESPACE + ".selectAllWithoutDeleted", DealConstant.DEAL_PUBLISH_STATUS_DELETED);
    }

    /**
     * 查询直接下级
     *
     * @param parentId
     * @return
     */
    public List<DealCategory> getDirectSubs(Long parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("publishStatus", DealConstant.DEAL_PUBLISH_STATUS_DELETED);
        params.put("parentId", parentId);
        return findAll(MAPPER_NAMESPACE + ".selectDirectSubs", params);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public DealCategory getById(Long id) {
        return findOne(MAPPER_NAMESPACE + ".selectByPrimaryKey", id);
    }

    /**
     * 分页查询
     *
     * @param search
     * @return
     */
    public PagingResult<DealCategory> getPageCategories(Search search) {
        return findForPage(MAPPER_NAMESPACE + ".countPageCategories", MAPPER_NAMESPACE + ".selectPageCategories", search);
    }

    /**
     * 更新
     *
     * @param category
     * @return
     */
    public int updateById(DealCategory category) {
        return update(MAPPER_NAMESPACE + ".updateByPrimaryKeySelective", category);
    }

    /**
     * 保存
     *
     * @param category
     * @return
     */
    public int save(DealCategory category) {
        return save(MAPPER_NAMESPACE + ".insertSelective", category);
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return delete(MAPPER_NAMESPACE + ".deleteByPrimaryKey", id);
    }

    /**
     * 根据urlName查询
     *
     * @param urlName
     * @return
     */
    public DealCategory getByUrlName(String urlName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("urlName", urlName);
        return findOne(MAPPER_NAMESPACE + ".selectByUrlName", params);
    }

    /**
     * 获取某个商品类别下的子类别中最大的排序序号
     *
     * @param parentId
     * @return
     */
    public int getMaxOrderByParentId(Long parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
//        Map<String, Object> data = findData(MAPPER_NAMESPACE + ".selectMaxOrderByParentId", params);
//        if (data == null) {
//            return 0;
//        }
//
//        Object maxOrder = data.get("maxOrder");
//        if (maxOrder == null) {
//            return 0;
//        } else {
//            return Integer.parseInt(maxOrder.toString());
//        }
        Integer maxOrder = super.findOneObject(MAPPER_NAMESPACE + ".selectMaxOrderByParentId", params);
        return null == maxOrder ? 0 : maxOrder;
    }

    /**
     * 交换两个商品类别的排序序号
     *
     * @param id1
     * @param id2
     * @return
     */
    public int swapOrder(Long id1, Long id2) {
        Map<String, Object> params = new HashMap<>();
        params.put("id1", id1);
        params.put("id2", id2);
        return update(MAPPER_NAMESPACE + ".swapOrder", params);
    }

}
