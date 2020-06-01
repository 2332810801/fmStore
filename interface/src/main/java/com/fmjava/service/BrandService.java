package com.fmjava.service;

import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.good.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    public List<Brand> findAllBrand();

    PageResult findPage(Integer page, Integer pageSize,Brand brand);

    void add(Brand brand);

    Brand findOne(Long id);

    void update(Brand brand);

    void deleteBrand(Long[] ids);

    List<Map> selectOptionList();


}
