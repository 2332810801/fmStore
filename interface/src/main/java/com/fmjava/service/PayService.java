package com.fmjava.service;

import java.util.Map;

public interface PayService {
    /**
     * 获取支付信息
     * @param outTradeNo 订单号
     * @param totalFee  支付金额
     * @return   支付信息
     */
    public Map createNative(String outTradeNo, String totalFee);

    /**
     * 根据订单号, 查询支付状态
     * @param out_trade_no
     * @return
     */
    public Map queryPayStatus(String out_trade_no);
}

