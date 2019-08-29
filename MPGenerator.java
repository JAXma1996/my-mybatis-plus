package cn.jiguang.antideception.admin.antideceptionadmin.common;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: anti-deception-admin
 * @Package: cn.jiguang.antideception.admin.antideceptionadmin.test
 * @ClassName:
 * @Author: mahaibin
 * @Description:  基于 MP的自动代码生成器 符合我自己的使用规范的
 * @Date: 2019/8/28 10:45
 * @Version: 1.0
 */
public class MPGenerator {

    public static void main(String[] args) {
        //以下列举了几个重要的定制化配置，如果不需要使用，则需将参数引用的地方注释

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

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath+ "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataBaseUrl);
        dsc.setDriverName(DataBaseDriver);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
       pc.setModuleName("generator");
        pc.setParent("cn.jiguang.antideception.admin.antideceptionadmin");
        mpg.setPackageInfo(pc);

        // 自定义配置注入新的配置 在模板文件中就可以使用

        // 如果模板引擎是 freemarker
        ///String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + ".xml";
            }
        });

        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
//                Map<String, Object> map = new HashMap<>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//                自定义属性注入abc=$!{cfg.abc}

            }
        };

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        if (entityTemplate!=null){
            templateConfig.setEntity(entityTemplate);
        }
        if (serviceTemplate!=null){
            templateConfig.setService(serviceTemplate);
        }
        if(controllerTemplate!=null ){
            templateConfig.setController(controllerTemplate);
        }
        if(serviceImplTemplate!=null){
            templateConfig.setServiceImpl(serviceImplTemplate);
        }
        if(mapperTemplate!=null){
            templateConfig.setMapper(mapperTemplate);
        }

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 生成文件的策略配置 主要包括文件名啊，表前缀如何处理 ，之类的
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(useLombok);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(restControllerStyle);
        strategy.setSuperEntityClass(superEntityClass);
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(tableName.split(","));
        strategy.setTablePrefix(tablePrefix);
        strategy.setControllerMappingHyphenStyle(controllerMappingHyphenStyle);
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
