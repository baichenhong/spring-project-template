package com.github.jbai;

import java.io.File;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

	private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

	@Override
	public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.register(AppConfig.class);
		mvcContext.register(WebConfig.class);
		mvcContext.setServletContext(container);
		mvcContext.refresh();

		ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(mvcContext));
		File tempdir = (File) container.getAttribute("javax.servlet.context.tempdir");
		registration.setMultipartConfig(new MultipartConfigElement(tempdir.getAbsolutePath(), maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2));
		registration.setLoadOnStartup(1);
		registration.setAsyncSupported(true);
		registration.addMapping("/");
	}

}
