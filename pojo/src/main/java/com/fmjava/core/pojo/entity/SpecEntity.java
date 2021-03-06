package com.fmjava.core.pojo.entity;

import com.fmjava.core.pojo.specification.Specification;
import com.fmjava.core.pojo.specification.SpecificationOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SpecEntity implements Serializable {
    //规格对象
    private Specification specification;
    //规格选项集合
    private List<SpecificationOption> specOptionList;
}
