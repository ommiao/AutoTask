package cn.ommiao.autotask.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

import cn.ommiao.base.entity.order.Group;
import cn.ommiao.base.entity.order.Order;

public class SectionOfOrder extends SectionEntity<Order> {

    public Group groupOfOrder;

    public SectionOfOrder(boolean isHeader, String header, Group groupOfOrder) {
        super(isHeader, header);
        this.groupOfOrder = groupOfOrder;
    }

    public SectionOfOrder(Order order, Group groupOfOrder) {
        super(order);
        this.groupOfOrder = groupOfOrder;
    }

    public int getIndex(Order order){
        for (int i = 0; i < groupOfOrder.orders.size(); i++) {
            if(groupOfOrder.orders.get(i) == order){
                return i;
            }
        }
        return -1;
    }

}
