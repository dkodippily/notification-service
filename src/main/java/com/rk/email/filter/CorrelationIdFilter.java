package com.rk.email.filter;


import com.rk.email.constant.ApplicationConstants;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Dayan Kodippily
 */

@Component
public class CorrelationIdFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(CorrelationIdFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String correlationId = ((HttpServletRequest) servletRequest).getHeader(ApplicationConstants.CORRELATION_ID);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = UUID.randomUUID().toString();

            LOGGER.info("X-Correlation-ID not specified, generating one: {}" , correlationId);
        }

        MDC.put(ApplicationConstants.CORRELATION_ID, correlationId);
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.remove(ApplicationConstants.CORRELATION_ID);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
