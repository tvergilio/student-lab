package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.student.model.Account;
import uk.ac.leedsbeckett.student.model.Invoice;

@Component
public class IntegrationService {

    private final RestTemplate restTemplate;

    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Account getStudentAccount(String studentId) {
        return restTemplate.getForObject("http://localhost:8081/accounts/student/" + studentId, Account.class);
    }

    public Invoice createCourseFeeInvoice(Invoice invoice) {
        return restTemplate.postForObject("http://localhost:8081/invoices/", invoice, Invoice.class);
    }
}
