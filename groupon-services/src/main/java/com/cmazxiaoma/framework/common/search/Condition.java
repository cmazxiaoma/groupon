package com.cmazxiaoma.framework.common.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 查询条件
 */
@NoArgsConstructor
@AllArgsConstructor
public class Condition implements Serializable {

	private static final long serialVersionUID = -5355773325400152119L;
	
	/**
	 * 参数名称
	 */
	@Getter
    @Setter
    private String name;

	/**
	 * 参数值
	 */
	@Getter
    @Setter
    private Object value;
	
}