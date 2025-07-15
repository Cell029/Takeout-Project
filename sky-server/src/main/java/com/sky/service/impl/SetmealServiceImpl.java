package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 向套餐表中提交数据
        setmealMapper.insert(setmeal);
        // 获取生成的套餐 id
        Long setmealId = setmeal.getId();
        // 获取要添加到套餐的菜品信息
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        // 给这些要添加到套餐中的菜品绑定套餐 id
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
        // 保存套餐和菜品的关联关系
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @CacheEvict(cacheNames = {"setmealCache", "dishItemCache"}, allEntries = true)
    public void startOrStop(Integer status, Long id) {
        // 根据套餐 id 查询当前套餐包含的菜品中的 status 是否有为 0 的
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        setmealDishes.forEach(setmealDish -> {
            if (Objects.equals(dishMapper.getById(setmealDish.getDishId()).getStatus(), StatusConstant.DISABLE)) {
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        });
        // 菜品的 status 全为 1，则正常起售
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                .build();
        setmealMapper.update(setmeal);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                // 起售中的套餐不能删除
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });
        // 删除套餐表中的数据
        setmealMapper.deleteByIds(ids);
        // 删除 setmeal_dish 表中的数据
        setmealDishMapper.deleteByIds(ids);
    }

    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    @CacheEvict(cacheNames = {"dishItemCache", "setmealCache"}, allEntries = true)
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 修改 setmeal 表
        setmealMapper.update(setmeal);
        // 获取套餐 id
        Long setmealId = setmealDTO.getId();
        // 删除该套餐中的 setmeal_dish 表中的内容
        setmealDishMapper.deleteById(setmealId);
        // 获取要添加到套餐的菜品信息
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        // 给这些要添加到套餐中的菜品绑定套餐 id
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
        // 新增对应的菜品到 setmeal_dish
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Override
    @Cacheable(value = "setmealCache", key = "#categoryId")
    public List<Setmeal> list(Long categoryId) {
        Setmeal setmeal = Setmeal.builder()
                .status(StatusConstant.ENABLE)
                .categoryId(categoryId)
                .build();
        List<Setmeal> setmealList = setmealMapper.list(setmeal);
        return setmealList;
    }

    @Override
    @Cacheable(value = "dishItemCache", key = "#id")
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

}
