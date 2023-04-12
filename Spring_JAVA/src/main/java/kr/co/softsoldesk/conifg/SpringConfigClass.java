package kr.co.softsoldesk.conifg;


import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

//Spring 환경설정 방법 1
public class SpringConfigClass implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//Spring Framework 프로젝트 설정을 위한 클래스의 객체생성
		AnnotationConfigWebApplicationContext servletAppContext=new AnnotationConfigWebApplicationContext();
		servletAppContext.register(ServletAppContext.class);
		
		//요청 발생 시 요청을 처리하는 Servlet을 DispatcherServlet으로 설정
		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		//매개변수로 선언된 servletContext객체를 이용하여 servlet추가 
		ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher", dispatcherServlet);
		
		//부가설정 
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
		//=======================================================================================================
		//일반 Bean등록
		AnnotationConfigWebApplicationContext rootAppContext=new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		//Listner 구현
		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		
		//Filter 구현
		FilterRegistration.Dynamic filter=servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		
		//dispatcher에 의해서 편집한 경로 UTF-8로 세팅
		filter.addMappingForServletNames(null, false, "dispatcher");
		
		
		
	}

}

/*
//Spring 환경설정 방법 2
public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer {
	   // DispatcherServlet에 매핑할 요청 주소를 셋팅한다.
	   @Override
	   protected String[] getServletMappings() {
	      // TODO Auto-generated method stub
	      return new String[] { "/" };
	   }

	   // Spring MVC 프로젝트 설정을 위한 클래스를 지정한다.
	   @Override
	   protected Class<?>[] getServletConfigClasses() {
	      // TODO Auto-generated method stub
	      return new Class[] { ServletAppContext.class };
	   }

	   // 프로젝트에서 사용할 Bean들을 정의기 위한 클래스를 지정한다.
	   @Override
	   protected Class<?>[] getRootConfigClasses() {
	      // TODO Auto-generated method stub
	      return new Class[] { RootAppContext.class };
	   }

	   // 파라미터 인코딩 필터 설정
	   @Override
	   protected Filter[] getServletFilters() {
	      // TODO Auto-generated method stub
	      CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
	      encodingFilter.setEncoding("UTF-8");
	      return new Filter[] {encodingFilter};
	   }
	}
 */