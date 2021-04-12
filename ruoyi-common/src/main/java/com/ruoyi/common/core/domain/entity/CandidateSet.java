package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 考生组对象 candidate_set
 */
public class CandidateSet extends TreeEntity<CandidateSet> {
    private static final long serialVersionUID = 1L;

    /** 考生组id */
    private Long candidateSetId;

    /** 部门id */
    private Long deptId;

    /** 部门 */
    private SysDept dept;

    /** 组名称 */
    private String candidateSetName;

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public void setCandidateSetId(Long candidateSetId) {
        this.candidateSetId = candidateSetId;
    }

    public Long getCandidateSetId() {
        return candidateSetId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setCandidateSetName(String candidateSetName) {
        this.candidateSetName = candidateSetName;
    }

    public String getCandidateSetName() {
        return candidateSetName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("candidateSetId", getCandidateSetId())
                .append("deptId", getDeptId())
                .append("dept", getDept())
                .append("parentId", getParentId())
                .append("ancestors", getAncestors())
                .append("candidateSetName", getCandidateSetName())
                .append("orderNum", getOrderNum())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}