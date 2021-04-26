package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.Candidate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考生信息Mapper接口
 */
public interface CandidateMapper {
    List<Candidate> selectCandidateList(Candidate candidate);
    Candidate selectCandidateById(Long candidateId);
    Candidate selectCandidateByUsername(String username);
    int insertCandidate(Candidate candidate);
    int updateCandidate(Candidate candidate);
    int resetCandidatePwd(@Param("username") String username, @Param("password") String password);
    int deleteCandidateById(Long candidateId);
    int checkUsernameUnique(String username);
}
