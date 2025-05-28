package com.estuamante.shogun.services;

import com.estuamante.shogun.auth.entities.User;
import com.estuamante.shogun.entities.Order;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentIntentService {

    public String createStripeCustomer(String username) throws Exception {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", username);

        Customer customer = Customer.create(customerParams);

        return customer.getId();
    }

    public Map<String, String> createPaymentIntent(Order order) throws StripeException {
        User user = order.getUser();
        Map<String, String> metaData = new HashMap<>();
        metaData.put("order id", order.getId().toString());
        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                .setCustomer(user.getStripeCustomerId())
                .putAllMetadata(metaData)
                .setAmount(convertToStripeAmount(order.getTotalAmount()))
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
        Map<String, String> map = new HashMap<>();
        map.put("client_secret", paymentIntent.getClientSecret());
        return map;
    }

    private long convertToStripeAmount(double price) {
        return Math.round(price * 100);
    }
}
