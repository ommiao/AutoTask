package cn.ommiao.base.entity.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.ommiao.base.entity.JavaBean;

public class Group extends JavaBean {

    public String groupId, groupName, groupDescription;

    public ArrayList<Order> orders;

    public int repeatTimes;

    public void addOrder(Order order){
        if(orders == null){
            this.orders = new ArrayList<>();
        }
        this.orders.add(order);
    }

    public void addOrders(List<Order> orders){
        if(this.orders == null){
            this.orders = new ArrayList<>();
        }
        this.orders.addAll(orders);
    }

    public void addOrders(Order... orders){
        if(this.orders == null){
            this.orders = new ArrayList<>();
        }
        this.orders.addAll(Arrays.asList(orders));
    }

}
