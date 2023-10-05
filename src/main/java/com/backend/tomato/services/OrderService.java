package com.backend.tomato.services;

import com.backend.tomato.dao.OrderDao;
import com.backend.tomato.entitites.MyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public void saveOrder(MyOrder myOrder) {
        this.orderDao.save(myOrder);
    }
}
