package com.board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;



@Configuration
// 스프링은 Configuration이 지정된 클래스를 자바 기반의 설정 파일로 인식합니다.
@PropertySource("classpath:/application.properties")
// 클래스에서 참조할 properties의 파일 위치를 지정함
public class DBConfiguration {
	
	@Autowired
	// Bean으로 등록된 객체를 클래스에 주입하는데 사용함. 이외에도 Resource나 Inject 등이 존재한다고 함
	private ApplicationContext applicationContext;
	
	@Bean
	// Configuration 클래스의 메서드 레벨에만 지정이 가능. 지정된 객체는 컨테이너에 의해 관리되는 빈으로 등록.
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	// prefix에 해당하는 spring.datasource.hikari로 시작하는 설정을 모두 읽어들여 해당 메서드에 매핑(바인딩)함. 추가적으로 클래스레벨에도 지정 가능함.
	public HikariConfig hikariConfig() {
	// HikariConfig => 히카리CP 객체를 생성. 히카리CP는 커넥션풀(Connection Pool) 라이브러리 중 하나
		return new HikariConfig();
	}
	@Bean
	public DataSource dataSource() {
	// dataSource => 데이터 소스 객체를 생성. 순수 JDBC는 SQL를 실행할때마다 커넥션을 맺고 끊는 I/O작업을 하는데 리소스를 잡아먹기 때문에 이를 해결하기 위한 커넥션 풀.
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
	// SQLSessionFactory 객체를 생성. SQL실행에 대한 모든 것을 갖는 정말 중요한 역할. SqlSessionFactoryBean은 마이바티스와 스프링의 연동 모듈로 사용
	// 마이바티스 XML Mapper, 설정 파일 위치 등을 지정
	// SqlSessionFactoryBean 자체가 아닌, getObject 메서드가 리턴하는 SqlSessionFactory를 생성합니다.
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		
		factoryBean.setDataSource(dataSource());
//		factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml"));
		return factoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	// SqlSessionTemplate은 SqlSessionFactory를 통해 생성되고 데이터베이스의 커밋, 롤백 등 SQL 실행에 필요한 모든 메서드를 갖는 객체로 생각할 수 있다.
	}
}
