package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 用户 业务层
 */
public interface ISysUserService {
    List<SysUser> selectUserList(SysUser user);
    SysUser selectUserByUserName(String userName);
    SysUser selectUserById(Long userId);
    String selectUserRoleGroup(String userName);
    String selectUserPostGroup(String userName);
    String checkUserNameUnique(String userName);
    String checkPhoneUnique(SysUser user);
    String checkEmailUnique(SysUser user);
    void checkUserAllowed(SysUser user);
    int insertUser(SysUser user);
    int updateUser(SysUser user);
    int updateUserStatus(SysUser user);
    int updateUserProfile(SysUser user);
    boolean updateUserAvatar(String userName, String avatar);
    int resetPwd(SysUser user);
    int resetUserPwd(String userName, String password);
    int deleteUserById(Long userId);
    int deleteUserByIds(Long[] userIds);
    String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);
}
