package com.kpi.payments.controller.servlets;

import com.kpi.payments.domain.Account;
import com.kpi.payments.domain.User;
import com.kpi.payments.domain.sorting.Direction;
import com.kpi.payments.domain.sorting.SortBy;
import com.kpi.payments.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/profile"})
public class UserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserServlet.class);

    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (User) request.getSession().getAttribute("user");
        List<Account> accounts = accountService.getAllByUserId(user.getId());

        sort(request, accounts);
        order(request, accounts);

        logger.info("Accounts: [{}]", accounts);
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/layouts/account.jsp").forward(request, response);
    }

    private void sort(HttpServletRequest request, List<Account> accounts) {
        String rawSortBy = request.getParameter("sortBy");
        var sortBy = SortBy.customValueOf(rawSortBy);

        Comparator<Account> comparator;
        if (sortBy == SortBy.ID) {
            logger.info("SORTING BY ID");
            comparator = Comparator.comparingLong(Account::getId);
        } else if (sortBy == SortBy.NAME) {
            logger.info("SORTING BY NAME");
            comparator = Comparator.comparing(Account::getName);
        } else {
            logger.info("SORTING BY MONEY");
            comparator = Comparator.comparing(Account::getMoneyAmount);
        }

        accounts.sort(comparator);
    }

    private void order(HttpServletRequest request, List<Account> accounts) {
        String rawDirection = request.getParameter("direction");
        var direction = Direction.customValueOf(rawDirection);
        if (direction == Direction.DESC) {
            Collections.reverse(accounts);
        }
    }
}
