秒杀活动设计方案：
前端层设计：
    1.将活动页面上的所有可以静态的元素全部静态化，并尽量减少动态元素。通过CDN来抗峰值
    2.请求拦截，用户点击之后，按钮置灰，禁止用户重复提交请求；js限制用户在5秒内只能提交一次请求
 
站点层设计：
    同一个uid，限制访问频度
    
服务层设计：
    1.用户请求分发，使用Nginx做负载均衡将请求分发到不同的机器上
    2.使用Redis集群，提前把商品库存数量加载到Redis中，通过Redis判断库存及扣减库存
    3.判断库存扣减库存的操作都写在Lua脚本中，Redis中库存为0后后面的请求直接返回false
    4.判断有库存的，成功生成订单，告诉用户成功，修改MySQL中的库存


Oracle优化：
    SELECT子句中避免使用 ‘ * ‘
    用Where子句替换HAVING子句
    用EXISTS替代IN、用NOT EXISTS替代NOT IN
    用索引提高效率
    用EXISTS替换DISTINCT
    用IN来替换OR
    避免在索引列上使用IS NULL和IS NOT NULL
    用UNION-ALL 替换UNION
 
DDL:
    2.2  修改表结构
     alert table table_name  MODIFY  col_name column_definition [FIRST | AFTER col_name]#修改字段类型
     alert table table_name  ADD col_name column_definition [FIRST | AFTER col_name]#增加字段
     alert table table_name  DROP col_name#删除字段
     alert table table_name  CHANGE old_col_name new_col_name column_definition [FIRST|AFTER col_name]#修改字段名
     
    如：将country字段修改长度为50个字节，并放在salary字段后。以下两种都可行。
    alter table table_name change country country varchar(50) default 'china' after salary; 
    alter table table_name modify country varchar(50) default 'china'  after salary; 
    修改字段时，注意原有默认值，修改命令时默认值仍需要添加
     
    2.3 查看表结构
    desc table_name;
     
    2.4 删除表
    drop table table_name;           