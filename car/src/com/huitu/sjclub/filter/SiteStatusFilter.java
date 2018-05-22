/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.filter;

import com.huitu.sjclub.Setting;
import com.huitu.sjclub.util.SystemUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("siteStatusFilter")
public class SiteStatusFilter extends OncePerRequestFilter {

	private static final String[] DEFAULT_IGNORE_URL_PATTERNS = new String[] { "/admin/**" };

	private static final String DEFAULT_REDIRECT_URL = "/common/site_close.jhtml";

	private static AntPathMatcher antPathMatcher = new AntPathMatcher();

	private String[] ignoreUrlPatterns = DEFAULT_IGNORE_URL_PATTERNS;

	private String redirectUrl = DEFAULT_REDIRECT_URL;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Setting setting = SystemUtils.getSetting();
		if (setting.getIsSiteEnabled()) {
			filterChain.doFilter(request, response);
		} else {
			String path = request.getServletPath();
			if (path.equals(redirectUrl)) {
				filterChain.doFilter(request, response);
				return;
			}
			if (ignoreUrlPatterns != null) {
				for (String ignoreUrlPattern : ignoreUrlPatterns) {
					if (antPathMatcher.match(ignoreUrlPattern, path)) {
						filterChain.doFilter(request, response);
						return;
					}
				}
			}
			response.sendRedirect(request.getContextPath() + redirectUrl);
		}
	}

	public String[] getIgnoreUrlPatterns() {
		return ignoreUrlPatterns;
	}

	public void setIgnoreUrlPatterns(String[] ignoreUrlPatterns) {
		this.ignoreUrlPatterns = ignoreUrlPatterns;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}