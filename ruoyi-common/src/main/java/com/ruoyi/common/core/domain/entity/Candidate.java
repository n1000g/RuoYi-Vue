package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 考生实体 candidate
 * 登陆认证需要，被 framework 依赖，故不在 system 而在 common
 */
public class Candidate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 考生ID */
    @Excel(name = "考生序号", cellType = Excel.ColumnType.NUMERIC, prompt = "顺序编号")
    private Long candidateId;

    /** 组ID */
    @Excel(name = "考生组序号", type = Excel.Type.IMPORT)
    private Long candidateSetId;

    /** 考生组 */
    @Excel(name = "考生组名", targetAttr = "candidateSetName", type = Excel.Type.EXPORT)
    private CandidateSet candidateSet;

    /** 用户名（考号） */
    @Excel(name = "考生号")
    private String username;

    /** 密码 */
    private String password;

    /** 考生姓名 */
    @Excel(name = "考生姓名")
    private String candidateName;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getCandidateSetId() {
        return candidateSetId;
    }

    public void setCandidateSetId(Long candidateSetId) {
        this.candidateSetId = candidateSetId;
    }

    public CandidateSet getCandidateSet() {
        return candidateSet;
    }

    public void setCandidateSet(CandidateSet candidateSet) {
        this.candidateSet = candidateSet;
    }

    @NotBlank(message = "用户名不能为空")
    @Size(min = 0, max = 30, message = "用户名长度不能超过30个字符")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("candidateId", getCandidateId())
            .append("candidateSetId", getCandidateSetId())
            .append("candidateSet", getCandidateSet())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("candidateName", getCandidateName())
            .append("email", getEmail())
            .append("phone", getPhone())
            .toString();
    }
}
