package com.boot.javactrl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.boot.javactrl.common.Result;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.boot.javactrl.service.IMenuService;
import com.boot.javactrl.entity.Menu;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张预阅
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    ///新增和修改
    //Post 请求 插入方法
    @PostMapping
    //RequestBody可以吧前台的Js对象转换后台的Java对象
    public Result save(@RequestBody Menu menu){
            //insert 表示插入 到 UserMapper.java
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        menuService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        menuService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "null") String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        //查询数据
        List<Menu> list = menuService.list(queryWrapper);
        // 找出pid 为 null 的一级菜单
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Menu menu : parentNode){
            //筛选 所以的数据的PId = 父级的数据就是二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return Result.success(parentNode);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
            return Result.success(menuService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
            QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("name",name);
            queryWrapper.orderByDesc("id");
            return Result.success(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}


