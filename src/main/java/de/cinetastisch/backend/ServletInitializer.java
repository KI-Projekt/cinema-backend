package de.cinetastisch.backend;

import de.cinetastisch.backend.config.Config;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

//public class ServletInitializer extends SpringBootServletInitializer {
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(SpringAzureCinemaApplication.class);
//	}
//
//}

public class ServletInitializer extends AbstractHttpSessionApplicationInitializer {

	public ServletInitializer() {
		super(Config.class);
	}

}
