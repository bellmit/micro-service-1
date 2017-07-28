package com.ms.monitor.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.system.ds.DynamicDataSource;
import com.system.springboot.SpringBootVFS;

/**
 * springboot集成mybatis的基本入口 1）创建数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要)
 * 2）创建SqlSessionFactory 3）配置事务管理器，除非需要使用事务，否则不用配置
 */
@Configuration // 该注解类似于spring配置文件
@MapperScan(basePackages = {"com.module.admin.sys.dao", "com.module.admin.cli.dao", 
		"com.module.admin.prj.dao", "com.module.admin.rest.dao", "com.module.admin.ms.dao",
		"com.module.admin.code.dao" })
public class MyBatisConfig {

    @Autowired
    private Environment env;

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
		//解决myBatis下 不能嵌套jar文件的问题
    	VFS.addImplClass(SpringBootVFS.class);
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        Resource configLocation = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
		fb.setConfigLocation(configLocation);
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        String typeAliasesPackage = "com.module.admin.sys.pojo;"
        		+ "com.module.admin.cli.pojo;"
        		+ "com.module.admin.prj.pojo;"
        		+ "com.module.admin.rest.pojo;"
        		+ "com.module.admin.code.pojo;"
        		+ "com.module.admin.ms.pojo;";
        fb.setTypeAliasesPackage(typeAliasesPackage);//env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
        List<Resource> resources = new ArrayList<Resource>();
        resources.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
        .getResources("classpath*:com/module/admin/sys/dao/*.xml")));
        resources.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
        .getResources("classpath*:com/module/admin/cli/dao/*.xml")));
        resources.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
        .getResources("classpath*:com/module/admin/prj/dao/*.xml")));
        resources.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
        .getResources("classpath*:com/module/admin/rest/dao/*.xml")));
        resources.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
        .getResources("classpath*:com/module/admin/ms/dao/*.xml")));
        resources.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
        .getResources("classpath*:com/module/admin/code/dao/*.xml")));
        fb.setMapperLocations(resources.toArray(new Resource[resources.size()]));//env.getProperty("mybatis.mapperLocations")));//

        return fb.getObject();
    }
}
