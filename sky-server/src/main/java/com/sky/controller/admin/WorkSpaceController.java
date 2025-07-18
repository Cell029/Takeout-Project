package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RestController
@Tag(name = "工作台相关接口")
@RequestMapping("/admin/workspace")
public class WorkSpaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping("/businessData")
    @Operation(summary = "工作台今日数据查询")
    public Result<BusinessDataVO> businessData(){
        // 获得当天的开始时间
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, null);
        return Result.success(businessDataVO);
    }

    @GetMapping("/overviewOrders")
    @Operation(summary = "查询订单管理数据")
    public Result<OrderOverViewVO> orderOverView(){
        return Result.success(workspaceService.getOrderOverView());
    }

    @GetMapping("/overviewDishes")
    @Operation(summary = "查询菜品总览")
    public Result<DishOverViewVO> dishOverView(){
        return Result.success(workspaceService.getDishOverView());
    }

    @GetMapping("/overviewSetmeals")
    @Operation(summary = "查询套餐总览")
    public Result<SetmealOverViewVO> setmealOverView(){
        return Result.success(workspaceService.getSetmealOverView());
    }

}
