### 根据表字典Excel自动生成MySQL和Oracle数据库建表语句
#### 一、修改sql生成规则配置文件
+ 根据自己需求修改***setting.properties***文件下的配置
> + sqlFilePath-文件生成路径
> + is_commit_sql-是否执行sql入库
> + table_template_name-表字典Excel文件名称
> + create_table_many-是否一次生成多张表
> + no_set_length_column_type-不设置字段长度的类型

#### 二、修改数据库配置
+ 根据自己的数据库信息修改配置文件***mybatis-config.xml***

#### 三、表字典模板文件放置路径
+ 表字典Excel文件需放在***resource***文件夹下


#### 四、个性化修改
+ OracleSqlBuilderExecutor编写Oracle建表语句
+ AbstractSqlBuilderExecutor编写MySQL建表语句
