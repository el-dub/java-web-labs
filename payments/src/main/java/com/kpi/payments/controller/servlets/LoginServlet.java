package com.kpi.payments.controller.servlets;

import com.kpi.payments.domain.User;
import com.kpi.payments.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/layouts/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> optionalUser = userService.getByEmailAndPassword(email, password);

        if (optionalUser.isPresent() && !optionalUser.get().getLocked()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", optionalUser.get());
            session.setMaxInactiveInterval(30 * 60);
            response.sendRedirect(request.getContextPath() + "/account");
        } else {
            var requestDispatcher = request.getRequestDispatcher("layouts/login.jsp");
            if (optionalUser.isPresent()) {
                request.setAttribute("errMsg", "This account is blocked!");
            } else {
                request.setAttribute("errMsg", "Wrong credentials!");
            }
            requestDispatcher.include(request, response);
        }
    }
}
