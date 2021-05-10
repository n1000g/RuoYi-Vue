package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.CandidateSet;
import org.apache.ibatis.annotations.Param;

/**
 * 考生组 Mapper 接口
 */
public interface CandidateSetMapper {
    /** 根据ID查询考生组信息（伪需求） */
    CandidateSet selectCandidateSetById(Long candidateSetId);

    /** 查询考生组列表（父节点ID、名称） */
    List<CandidateSet> selectCandidateSetList(CandidateSet candidateSet);

    /** 根据ID查询考生组全部子列表信息（真需求） */
    List<CandidateSet> selectChildrenCandidateSetById(Long candidateSetId);

    /** 有无子节点 */
    int hasChildById(Long candidateSetId);

    /** 检查考生组下有无考生（建索引） */
    int checkCandidateSetEmpty(Long candidateSetId);

    /** 修改子元素关系 */
    int updateCandidateSetChildren(@Param("candidateSets") List<CandidateSet> candidateSets);

    /** 新增考生组 */
    int insertCandidateSet(CandidateSet candidateSet);

    /** 修改考生组 */
    int updateCandidateSet(CandidateSet candidateSet);

    /** 删除考生组 */
    int deleteCandidateSetById(Long candidateSetId);
}
