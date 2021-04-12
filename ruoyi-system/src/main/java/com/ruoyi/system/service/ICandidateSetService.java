package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.CandidateSet;

/**
 * 考生组管理Service接口
 */
public interface ICandidateSetService {

    CandidateSet selectCandidateSetById(Long candidateSetId);
    List<CandidateSet> selectCandidateSetList(CandidateSet candidateSet);
    List<TreeSelect> buildCandidateSetTreeSelect(List<CandidateSet> candidateSets);
    boolean hasChildById(Long candidateSetId);
    boolean checkCandidateSetNotEmpty(Long candidateSetId);
    int insertCandidateSet(CandidateSet candidateSet);
    int updateCandidateSet(CandidateSet candidateSet);
    int deleteCandidateSetById(Long candidateSetId);

}
