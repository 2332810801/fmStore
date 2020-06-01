package com.fmjava.service;

import com.fmjava.core.pojo.log.PayLog;
import com.fmjava.core.pojo.order.Order;

public interface OrderService {
    //添加订单
    public void add(Order order);
    //根据用户名获取用户支付日志
    public PayLog getPayLogByUserName(String userName);
    //更新支付状态
    public void updatePayStatus(String userName);
}
