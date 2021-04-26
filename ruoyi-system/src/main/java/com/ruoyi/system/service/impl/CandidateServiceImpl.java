package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.Candidate;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.CandidateMapper;
import com.ruoyi.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.ICandidateService;

/**
 * 考生信息Service业务层处理
 */
@Service
public class CandidateServiceImpl implements ICandidateService {

    private static final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private ISysConfigService configService;

    @Override
    public Candidate selectCandidateById(Long candidateId) {
        return candidateMapper.selectCandidateById(candidateId);
    }

    @Override
    public Candidate selectCandidateByUsername(String username) {
        return candidateMapper.selectCandidateByUsername(username);
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<Candidate> selectCandidateList(Candidate candidate) {
        return candidateMapper.selectCandidateList(candidate);
    }

    @Override
    public int insertCandidate(Candidate candidate) {
        return candidateMapper.insertCandidate(candidate);
    }

    @Override
    public int updateCandidate(Candidate candidate) {
        return candidateMapper.updateCandidate(candidate);
    }

    @Override
    public int deleteCandidateByIds(Long[] candidateIds) {
        int res = 0;
        for (Long candidateId : candidateIds) {
            res += deleteCandidateById(candidateId);
        }
        return res;
    }

    private int deleteCandidateById(Long candidateId) {
        return candidateMapper.deleteCandidateById(candidateId);
    }

    @Override
    public int resetPwd(Candidate candidate) {
        return candidateMapper.updateCandidate(candidate);
    }

    @Override
    public int resetPwd(String username, String password) {
        return candidateMapper.resetCandidatePwd(username, password);
    }

    @Override
    public boolean checkCandidateUsernameNotUnique(String username) {
        return candidateMapper.checkUsernameUnique(username) > 0;
    }

    @Override
    public String importCandidate(List<Candidate> candidateList, boolean updateSupport) {
        if (StringUtils.isNull(candidateList) || candidateList.size() == 0) {
            throw new CustomException("导入考生数据不能为空");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("candidate.initPassword");
        for (Candidate candidate : candidateList) {
            try {
                Candidate c = candidateMapper.selectCandidateByUsername(candidate.getUsername());
                if (StringUtils.isNull(c)) {
                    candidate.setPassword(SecurityUtils.encryptPassword(password));
                    this.insertCandidate(candidate);
                    successNum++;
                    successMsg.append("<br/>")
                            .append(successNum)
                            .append("、考生号 ")
                            .append(candidate.getUsername())
                            .append("导入成功");
                } else if (updateSupport) {
                    this.updateCandidate(candidate);
                    successNum++;
                    successMsg.append("<br/>")
                            .append(successNum)
                            .append("、考生号 ")
                            .append(candidate.getUsername())
                            .append(" 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>")
                            .append(failureNum)
                            .append("、考生 ")
                            .append(candidate.getUsername())
                            .append(" 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                failureMsg.append("<br/>")
                        .append(failureNum)
                        .append("、考生 ")
                        .append(candidate.getUsername())
                        .append(" 导入失败：")
                        .append(e.getMessage());
                log.error(failureMsg.toString(), e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
