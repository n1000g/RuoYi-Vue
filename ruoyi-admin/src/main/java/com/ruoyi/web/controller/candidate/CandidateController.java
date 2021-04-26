package com.ruoyi.web.controller.candidate;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.domain.entity.Candidate;
import com.ruoyi.system.service.ICandidateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

@Api("考生管理")
@RestController
@RequestMapping("/candidate/candidate")
public class CandidateController extends BaseController {

    @Autowired
    private ICandidateService candidateService;

    @ApiOperation("获取考生列表")
    @PreAuthorize("@ss.hasPermi('candidate:candidate:list')")
    @GetMapping("/list")
    public TableDataInfo list(Candidate candidate) {
        startPage();
        List<Candidate> list = candidateService.selectCandidateList(candidate);
        return getDataTable(list);
    }

    @ApiOperation("导出考生列表")
    @PreAuthorize("@ss.hasPermi('candidate:candidate:export')")
    @Log(title = "考生管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Candidate candidate) {
        List<Candidate> list = candidateService.selectCandidateList(candidate);
        ExcelUtil<Candidate> util = new ExcelUtil<Candidate>(Candidate.class);
        return util.exportExcel(list, "考生数据");
    }

    @ApiOperation("导入考生列表")
    @Log(title = "考生管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('candidate:candidate:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Candidate> util = new ExcelUtil<Candidate>(Candidate.class);
        List<Candidate> candidateList = util.importExcel(file.getInputStream());
        String message = candidateService.importCandidate(candidateList, updateSupport);
        return AjaxResult.success(message);
    }

    @ApiOperation("获取导入模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<Candidate> util = new ExcelUtil<Candidate>(Candidate.class);
        return util.importTemplateExcel("考生数据");
    }

    @ApiOperation("获取考生详细信息")
    @PreAuthorize("@ss.hasPermi('candidate:candidate:query')")
    @GetMapping(value = "/{candidateId}")
    public AjaxResult getInfo(@PathVariable("candidateId") Long candidateId) {
        return AjaxResult.success(candidateService.selectCandidateById(candidateId));
    }

    @ApiOperation("新增考生")
    @PreAuthorize("@ss.hasPermi('candidate:candidate:add')")
    @Log(title = "考生管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Candidate candidate) {
        if (candidateService.checkCandidateUsernameNotUnique(candidate.getUsername())) {
            return AjaxResult.error("新增考生'" + candidate.getUsername() + "'失败，考生号已存在");
        }
        candidate.setPassword(SecurityUtils.encryptPassword(candidate.getPassword()));
        return toAjax(candidateService.insertCandidate(candidate));
    }

    @ApiOperation("修改考生")
    @PreAuthorize("@ss.hasPermi('candidate:candidate:edit')")
    @Log(title = "考生管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Candidate candidate) {
        return toAjax(candidateService.updateCandidate(candidate));
    }

    @ApiOperation("批量删除考生")
    @PreAuthorize("@ss.hasPermi('candidate:candidate:remove')")
    @Log(title = "考生管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{candidateIds}")
    public AjaxResult remove(@PathVariable Long[] candidateIds) {
        return toAjax(candidateService.deleteCandidateByIds(candidateIds));
    }

    @ApiOperation("重置密码")
    @PreAuthorize("@ss.hasPermi('candidate:candidate:restPwd')")
    @Log(title = "考生管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody Candidate candidate) {
        candidate.setPassword(SecurityUtils.encryptPassword(candidate.getPassword()));
        return toAjax(candidateService.resetPwd(candidate));
    }
}
