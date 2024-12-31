package com.example;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.HttpHeaders;

public class HttpHeaderWrappedRequest extends HttpServletRequestWrapper {

  private final HttpHeaders headers = new HttpHeaders();

  public HttpHeaderWrappedRequest(HttpServletRequest req) {
    super(req);

    Enumeration<String> attrNames = req.getHeaderNames();
    while (attrNames.hasMoreElements()) {
      String name = attrNames.nextElement();
      Enumeration<String> values = req.getHeaders(name);
      if (values != null) {
        while (values.hasMoreElements()) {
          headers.add(name, values.nextElement());
        }
      }
    }
  }

  @Override
  public String getHeader(String name) {
    return headers.getFirst(name);
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    Set<String> names = headers.keySet();
    return Collections.enumeration((names == null) ? Collections.EMPTY_SET : names);
  }

  @Override
  public Enumeration<String> getHeaders(String name) {
    List<String> values = headers.get(name);
    return Collections.enumeration((values == null) ? Collections.EMPTY_SET : values);
  }

  public void setHeader(String name, String value) {
    headers.add(name, value);
  }
}
