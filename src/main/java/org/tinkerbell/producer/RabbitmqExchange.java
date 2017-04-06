package org.tinkerbell.producer;

/**
 * exchange交换机配置:
 * 在RabbitMQ中，ExchangeType有direct、Fanout和Topic三种，不同类型的Exchange路由的行为是不一样的
 * Created by nn_liu on 2017/4/6.
 */
public interface RabbitmqExchange {

    final String CONTRACT_FANOUT = "CONTRACT_FANOUT";
    final String CONTRACT_TOPIC = "CONTRACT_TOPIC";
    final String CONTRACT_DIRECT = "CONTRACT_DIRECT";

}
