package org.contacts.rest;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

@Provider
@Compressed
public class GZIPWriterInterceptor implements WriterInterceptor {

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException {
		context.getHeaders().add(HttpHeaders.CONTENT_ENCODING, "gzip");
		context.setOutputStream(new GZIPOutputStream(context.getOutputStream()));
		context.proceed();
	}
}
