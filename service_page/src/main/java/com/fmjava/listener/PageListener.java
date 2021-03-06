package com.fmjava.listener;

import com.fmjava.service.CmsService;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.Serializable;

public class PageListener implements MessageListener {
    @Autowired
    private CmsService cmsService;

    @Override
    public void onMessage(Message message) {
        ActiveMQObjectMessage atm  = (ActiveMQObjectMessage)message;
        try {
            Long[] ids = (Long[]) atm.getObject();
            for (Long id : ids) {
                cmsService.createStaticPage(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
