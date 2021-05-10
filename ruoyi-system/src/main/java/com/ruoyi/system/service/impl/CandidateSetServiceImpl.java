package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CandidateSetMapper;
import com.ruoyi.common.core.domain.entity.CandidateSet;
import com.ruoyi.system.service.ICandidateSetService;

/**
 * 考生组管理Service业务层处理
 */
@Service
public class CandidateSetServiceImpl implements ICandidateSetService {

    @Autowired
    private CandidateSetMapper candidateSetMapper;

    @Override
    public CandidateSet selectCandidateSetById(Long candidateSetId) {
        return candidateSetMapper.selectCandidateSetById(candidateSetId);
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<CandidateSet> selectCandidateSetList(CandidateSet candidateSet) {
        return candidateSetMapper.selectCandidateSetList(candidateSet);
    }

    @Override
    public List<TreeSelect> buildCandidateSetTreeSelect(List<CandidateSet> candidateSets) {
        List<CandidateSet> csTrees = buildCandidateSetTree(candidateSets);
        return csTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /** 整理成能够构建 TreeSelect 的结构 */
    private List<CandidateSet> buildCandidateSetTree(List<CandidateSet> candidateSets) {
        List<CandidateSet> returnList = new ArrayList<CandidateSet>();
        List<Long> tempList = new ArrayList<Long>();
        for (CandidateSet candidateSet : candidateSets) {
            tempList.add(candidateSet.getCandidateSetId());
        }
        for (CandidateSet candidateSet : candidateSets) {
            if (!tempList.contains(candidateSet.getParentId())) {
                recursionFn(candidateSets, candidateSet);
                returnList.add(candidateSet);
            }
        }
        if (returnList.isEmpty()) returnList = candidateSets;
        return returnList;
    }

    private void recursionFn(List<CandidateSet> list, CandidateSet cs) {
        List<CandidateSet> childList = getChildList(list, cs);
        cs.setChildren(childList);
        for (CandidateSet csChild : childList) {
            if (getChildList(list, csChild).size() > 0) {
                recursionFn(list, csChild);
            }
        }
    }

    private List<CandidateSet> getChildList(List<CandidateSet> list, CandidateSet cs) {
        List<CandidateSet> csList = new ArrayList<>();
        for (CandidateSet n : list) {
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == cs.getCandidateSetId().longValue()) {
                csList.add(n);
            }
        }
        return csList;
    }

    @Override
    public boolean hasChildById(Long candidateSetId) {
        return candidateSetMapper.hasChildById(candidateSetId) > 0;
    }

    @Override
    public boolean checkCandidateSetNotEmpty(Long candidateSetId) {
        return candidateSetMapper.checkCandidateSetEmpty(candidateSetId) > 0;
    }

    @Override
    public int insertCandidateSet(CandidateSet candidateSet) {
        CandidateSet parent = candidateSetMapper.selectCandidateSetById(candidateSet.getParentId());
        candidateSet.setAncestors(parent.getAncestors() + "," + candidateSet.getParentId());
        return candidateSetMapper.insertCandidateSet(candidateSet);
    }

    @Override
    public int updateCandidateSet(CandidateSet candidateSet) {
        CandidateSet newParent = candidateSetMapper.selectCandidateSetById(candidateSet.getParentId());
        CandidateSet oldCs = candidateSetMapper.selectCandidateSetById(candidateSet.getCandidateSetId());
        if (StringUtils.isNotNull(newParent) && StringUtils.isNotNull(oldCs)) {
            String newAncestors = newParent.getAncestors() + "," + newParent.getCandidateSetId();
            String oldAncestors = oldCs.getAncestors();
            candidateSet.setAncestors(newAncestors);
            updateCandidateSetChildren(candidateSet.getCandidateSetId(), newAncestors, oldAncestors);
        }
        return candidateSetMapper.updateCandidateSet(candidateSet);
    }

    private void updateCandidateSetChildren(Long candidateSetId, String newAncestors, String oldAncestors) {
        List<CandidateSet> children = candidateSetMapper.selectChildrenCandidateSetById(candidateSetId);
        for (CandidateSet child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            candidateSetMapper.updateCandidateSetChildren(children);
        }
    }

    @Override
    public int deleteCandidateSetById(Long candidateSetId) {
        return candidateSetMapper.deleteCandidateSetById(candidateSetId);
    }
}
