package com.tensquare.spit.controller;


import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin

@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }


    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@RequestBody Spit spit, @PathVariable String spitId){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size){
        Page<Spit> pagedate = spitService.findByParentid(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pagedate.getTotalElements(),pagedate.getContent()));
    }

    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String spitId){
        String userID = "111";
        if(redisTemplate.opsForValue().get("thumbup_"+userID+"_"+spitId)!=null){
            return new Result(false,StatusCode.ERROR,"不能重复点赞");
        }
        spitService.updateThumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userID+"_"+spitId,"1");
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
