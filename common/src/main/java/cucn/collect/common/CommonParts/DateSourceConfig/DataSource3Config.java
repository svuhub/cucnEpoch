package cucn.collect.common.CommonParts.DateSourceConfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//DataSource3
@Configuration // 注册到springboot容器中
@MapperScan(basePackages = "cucn.collect.common.domain.MyBatis.mapper03", sqlSessionFactoryRef = "test3SqlSessionFactory")
public class DataSource3Config {

	@Bean(name = "test3DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.test3")
	public DataSource testDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "test3SqlSessionFactory")
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("test3DataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		// bean.setMapperLocations(
		// new
		// PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test3/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "test3TransactionManager")
	public DataSourceTransactionManager testTransactionManager(@Qualifier("test3DataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "test3SqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("test3SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
