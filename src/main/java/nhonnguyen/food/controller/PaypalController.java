package nhonnguyen.food.controller;

import org.springframework.ui.Model;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import nhonnguyen.food.entity.OrderPay;
import nhonnguyen.food.services.CartService;
import nhonnguyen.food.services.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaypalController {

    @Autowired
    PaypalService service;
    @Autowired
    private CartService cartService;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/pays")
    public String getPaymentForm(Model model, HttpSession session) {
        // Lấy tổng giá trị từ giỏ hàng và đặt giá trị vào model
        double totalAmount = cartService.getSumPrice(session);
        model.addAttribute("totalAmount", totalAmount);

        // Rest of the method
        return "pay/paypal";
    }
    @PostMapping("/pays")
    public String payment(@ModelAttribute("orderpay") OrderPay orderpay) {
        try {
            Payment payment = service.createPayment(orderpay.getPrice(), orderpay.getCurrency(), orderpay.getMethod(),
                    orderpay.getIntent(), orderpay.getDescription(), "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "pay/cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "pay/success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
