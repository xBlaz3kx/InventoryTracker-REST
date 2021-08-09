package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.order.OrderRepository;
import com.bdular.inventorytracker.data.order.data.Order;
import com.bdular.inventorytracker.data.user.customer.CustomerRepository;
import com.bdular.inventorytracker.data.user.customer.data.Customer;
import com.bdular.inventorytracker.utils.documents.pdf.order.OrderDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static com.bdular.inventorytracker.utils.DateHelper.dateFormat;
import static com.bdular.inventorytracker.utils.ResourceUtil.getOrderFilePath;
import static java.lang.String.format;

@Service
@Validated
public class CustomerService {

    static ResourceBundle bundle = ResourceBundle.getBundle("pdf-translations");

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EmailService emailService;
    //@Autowired
    //CustomerImportBatch importBatch;

    public Customer getCustomer(String id) {
        return customerRepository.findById(id).orElseThrow();
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(@Valid Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    public void sendInvoiceToCustomer(@NotNull String id, @NotNull String orderID) {
        customerRepository.findById(id).map(customer -> {
            if (!customer.getEmail().isEmpty()) {
                orderRepository.findById(orderID).map(order -> {
                    String fileName = getOrderFilePath(order);
                    FileSystemResource resource = new FileSystemResource(fileName);
                    if (!resource.exists()) {
                        try {
                            //email the invoice
                            OrderDocument<Order> orderOrderPDF = new OrderDocument<>(order);
                            resource = orderOrderPDF.getPDF();
                            emailService.sendMessageWithAttachment(customer.getEmail(),
                                    format("%s %d", bundle.getString("Order"), order.getReference()),
                                    "",
                                    resource.getPath());
                        } catch (MessagingException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return order;
                }).orElseThrow();
            }
            return customer;
        }).orElseThrow();
    }

    public void deleteCustomer(@NotNull String id) {
        customerRepository.deleteById(id);
    }

    public void importCustomersFromExcel(MultipartFile file) throws Exception {
        String fileName = format("%s %s %s", "Customers\\", file.getOriginalFilename(), dateFormat.format(new Date()));
        File file1 = new File(fileName);
        file1.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file1);
        outputStream.write(file.getBytes());
        outputStream.close();
        FileSystemResource excelFile = new FileSystemResource(file1);
        //importBatch.launchJob(excelFile.getPath());
    }
}
