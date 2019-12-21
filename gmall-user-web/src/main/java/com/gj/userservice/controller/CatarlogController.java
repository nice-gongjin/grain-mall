package com.gj.userservice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gj.entitys.PmsBaseAttrInfo;
import com.gj.services.Caterlog2Service;
import com.gj.services.Caterlog3Service;
import com.gj.services.CaterlogService;
import com.gj.util.PageUtils;
import com.gj.util.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caterlog")
@CrossOrigin
public class CatarlogController {

    @Reference
    CaterlogService caterlogService;

    @Reference
    Caterlog2Service caterlog2Service;

    @Reference
    Caterlog3Service caterlog3Service;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R getCaterlog(){
        PageUtils caterlogs = caterlogService.getCaterlogs();
        System.out.println("*** " + caterlogs.toString());

        return R.ok().put("caterlog", caterlogs);
    }

    @RequestMapping(value = "/list2", method = RequestMethod.POST)
    public R getCaterlog2(){
        PageUtils caterlogs = caterlog2Service.getCaterlogs2();
        System.out.println("*** " + caterlogs.toString());

        return R.ok().put("caterlog2", caterlogs);
    }

    @RequestMapping(value = "/list3", method = RequestMethod.POST)
    public R getCaterlog3(){
        PageUtils caterlogs = caterlog3Service.getCaterlogs3();
        System.out.println("*** " + caterlogs.toString());

        return R.ok().put("caterlog3", caterlogs);
    }

    @RequestMapping()
    public R saveAttriInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        System.out.println("****** pmsBaseAttrInfo = " + pmsBaseAttrInfo.toString());

        return R.ok();
    }

    @RequestMapping()
    public R updateAttriInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        System.out.println("****** pmsBaseAttrInfo = " + pmsBaseAttrInfo.toString());

        return R.ok();
    }

}
