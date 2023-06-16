package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Admin;
import com.example.server.pojo.RespBean;
import com.example.server.pojo.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAdminService extends IService<Admin> {

    /**
     * @Description: 登陆之后返回 token
     * @Params:
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * @Description: 根据用户名获取用户
     * @Params:
     */
    Admin getAdminByUsername(String username);

    /**
     * @Description: 根据用户 id 查询角色列表
     * @Params:
     */
    List<Role> getRoles(Integer adminId);

    /**
     * @Description: 获取所有操作员
     * @Params:
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * @Description: 更新操作员角色
     * @Params:
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);

    /**
     * @Description: 更新用户密码
     * @param oldPass
     * @param pass
     * @param adminId
     */
    RespBean updateAdminPassword(String oldPass, String pass, Integer adminId);

    /**
     * @Description: 更新用户头像
     * @param url
     * @param id
     * @param authentication
     */
    RespBean updateAdminUserFace(String url, Integer id, Authentication authentication);
}
