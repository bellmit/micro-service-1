package com.ms.log.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

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

import com.system.springboot.SpringBootVFS;

@Configuration // 该注解类似于spring配置文件
@MapperScan(basePackages = {"com.module.api.*.dao" })
public class MyBatisConfig {

    @Autowired
    private Environment env;

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {
		//解决myBatis下 不能嵌套jar文件的问题
    	VFS.addImplClass(SpringBootVFS.class);
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        Resource configLocation = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
		fb.setConfigLocation(configLocation);
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        String typeAliasesPackage = "com.module.api.*.pojo;";
        
        fb.setTypeAliasesPackage(typeAliasesPackage);//env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
        List<Resource> resources = new ArrayList<Resource>();
        resources.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
        .getResources("classpath*:com/module/api/*/dao/*.xml")));
        fb.setMapperLocations(resources.toArray(new Resource[resources.size()]));//env.getProperty("mybatis.mapperLocations")));//

        return fb.getObject();
    }
}
