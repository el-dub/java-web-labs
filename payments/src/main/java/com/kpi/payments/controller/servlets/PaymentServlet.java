package com.kpi.payments.controller.servlets;

import com.kpi.payments.domain.Payment;
import com.kpi.payments.domain.PaymentStatus;
import com.kpi.payments.domain.User;
import com.kpi.payments.domain.sorting.Direction;
import com.kpi.payments.domain.sorting.SortBy;
import com.kpi.payments.service.AccountService;
import com.kpi.payments.service.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "PaymentServlet", urlPatterns = {"/payment", "/payment/create", "/payment/send"})
public class PaymentServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(PaymentServlet.class);

    private final PaymentService paymentService = new PaymentService();
    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if ("/payment".equals(uri)) {
            var user = (User) request.getSession().getAttribute("user");
            List<Payment> payments = paymentService.getAllByUserId(user.getId());

            sort(request, payments);
            order(request, payments);

            logger.info("Payments: [{}]", payments);
            request.setAttribute("payments", payments);
            request.getRequestDispatcher("/layouts/payment.jsp").forward(request, response);
        }
        if ("/payment/send".equals(uri)) {
            String paymentId = request.getParameter("paymentId");
            var payment = paymentService.getById(Long.parseLong(paymentId)).get();
            payment.setStatus(PaymentStatus.SENT);
            paymentService.update(payment);
            response.sendRedirect("/payment");
        }
        if ("/payment/create".equals(uri)) {
            long senderId = Long.parseLong(request.getParameter("senderId"));
            request.setAttribute("senderId", senderId);
            var requestDispatcher = request.getRequestDispatcher("/layouts/createPayment.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if ("/payment/create".equals(uri)) {
            insertUser(request, response);
        }
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String senderId = request.getParameter("senderId");
        String recipientId = request.getParameter("recipientId");
        String amount = request.getParameter("amount");
        String operation = request.getParameter("operation");
        var payment = Payment.builder()
                .sender(accountService.getById(Long.parseLong(senderId)).get())
                .recipient(accountService.getById(Long.parseLong(recipientId)).get())
                .time(new Timestamp(System.currentTimeMillis()))
                .amount(new BigDecimal(amount))
                .status(PaymentStatus.valueOf(operation))
                .build();
        paymentService.save(payment);
        response.sendRedirect("/payment");
    }

    private void sort(HttpServletRequest request, List<Payment> payments) {
        String rawSortBy = request.getParameter("sortBy");
        var sortBy = SortBy.customValueOf(rawSortBy);

        Comparator<Payment> comparator;
        if (sortBy == SortBy.ID) {
            logger.info("SORTING BY ID");
            comparator = Comparator.comparingLong(Payment::getId);
        } else {
            logger.info("SORTING BY TIME");
            comparator = Comparator.comparing(Payment::getTime);
        }

        payments.sort(comparator);
    }

    private void order(HttpServletRequest request, List<Payment> payments) {
        String rawDirection = request.getParameter("direction");
        var direction = Direction.customValueOf(rawDirection);
        if (direction == Direction.DESC) {
            Collections.reverse(payments);
        }
    }
}
