package com.cmazxiaoma.user.service;

import com.cmazxiaoma.framework.base.exception.BusinessException;
import com.cmazxiaoma.framework.base.exception.ServiceException;
import com.cmazxiaoma.framework.common.page.PagingResult;
import com.cmazxiaoma.framework.common.search.Search;
import com.cmazxiaoma.framework.util.StringUtil;
import com.cmazxiaoma.user.dao.UserBasicInfoDAO;
import com.cmazxiaoma.user.dao.UserDAO;
import com.cmazxiaoma.user.entity.User;
import com.cmazxiaoma.user.entity.UserBasicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.sql.rowset.serial.SerialException;
import java.util.Date;
import java.util.Objects;

/**
 * UserService
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserBasicInfoDAO basicInfoDAO;

    public User login(User user) throws ServiceException {
        //验证
        if (null == user) {
            throw new ServiceException("user不能为空");
        }

        if (StringUtils.isEmpty(user.getName())) {
            throw new ServiceException("账号不能为空");
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            throw new ServiceException("密码不能为空");
        }

        User dbUser = userDAO.getByName(user.getName());

        if (null == dbUser) {
            throw new ServiceException("该用户不存在");
        }

        //校验密码

        //用户输入的密码
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).toUpperCase();

        if (!Objects.equals(password, dbUser.getPassword())) {
            throw new ServiceException("密码不正确");
        }

        User tempUser = new User();
        tempUser.setId(dbUser.getId());
        tempUser.setLoginTime(new Date());
        updateByIdSelective(tempUser);
        return dbUser;
    }

    /**
     * 根据用户ID查询用户基本信息
     *
     * @param userId
     * @return
     */
    public UserBasicInfo getBasicInfoByUserId(Long userId) {
        return basicInfoDAO.getById(userId);
    }

    /**
     * 插入用户基本信息
     *
     * @param basicInfo
     * @param userId
     */
    public void saveBasicInfo(UserBasicInfo basicInfo, Long userId) {
        Date now = new Date();
        basicInfo.setCreateTime(now);
        basicInfo.setUpdateTime(now);
        basicInfo.setId(userId);
        basicInfoDAO.save(basicInfo);
//		CommonMybatisDAO.save(UserBasicInfo.SAVE_SQL_ID, basicInfo);
//		CommonMybatisDAO.save(User.SAVE_SQL_ID, new User());
//		CommonMybatisDAO.findAll(Cart.FIND_SQL_ID, Cart.class);
    }

    /**
     * 更新用户基本信息
     *
     * @param basicInfo
     */
    public void updateBasicInfo(UserBasicInfo basicInfo) {
        basicInfo.setUpdateTime(new Date());
        basicInfoDAO.updateById(basicInfo);
    }

    /**
     * 通过ID获取用户
     *
     * @param id
     * @return
     */
    public User getById(Long id) {
        return userDAO.getById(id);
    }


    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    public User getByUsername(String username) {
        return userDAO.getByName(username);
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    public boolean register(final User user) {
        final String username = user.getName();

        if (StringUtils.isEmpty(user.getName())) {
            throw new ServiceException("账号不能为空");
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            throw new ServiceException("密码不能为空");
        }

        if (StringUtils.isEmpty(user.getRepwd())) {
            throw new ServiceException("重复密码不能为空");
        }

        if (!Objects.equals(user.getPassword(), user.getRepwd())) {
            throw new ServiceException("两次密码输入不一致");
        }

        if (!"on".equalsIgnoreCase(user.getChecked())) {
            throw new ServiceException("请勾选DCAMPUS协议");
        }

        User temp = getByUsername(username);

        if (temp != null) {
            throw new BusinessException("用户名已经被注册过！");
        }

        // 保存用户基本信息
        Date nowTime = new Date();
        user.setCreateTime(nowTime);
        user.setUpdateTime(nowTime);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).toUpperCase());
        user.setLoginTime(nowTime);

        int effectRows = userDAO.save(user);

        if (effectRows == 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    public int updateByIdSelective(User user) {
        if (user == null || user.getId() == 0) {
            return 0;
        }
        user.setUpdateTime(new Date());
        return userDAO.updateById(user);
    }

    /**
     * 搜索用户
     *
     * @param search
     * @return
     */
    public PagingResult<User> findUserForPage(Search search) {
        return userDAO.findUserForPage(search);
    }

}