package org.contacts.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(ContactBookResource.class);
		classes.add(CSVMessageWriter.class);
		classes.add(LoggingFilter.class);
		classes.add(GZIPWriterInterceptor.class);
		return classes;
	}
}
