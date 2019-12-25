package com.gj.userservice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gj.entitys.PmsBaseAttrInfo;
import com.gj.entitys.PmsBaseCatalog1;
import com.gj.entitys.PmsBaseCatalog2;
import com.gj.services.Caterlog2Service;
import com.gj.services.Caterlog3Service;
import com.gj.services.CaterlogService;
import com.gj.util.PageUtils;
import com.gj.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caterlog")
public class CatarlogController {

    @Reference
    CaterlogService caterlogService;

    @Reference
    Caterlog2Service caterlog2Service;

    @Reference
    Caterlog3Service caterlog3Service;

    @RequestMapping(value = "/getCatalog1", method = RequestMethod.POST)
    public R getCatalog1(){
        List<PmsBaseCatalog1> catalog1s = caterlogService.selectList(null);
        System.out.println("*** " + catalog1s);

        return R.ok().put("catalog1s", catalog1s);
    }

    @RequestMapping(value = "/getCatalog2", method = RequestMethod.POST)
    public R getCatalog2(@RequestParam("catalog1Id") String catalog1Id){
        System.out.println("*** " + catalog1Id + " --- " + catalog1Id.getClass().getName());
        boolean catalog1Ids = StringUtils.isNotBlank(catalog1Id);
        if (catalog1Ids) {
            PmsBaseCatalog2 pmsBaseCatalog2 = caterlog2Service.selectById(Integer.parseInt(catalog1Id));
            System.out.println("****** pmsBaseCatalog2 = " + pmsBaseCatalog2);
            return R.ok().put("catalog2s", pmsBaseCatalog2);
        }
        return R.error("二级分类的ID不能为空！");
    }

    @RequestMapping(value = "/getCatalog3", method = RequestMethod.POST)
    public R getCaterlog3(@RequestParam("catalog1Id") String catalog2Id){
        System.out.println("*** " + catalog2Id + " --- " + catalog2Id.getClass().getName());

        return R.ok();
    }

    @RequestMapping(value = "/attrInfoList",method = RequestMethod.GET)
    public R getAttrInfoList(@RequestBody String catalog3Id){
        System.out.println("****** catalog3Id = " + catalog3Id);

        return R.ok();
    }

    @RequestMapping(value = "/attrValueList",method = RequestMethod.POST)
    public R getListById(@RequestBody String attrId){
        System.out.println("****** attrId = " + attrId);

        return R.ok();
    }

    @RequestMapping(value = "/saveAttrInfo",method = RequestMethod.POST)
    public R updateAttriInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        System.out.println("****** pmsBaseAttrInfo = " + pmsBaseAttrInfo.toString());

        return R.ok();
    }

    @RequestMapping(value = "/deleteAttr",method = RequestMethod.DELETE)
    public R deleteAttriInfo(@PathVariable String attrId){
        System.out.println("****** attrId =  " + attrId);

        return R.ok();
    }

}
