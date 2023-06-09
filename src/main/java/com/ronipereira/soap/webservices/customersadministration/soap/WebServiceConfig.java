package com.ronipereira.soap.webservices.customersadministration.soap;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context){
            MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
            messageDispatcherServlet.setApplicationContext(context);
            messageDispatcherServlet.setTransformWsdlLocations(true);
            return new ServletRegistrationBean<MessageDispatcherServlet>(messageDispatcherServlet, "/ws/*");

        }

    @Bean
    XsdSchema customerSchema(){
            return new SimpleXsdSchema(new ClassPathResource("Customer-information.xsd"));
        }

    @Bean(name = "customers")
    DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema customerSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CustomerPort");
        definition.setTargetNamespace("http://ronipereira.com");
        definition.setLocationUri("/ws");
        definition.setSchema(customerSchema);
        return definition;
        }
}
