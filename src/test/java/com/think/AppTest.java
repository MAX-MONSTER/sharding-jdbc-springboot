package com.think;

import com.think.dao.HanUserMapper;
import com.think.entity.HanUser;
import com.think.main.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.think.dao.OrderMapper;
import com.think.entity.TOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class AppTest {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private HanUserMapper hanUserMapper;

    @Test
    public void test() {
        long l = System.currentTimeMillis();
        System.out.println(l);
        for (int i = 18888; i < 19999; i++) {
            TOrder order = new TOrder();
            order.setUserId(i);
            order.setOrderId(i);
            orderMapper.insert(order);
        }
        long e = System.currentTimeMillis();
        System.out.println((e-l)/1000);
        TOrder order = orderMapper.findById(3000);
        HanUser byId = hanUserMapper.findById(1);
        System.out.println(byId.getId() + "" + byId.getName());
    }

    @Test
    public void test2() {
        TOrder order = orderMapper.findById(220);
        System.out.println(order.getOrderId() + "" + order.getUserId());
    }

}
