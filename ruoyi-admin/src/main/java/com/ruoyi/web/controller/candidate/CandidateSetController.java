package com.ruoyi.web.controller.candidate;

import java.util.Iterator;
import java.util.List;

import com.ruoyi.common.core.domain.entity.CandidateSet;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.system.service.ICandidateSetService;

/**
 * 考生组管理
 */
@Api("考生组管理")
@RestController
@RequestMapping("/candidate/set")
public class CandidateSetController extends BaseController {
    @Autowired
    private ICandidateSetService candidateSetService;

    @ApiOperation("获取考生组列表")
    @PreAuthorize("@ss.hasPermi('candidate:set:list')")
    @GetMapping("/list")
    public AjaxResult list(CandidateSet candidateSet) {
        List<CandidateSet> list = candidateSetService.selectCandidateSetList(candidateSet);
        return AjaxResult.success(list);
    }

    @ApiOperation("查询考生组列表（排除节点）")
    @PreAuthorize("@ss.hasPermi('candidate:set:list')")
    @GetMapping("/list/exclude/{candidateSetId}")
    public AjaxResult excludeChild(@PathVariable(value = "candidateSetId", required = false) Long candidateSetId) {
        // 取出全部 dept 数据，用迭代器遍历，对每条数据 d 如果 d.id=参数 或 d.ancestors 包含 参数，就删掉这个节点
        // 取出全部 dept 数据中排除 参数 和 参数子节点 的全部结点，用作更改父节点使用
        List<CandidateSet> candidateSets = candidateSetService.selectCandidateSetList(new CandidateSet());
        Iterator<CandidateSet> it = candidateSets.iterator();
        while (it.hasNext()) {
            CandidateSet d = (CandidateSet) it.next();
            if (d.getDeptId().intValue() == candidateSetId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), candidateSetId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(candidateSets);
    }

    @ApiOperation("获取考生组详细信息")
    @PreAuthorize("@ss.hasPermi('candidate:set:query')")
    @GetMapping(value = "/{candidateSetId}")
    public AjaxResult getInfo(@PathVariable("candidateSetId") Long candidateSetId) {
        return AjaxResult.success(candidateSetService.selectCandidateSetById(candidateSetId));
    }

    @ApiOperation("获取下拉树列表（考生管理展示需要）")
    @GetMapping(value = "/treeselect")
    public AjaxResult treeselect(CandidateSet candidateSet) {
        List<CandidateSet> candidateSets = candidateSetService.selectCandidateSetList(candidateSet);
        return AjaxResult.success(candidateSetService.buildCandidateSetTreeSelect(candidateSets));
    }

    @ApiOperation("新增考生组")
    @PreAuthorize("@ss.hasPermi('candidate:set:add')")
    @Log(title = "考生组管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody CandidateSet candidateSet) {
        candidateSet.setCreateBy(SecurityUtils.getUsername());
        return toAjax(candidateSetService.insertCandidateSet(candidateSet));
    }

    @ApiOperation("修改考生组")
    @PreAuthorize("@ss.hasPermi('candidate:set:edit')")
    @Log(title = "考生组管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CandidateSet candidateSet) {
        if (candidateSet.getParentId().equals(candidateSet.getCandidateSetId())) {
            return AjaxResult.error("修改考生组'" + candidateSet.getCandidateSetName() + "'失败，上级目录不能是自己");
        }
        candidateSet.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(candidateSetService.updateCandidateSet(candidateSet));
    }

    @ApiOperation("删除考生组")
    @PreAuthorize("@ss.hasPermi('candidate:set:remove')")
    @Log(title = "考生组管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{candidateSetId}")
    public AjaxResult remove(@PathVariable Long candidateSetId) {
        if (candidateSetService.hasChildById(candidateSetId)) {
            return AjaxResult.error("存在子目录，不允许删除");
        }
        if (candidateSetService.checkCandidateSetNotEmpty(candidateSetId)) {
            // todo
            return AjaxResult.error("存在考生，级联删除逻辑未完成");
        }
        return toAjax(candidateSetService.deleteCandidateSetById(candidateSetId));
    }
}
