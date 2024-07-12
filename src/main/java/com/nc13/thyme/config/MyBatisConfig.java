package com.nc13.thyme.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 설정은 보통 하나의 파일에만 하기 때문에 resource 로 단수형으로 기입 된다.
        sqlSessionFactoryBean.setConfigLocation(
                new ClassPathResource("mybatis/mybatis-config.xml")
        );
        // mapper 에는 파일이 여러개 들어가기 때문에 복수 resources 로 사용하게 된다.
        // 전부다 끌어와야 되기 때문에 출력을 담아주는 값도 [array] 담게 되고 getResources 로 적게 된다.
        Resource[] resources =
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mappers/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
