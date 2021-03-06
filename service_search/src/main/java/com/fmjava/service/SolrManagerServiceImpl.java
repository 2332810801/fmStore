package com.fmjava.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;

import java.util.List;
@Service
public class SolrManagerServiceImpl implements SolrManagerService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public void saveItemToSolr(List list) {
        if (list !=null){
            solrTemplate.saveBeans(list);
            solrTemplate.commit();
        }
    }

    @Override
    public void deleteItemByGoodsId(List goodsIds) {
        if (goodsIds != null){
            //创建查询对象
            Query query = new SimpleQuery();
            Criteria criteria = new Criteria("item_goodsid").in(goodsIds);
            query.addCriteria(criteria);
            solrTemplate.delete(query);
            solrTemplate.commit();
        }
    }


}
