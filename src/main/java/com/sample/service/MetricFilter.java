package com.sample.service;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This filter is used to get rest service request and update statistics. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */


@Component
public class MetricFilter implements Filter {

	@Autowired
	private IMetricService metricService;

	@Override
	public void init(final FilterConfig config) throws ServletException {
		if (metricService == null) {
			metricService = (IMetricService) WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext()).getBean("metricService");
		}
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws java.io.IOException, ServletException {
		final HttpServletRequest httpRequest = ((HttpServletRequest) request);
		final String req = httpRequest.getRequestURI();

		chain.doFilter(request, response);
		metricService.increaseCount(req);
	}

	@Override
	public void destroy() {
	}
}