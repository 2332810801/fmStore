package com.fmjava.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.specification.SpecificationDao;
import com.fmjava.core.dao.specification.SpecificationOptionDao;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.SpecEntity;
import com.fmjava.core.pojo.good.Brand;
import com.fmjava.core.pojo.good.BrandQuery;
import com.fmjava.core.pojo.specification.Specification;
import com.fmjava.core.pojo.specification.SpecificationOption;
import com.fmjava.core.pojo.specification.SpecificationOptionQuery;
import com.fmjava.core.pojo.specification.SpecificationQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpecServiceImpl implements SpecService  {
    @Autowired
    private SpecificationDao specDao;

    @Autowired
    private SpecificationOptionDao optionDao;

    @Override
    public PageResult findPage(Integer page, Integer pageSize, Specification spec) {
        PageHelper.startPage(page,pageSize);
        SpecificationQuery specQuery = new SpecificationQuery();
        if (spec != null){
            SpecificationQuery.Criteria criteria = specQuery.createCriteria();
            if (spec.getSpecName() !=null && !"".equals(spec.getSpecName())){
               criteria.andSpecNameLike("%"+spec.getSpecName()+"%");
            }
        }
        Page<Specification> brandList = (Page<Specification>)specDao.selectByExample(specQuery);
        return new PageResult(brandList.getResult(),brandList.getTotal());
    }

    @Override
    public void add(SpecEntity specEntity) {
        //保存规格
        specDao.insertSelective(specEntity.getSpecification());
        //取出每一个规格  设置id 保存规格
        for (SpecificationOption specificationOption : specEntity.getSpecOptionList()) {
            //设置specId
            specificationOption.setSpecId(specEntity.getSpecification().getId());

            //保存规格选项
            optionDao.insertSelective(specificationOption);
        }

    }

    @Override
    public SpecEntity findOne(Long id) {
        //1.根据id查出规格
        Specification specification = specDao.selectByPrimaryKey(id);
        //2.根据id 查询规格选项对应 的集合
        SpecificationOptionQuery optionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = optionQuery.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<SpecificationOption> specificationOptions = optionDao.selectByExample(optionQuery);
        //3.封装成实体
        SpecEntity specEntity = new SpecEntity();
        specEntity.setSpecification(specification);
        specEntity.setSpecOptionList(specificationOptions);
        return specEntity;
    }

    @Override
    public void update(SpecEntity specEntity) {
        //1.更新规格对象
        specDao.updateByPrimaryKeySelective(specEntity.getSpecification());
        //2.先打破之前关系, 根据id删除对应的规格
        SpecificationOptionQuery optionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = optionQuery.createCriteria();
        criteria.andSpecIdEqualTo(specEntity.getSpecification().getId());
        optionDao.deleteByExample(optionQuery);
       if (specEntity.getSpecOptionList() != null){

           for (SpecificationOption specificationOption : specEntity.getSpecOptionList()) {
               //设置specId
               specificationOption.setSpecId(specEntity.getSpecification().getId());               //保存规格选项
               optionDao.insertSelective(specificationOption);
           }

       }

    }

    @Override
    public void delete(Long[] ids) {
        if (ids !=null){
            for (Long id : ids) {
                //1.删除规格
                specDao.deleteByPrimaryKey(id);
                //2.删除规格选项
                SpecificationOptionQuery optionQuery = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = optionQuery.createCriteria();
                criteria.andSpecIdEqualTo(id);
                optionDao.deleteByExample(optionQuery);
            }
        }
    }

    @Override
    public List<Map> selectOptionList() {
            return specDao.selectOptionList();
    }

}
