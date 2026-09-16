# Design Patterns Demo

这是一个基于 Spring Boot 的设计模式示例项目，主要演示以下模式在 Java 业务代码中的基本用法：

- 策略模式（Strategy Pattern）
- 责任链模式（Chain of Responsibility Pattern）
- 工厂模式与策略注册（Factory / Registry）
- 模板方法与状态分发（Template Method / Command Map）
- 状态机模式（State Machine）

项目重点不是提供完整的订单系统，而是通过订单、策略和处理链等示例，展示如何将不同业务规则拆分成可扩展的组件。

## 技术栈

- Java 8
- Spring Boot 2.5.3
- Spring Statemachine 2.2.1.RELEASE
- Lombok
- Maven

依赖配置见 [pom.xml](/Users/w/IdeaProjects/design/design/pom.xml)。

## 项目结构

```text
src/main/java
├── com/Application.java                       Spring Boot 启动类
└── com/design
    ├── strategy                              策略模式
    │   ├── StrategyAbstract.java             策略抽象类
    │   ├── Strategy1.java                    策略实现一
    │   ├── Strategy2.java                    策略实现二
    │   ├── StrategyFactory.java              策略注册与分发
    │   └── TestEnum.java                     策略类型枚举
    ├── responsibility                        责任链模式
    │   ├── Abstract.java                     处理节点抽象类
    │   ├── ChainHandler.java                 责任链组装与入口
    │   ├── Param.java                        链路参数
    │   └── impl/Test1、Test2.java             责任链节点
    ├── handler                              订单处理器分发
    │   ├── AbstractOrderHandler.java         订单处理器抽象类
    │   ├── BaseOrderHandler.java             默认处理逻辑
    │   ├── OrderHandlerFactory.java          按订单类型分发
    │   ├── orderHandler/                     普通订单和预售订单
    │   └── impl/                             具体订单实现
    └── order                                订单状态机
        ├── OrderState.java                   订单状态
        ├── OrderEvent.java                   状态事件
        ├── OrderStateMachineConfig.java     状态和转换配置
        └── OrderStateMachineService.java    状态机调用服务
```

## 一、策略模式

策略模式用于封装一组可以互相替换的业务算法。调用方只需要传入策略类型，不需要了解具体实现类。

### 核心类

- `StrategyAbstract`：定义策略统一接口，包括获取类型和执行策略。
- `Strategy1`、`Strategy2`：不同的策略实现。
- `StrategyFactory`：Spring 启动时收集所有策略，并按类型建立映射。
- `TestEnum`：维护策略类型编码。

### 调用流程

```text
调用方传入策略类型
        ↓
StrategyFactory 根据类型查找策略
        ↓
执行对应 StrategyAbstract.test()
```

例如：

```java
strategyFactory.run(TestEnum.S_1.getCode());
```

新增策略时，通常只需要新增一个 Spring Bean，并返回新的类型编码，不需要修改调用方的分支判断。

### 适用场景

- 不同支付方式的处理
- 不同订单类型的计算规则
- 不同渠道、会员等级或业务类型的价格策略
- 多种可替换的校验、导出或通知方式

## 二、责任链模式

责任链模式将多个处理步骤串联起来，请求依次经过各个节点。每个节点可以处理自己的逻辑，也可以继续交给下一个节点。

### 核心类

- `Abstract`：责任链节点基类，保存下一个处理器。
- `Test1`、`Test2`：具体处理节点。
- `ChainHandler`：通过 Spring 注入节点，并根据 `@Order` 组装链路。
- `Param`：节点之间传递的参数对象。

### 调用流程

```text
ChainHandler → Test1 → Test2 → 返回最终 Param
```

调用示例：

```java
chainHandler.executionChain(Param.builder().i(1).build());
```

当前示例中的节点都会继续向下执行。实际业务中，某个节点也可以在校验失败时直接中断链路并返回错误结果。

### 适用场景

- 订单创建前的多项校验
- 风控、权限和资格检查
- 物流、库存、优惠券等前置处理
- 请求过滤器和审批流程

## 三、订单处理器：工厂 + 模板方法 + 状态分发

`handler` 包是一个组合示例，不是单独的一种模式。

### 处理层次

```text
OrderHandlerFactory
        ↓ 按订单类型选择
NormalOrderHandler / PreOrderHandler
        ↓ 复用默认逻辑
BaseOrderHandler
        ↓ 按订单状态选择方法
CREATE / CANAL / CUSTOM
```

### 核心类

- `OrderHandlerFactory`：根据订单类型找到对应处理器。
- `AbstractOrderHandler`：定义订单处理器公共结构，并将状态码映射到具体方法。
- `BaseOrderHandler`：提供默认的创建、关闭和自定义处理逻辑。
- `NormalOrderImpl`：普通订单的个性化实现。
- `PreOrderImpl`：预售订单的个性化实现。
- `TyeEnum`：订单类型枚举。
- `OrderStatusEnum`：订单处理状态枚举。

子类只重写有差异的业务方法，公共逻辑可以放在基类中复用，适合“先按大类型分组，再按订单状态执行不同动作”的场景。

## 四、订单状态机

订单状态机用于描述订单状态以及状态之间允许发生的事件，避免在业务代码中堆积大量 `if/else`。

### 状态流转

```text
待支付 --支付--> 已支付
待支付 --取消--> 已取消
已支付 --开始处理--> 处理中
处理中 --完成--> 已完成

已支付、处理中、已完成 --申请退款--> 退款中 --退款成功--> 已退款
已支付、处理中、已完成 --申请退货退款--> 退货退款中 --退货退款成功--> 已退货退款
```

### 核心类

- `OrderState`：定义订单状态。
- `OrderEvent`：定义触发状态变化的事件。
- `OrderStateMachineConfig`：使用 Spring Statemachine 配置初始状态和合法转换。
- `OrderStateMachineService`：根据订单 ID 获取状态机并发送事件。

### 测试示例

```java
Long orderId = 10001L;
orderStateMachineService.getState(orderId);
orderStateMachineService.sendEventAndGetState(orderId, OrderEvent.PAY);
orderStateMachineService.sendEventAndGetState(
        orderId, OrderEvent.START_PROCESSING);
```

如果当前状态不允许执行指定事件，状态机不会完成转换，服务会抛出状态不允许的异常。

### Demo 与真实业务的边界

当前状态机代码是 Demo，状态机实例按订单 ID 缓存在进程内，主要用于演示和测试状态流转。

真实订单处理时，推荐采用以下流程：

```text
查询数据库中的订单状态
        ↓
使用数据库状态初始化状态机
        ↓
由状态机校验事件是否合法
        ↓
在事务中更新订单状态
        ↓
记录状态变更和业务操作日志
```

生产环境还需要考虑事务、幂等校验、并发更新、支付回调鉴权、超时关闭以及多实例部署下的状态一致性。

## 五、测试入口

`com.design.strategy.Test` 实现了 `CommandLineRunner`。应用启动后会自动执行策略、责任链、订单处理器和订单状态机示例，并将结果输出到控制台。

因此该类是演示入口，不代表正式业务入口。正式项目中建议将这些验证逻辑迁移到单元测试或集成测试中。

## 启动项目

确保本机已安装 Java 8 和 Maven，然后执行：

```bash
mvn spring-boot:run
```

默认端口为 `8082`，配置见 `src/main/resources/application.yml`。

## 扩展建议

如果将该 Demo 扩展为真实订单模块，可以继续增加：

1. 订单状态持久化和状态恢复。
2. 状态变更记录表和操作日志。
3. 数据库乐观锁或分布式锁，避免并发状态覆盖。
4. 事件幂等表，避免支付回调、退款回调重复处理。
5. 统一的业务异常和错误码。
6. 针对每条合法和非法状态流转的单元测试。
7. 通过配置开关控制 Demo 启动测试，避免测试代码影响正式启动。
