package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.Candidate;

import java.util.List;

/**
 * 考生信息Service接口
 */
public interface ICandidateService {
    Candidate selectCandidateById(Long candidateId);
    Candidate selectCandidateByUsername(String username);
    List<Candidate> selectCandidateList(Candidate candidate);
    int insertCandidate(Candidate candidate);
    int updateCandidate(Candidate candidate);
    int deleteCandidateByIds(Long[] candidateIds);
    int resetPwd(Candidate candidate);
    int resetPwd(String username, String password);
    boolean checkCandidateUsernameNotUnique(String username);
    String importCandidate(List<Candidate> candidateList, boolean updateSupport);
}
