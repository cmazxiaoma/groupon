package com.cmazxiaoma.support.help.service;

import java.util.List;

import com.cmazxiaoma.support.help.dao.HelpInfoDAO;
import com.cmazxiaoma.support.help.entity.HelpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * HelpInfoService
 */
@Service
public class HelpInfoService {

    @Autowired
    private HelpInfoDAO helpInfoDAO;

    /**
     * 获取在页面上显示的帮助信息
     *
     * @return
     */
    public List<HelpInfo> getListForShow() {
        return helpInfoDAO.getListForShow();
    }
}