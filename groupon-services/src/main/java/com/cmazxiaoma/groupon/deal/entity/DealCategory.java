package com.cmazxiaoma.groupon.deal.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Data;
import org.springframework.beans.factory.parsing.BeanEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/11 10:32
 */
@Data
public class DealCategory extends BaseEntity {

    /**
     * 父类别ID
     */
    private Long parentId;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 类别url名称
     */
    private String urlName;

    /**
     * 发布状态
     */
    private Integer publishStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 排序序号
     */
    private Integer orderNum;

    /**
     * 层次深度
     */
    private Integer deep;

    private List<DealCategory> children;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof DealCategory)) {
            return false;
        }

        DealCategory other = (DealCategory) obj;
        if (other.getId() == null && getId() == null) {
            return true;
        }

        if (other.getId() != null && getId() != null) {
            return getId().equals(other.getId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public List<Long> getSelfAndChildrenIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(this.getId());

        if (children != null && !children.isEmpty()) {
            ids.addAll(BaseEntity.idList(children));
            children.forEach(child -> getSub(child.getChildren(), ids));
        }

        return ids;
    }

    private void getSub(List<DealCategory> list, List<Long> ids) {
        if (list != null && !list.isEmpty()) {
            ids.addAll(BaseEntity.idList(list));
            list.forEach(child -> getSub(child.getChildren(), ids));
        }
    }

}
