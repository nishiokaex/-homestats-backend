package com.example;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReverseProxyFilter implements Filter {

  private final Environment environment;
  private final ForwardedHeaderFilter filter = new ForwardedHeaderFilter();

  public ReverseProxyFilter(Environment environment) {
    this.environment = environment;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    System.out.println("X-Forwarded-Host: " + httpRequest.getHeader("X-Forwarded-Host") + ", " + httpRequest.getRequestURI());

    String host = environment.getProperty("app.http.X_Forwarded_Host", String.class);
    String port = environment.getProperty("app.http.X_Forwarded_Port", String.class);
    String proto = environment.getProperty("app.http.X_Forwarded_Proto", String.class);

    if (host == null && port == null && proto == null) {
      filter.doFilter(request, response, chain);
    } else {
      HttpHeaderWrappedRequest wrappedRequest = new HttpHeaderWrappedRequest(httpRequest);

      System.out.println("X-Forwarded-Host(Wrapped): " + wrappedRequest.getHeader("X-Forwarded-Host") + ", " + httpRequest.getRequestURI());

      // "X-Forwarded-Host", "X-Forwarded-Port", and "X-Forwarded-Proto" ヘッダの上書き
      if (host != null) {
        wrappedRequest.setHeader("X-Forwarded-Host", host);
      }
      if (port != null) {
        wrappedRequest.setHeader("X-Forwarded-Port", port);
      }
      if (proto != null) {
        wrappedRequest.setHeader("X-Forwarded-Proto", proto);
      }

      filter.doFilter(wrappedRequest, response, chain);
    }
  }

  @Override
  public void destroy() {
  }
}
