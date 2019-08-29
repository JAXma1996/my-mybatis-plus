### [MybatisPlus](https://github.com/baomidou/mybatis-plus)的代码自动生成器

通常我们写后端业务代码的时候免不了总是要生成一系列的：controller ,service, mapper ,entity等。

通过使用mybatis-plus 的generator的使用，可以自动根据数据表的字段，生成对应的entity等，面去繁琐的重复劳动力。

#### 如何使用？

1.**引入以下的两个denpency**

```
        
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-generator</artifactId>
        <version>3.2.0</version>
    </dependency>
    <!-- 以下这个是默认的template 引擎 -->
    <dependency>
        <groupId>org.apache.velocity</groupId>
        <artifactId>velocity-engine-core</artifactId>
        <version>2.1</version>
    </dependency>    
```
2.**将[MPGenerator](https://github.com/JAXma1996/my-mybatis-plus/blob/master/MPGenerator.java) 复制到你的项目目录下**

3.**修改以下配置：**

```
  //数据库配置
        String dataBaseUrl = "jdbc:mysql://localhost:3306/db_anti_deception?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        String DataBaseDriver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "123456";
        //作者名，用于生成文件注解时使用。
        String author = "mahaibin";

        //表名,多表使用逗号分开
        final String tableName = "t_task";
        //controller的url请求是否使用 "-" 形式如: true :/case-info false:/caseInfo
        boolean controllerMappingHyphenStyle = false;
        // 是否使用lombok
        boolean useLombok = true;
        //表是否有前缀（t_case），如果不配置, 那生成的Entity会是：TCase
        String tablePrefix = "t_";
        //controller文件是不是带RestController
        boolean restControllerStyle = true;
        //entity是否需要继承父类，如果需要则将已有的父类路径写下面，同理可配置Controller等父类。
        String superEntityClass = "cn.jiguang.antideception.admin.antideceptionadmin.common.entity.BaseEntity";
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别 ,使用系统默认传null
        String entityTemplate = "vmtempl/entity.java";
        String serviceTemplate = "vmtempl/service.java";
        String controllerTemplate = "vmtempl/controller.java";
        String serviceImplTemplate = "vmtempl/serviceImpl.java";
        String mapperTemplate = "vmtempl/mapper.java";
```
4. **修改template文件**
  **一般情况下默认的模板时可以满足生成的需求的**，但是有时候我们需要修改模板，做一些我们自己的定制化，只需要复制[源template](https://github.com/baomidou/mybatis-plus/tree/3.0/mybatis-plus-generator/src/main/resources/templates) 的文件到自己的resource 目录下 ，并且配置第三步骤中的 xxxtemplate就可以。 
  对于在template 文件中出现的参数（如：${superEntityClass}）的注入请看： [AbstractTemplateEngin](https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-generator/src/main/java/com/baomidou/mybatisplus/generator/engine/AbstractTemplateEngine.java) 中的：getObjectMap（）中对 objectMap 的设置。 也可直接看本文末附件
  以下展示修改类注解：
* before:
```

/**
 * <p>
 * $!{table.comment}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
#if(${entityLombokModel})
@Data
#if(${superEntityClass})
@EqualsAndHashCode(callSuper = true)
#else
@EqualsAndHashCode(callSuper = false)
#end
```

* after：
```
/**
 * @ProjectName: anti-deception-admin
 * @Package: ${package.Entity}
 * @ClassName: ${entity}
 * @Author: ${author}
 * @Description:
 * @Date: ${date}
 */

#if(${entityLombokModel})
@Data
```
##### 附件 参数注入设置
```
 objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
        objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
        objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
        objectMap.put("activeRecord", globalConfig.isActiveRecord());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger2", globalConfig.isSwagger2());
        objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        objectMap.put("table", tableInfo);
        objectMap.put("enableCache", globalConfig.isEnableCache());
        objectMap.put("baseResultMap", globalConfig.isBaseResultMap());
        objectMap.put("baseColumnList", globalConfig.isBaseColumnList());
        objectMap.put("entity", tableInfo.getEntityName());
        objectMap.put("entitySerialVersionUID", config.getStrategyConfig().isEntitySerialVersionUID());
        objectMap.put("entityColumnConstant", config.getStrategyConfig().isEntityColumnConstant());
        objectMap.put("entityBuilderModel", config.getStrategyConfig().isEntityBuilderModel());
        objectMap.put("entityLombokModel", config.getStrategyConfig().isEntityLombokModel());
        objectMap.put("entityBooleanColumnRemoveIsPrefix", config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
        objectMap.put("superEntityClass", getSuperClassName(config.getSuperEntityClass()));
        objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
        objectMap.put("superMapperClass", getSuperClassName(config.getSuperMapperClass()));
        objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
        objectMap.put("superServiceClass", getSuperClassName(config.getSuperServiceClass()));
        objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
        objectMap.put("superServiceImplClass", getSuperClassName(config.getSuperServiceImplClass()));
        objectMap.put("superControllerClassPackage", config.getSuperControllerClass());
        objectMap.put("superControllerClass", getSuperClassName(config.getSuperControllerClass()));
        return Objects.isNull(config.getInjectionConfig()) ? objectMap : config.getInjectionConfig().prepareObjectMap(objectMap);
```