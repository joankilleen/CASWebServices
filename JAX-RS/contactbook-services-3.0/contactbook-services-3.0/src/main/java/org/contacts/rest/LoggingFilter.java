package org.contacts.rest;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String contentType = requestContext.getHeaderString("Content-Type");
		String acceptHeader = requestContext.getHeaderString("Accept");
		System.out.println("Request received: " + requestContext.getMethod() + " "
				+ requestContext.getUriInfo().getRequestUri()
				+ (contentType != null ? " [Content-Type: " + contentType + "]" : "")
				+ (acceptHeader != null ? " [Accept: " + acceptHeader + "]" : ""));
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		String contentType = responseContext.getHeaderString("Content-Type");
		System.out.println("Response sent: " + responseContext.getStatus() + " " + responseContext.getStatusInfo()
				+ (contentType != null ? " [Content-Type: " + contentType + "]" : ""));
	}
}
