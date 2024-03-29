package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * 查询全部
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * 根据Id查询
     * @param labelId
     * @return
     */

    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(labelId));
    }

    /**
     * 增加标签
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    @RequestMapping(value = "/{labelId}")
    public Result update(@RequestBody Label label, @PathVariable String labelId){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> list = labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/search/{page}/{size}")
    public Result pageQuery(@RequestBody Label label, @PathVariable int page, @PathVariable int size){
        Page<Label> pageData = labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",  new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }
}
