package com.kpi.payments.controller.servlets;

import com.kpi.payments.domain.Account;
import com.kpi.payments.domain.Payment;
import com.kpi.payments.domain.PaymentStatus;
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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "AccountServlet", urlPatterns = {"/account", "/account/replenish", "/account/lock", "/account/unlock"})
public class AccountServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AccountServlet.class);

    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if ("/account".equals(uri)) {
            var user = (User) request.getSession().getAttribute("user");
            List<Account> accounts = accountService.getAllByUserId(user.getId());

            sort(request, accounts);
            order(request, accounts);

            logger.info("Accounts: [{}]", accounts);
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("/layouts/account.jsp").forward(request, response);
        }
        if ("/account/replenish".equals(uri)) {
            String accountId = request.getParameter("accountId");
            request.setAttribute("accountId", accountId);
            request.getRequestDispatcher("/layouts/replenishAccount.jsp").forward(request, response);
        }
        if ("/account/lock".equals(uri)) {
            String accountId = request.getParameter("accountId");
            var account = accountService.getById(Long.parseLong(accountId)).get();
            account.setLocked(true);
            accountService.update(account);
            response.sendRedirect("/account");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if ("/account/replenish".equals(uri)) {
            replenishAccount(request, response);
        }
    }

    private void replenishAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountId = request.getParameter("accountId");
        String amount = request.getParameter("amount");
        var account = accountService.getById(Long.parseLong(accountId)).get();
        var oldMoneyAmount = account.getMoneyAmount();
        var newMoneyAmount = oldMoneyAmount.add(new BigDecimal(amount));
        account.setMoneyAmount(newMoneyAmount);
        accountService.update(account);
        response.sendRedirect("/account");
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
