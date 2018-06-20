package com.exist.ecc.config;

import java.util.Locale;
import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.exist.ecc.service","com.exist.ecc.dao","com.exist.ecc.controllers", "com.exist.ecc.restcontrollers"})
public class WebAppConfiguration extends WebMvcConfigurerAdapter {
   
   @Bean
   public ViewResolver viewResolver() {
      InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
      internalResourceViewResolver.setViewClass(JstlView.class);
      internalResourceViewResolver.setPrefix("/WEB-INF/views/");
      internalResourceViewResolver.setSuffix(".jsp");
      return internalResourceViewResolver;
   }

   @Bean
   public CommonsMultipartResolver multipartResolver() {
      CommonsMultipartResolver resolver=new CommonsMultipartResolver();
      resolver.setDefaultEncoding("utf-8");
      return resolver;
   }

   @Bean
   public MessageSource messageSource() {
      ResourceBundleMessageSource source = new ResourceBundleMessageSource();
      source.setBasename("messages");
      return source;
   }

   @Bean
   public LocaleResolver localeResolver(){
      CookieLocaleResolver resolver = new CookieLocaleResolver();
      resolver.setDefaultLocale(new Locale("en"));
      return resolver;
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
      interceptor.setParamName("lang");
      registry.addInterceptor(interceptor);
   }

   @Bean
   public BasicDataSource myDataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName("org.postgresql.Driver");
      dataSource.setUrl("jdbc:postgresql://localhost/personneldb");
      dataSource.setUsername("postgres");
      dataSource.setPassword("ex1stgl0bal");
      return dataSource;
   }

   @Bean
   public LocalSessionFactoryBean sessionFactory() {
      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      sessionFactory.setDataSource(myDataSource());
      sessionFactory.setPackagesToScan( new String[] { "com.exist.ecc.model" });
      sessionFactory.setHibernateProperties(hibernateProperties());
      return sessionFactory;
   }

   @Bean
   @Autowired
   public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
      HibernateTransactionManager txManager = new HibernateTransactionManager();
      txManager.setSessionFactory(sessionFactory);
      return txManager;
   }

   private Properties hibernateProperties() {
      return new Properties() {
         {
            setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
            setProperty("hibernate.cache.use_second_level_cache", "true");
            setProperty("hibernate.cache.use_query_cache", "true");
         }
      };
   }

   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
   }

}