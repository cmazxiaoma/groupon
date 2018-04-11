package com.cmazxiaoma.admin.common.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyUITreeNode
 */
public class EasyUITreeNode implements Comparable<EasyUITreeNode> {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long parentId;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private String state; // 状态，closed：收起所有子类别

    @Getter
    @Setter
    private boolean checked; // 是否选中

    @Getter
    @Setter
    private Map<String, Object> attributes; // 自定义属性

    @Getter
    @Setter
    private List<EasyUITreeNode> children; // 子类别

    public EasyUITreeNode() {
    }

    public EasyUITreeNode(Long id, Long parentId, String text, String state) {
        this.id = id;
        this.parentId = parentId;
        this.text = text;
        this.state = state;
        this.attributes = new HashMap<>();
        this.children = new ArrayList<>();
    }

    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(EasyUITreeNode o) {
        return (int) (this.getId() - o.getId());
    }

}