package com.kpi.payments.controller.filters;

import com.kpi.payments.domain.Role;
import com.kpi.payments.domain.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String ERROR_ENDPOINT = "/error";
    private static final Set<String> userEndpoints = Set.of("/profile", "/payment", "/payment/create");
    private static final Set<String> adminEndpoints = Set.of();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        if (isSessionValid(session)) {
            if (isRequestMadeToAdminEndpoints(uri)) {
                var user = (User) session.getAttribute("user");
                var role = user.getRole();
                if (role == Role.ADMIN) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(403, "Access denied");
                }
            }
            if (isRequestMadeToUserEndpoints(uri)) {
                filterChain.doFilter(request, response);
            }
            //To enable servlet redirect to error page
            if (!isRequestMadeToAdminEndpoints(uri) && !isRequestMadeToUserEndpoints(uri)) {
                filterChain.doFilter(request, response);
            }
        } else {
            if (uri.equals(LOGIN_ENDPOINT)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(LOGIN_ENDPOINT);
            }
        }
    }

    private boolean isSessionValid(HttpSession session) {
        return session != null && session.getAttribute("user") != null;
    }

    private boolean isRequestMadeToUserEndpoints(String uri) {
        return userEndpoints.contains(uri);
    }

    private boolean isRequestMadeToAdminEndpoints(String uri) {
        return adminEndpoints.contains(uri);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {
    }
}
