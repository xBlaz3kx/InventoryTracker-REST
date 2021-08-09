package com.bdular.inventorytracker.utils.documents.pdf.order;

import com.bdular.inventorytracker.data.order.data.ConsignmentOrder;
import com.bdular.inventorytracker.data.order.data.Order;
import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.user.customer.data.Customer;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import com.bdular.inventorytracker.utils.documents.pdf.PDFMaker;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.bdular.inventorytracker.utils.DateHelper.dateFormat;
import static com.bdular.inventorytracker.utils.ResourceUtil.INVOICE_TEMPLATE_PATH;
import static com.bdular.inventorytracker.utils.ResourceUtil.getOrderFilePath;
import static com.itextpdf.layout.property.HorizontalAlignment.CENTER;
import static com.itextpdf.layout.property.HorizontalAlignment.RIGHT;
import static com.itextpdf.layout.property.VerticalAlignment.MIDDLE;
import static java.lang.String.format;

public class OrderDocument<S extends Order> extends PDFMaker<S> {

    static ResourceBundle bundle = ResourceBundle.getBundle("pdf-translations");

    public OrderDocument(S order) throws IOException {
        super(order);
        setFilePath(getOrderFilePath(order));
        setTemplatePath(INVOICE_TEMPLATE_PATH);
        makePDF();
    }

    @Override
    public void makePDF() throws IOException {
        super.makePDF();
        addMetadata(format("%s %s", bundle.getString("Order"), getItem().getID()), getSeller(), format("%s, %s", bundle.getString("Order"), getItem().getID()));
        fillHeader(addOrderInfo());
        createTable(createHeaders(), new float[]{200, 90, 90, 90, 90}, createOrderDetailTable());
        Table table = getTable();
        createOrderSummary(table);
        // addQRCode(QRGenerator.createOrderQRCode(getItem()));
        table.setHorizontalAlignment(CENTER);
        table.setRelativePosition(0, 300, 0, 0);
        table.setVerticalAlignment(MIDDLE);
        addTableToDocument(table);
        addSignatures();
        close();
    }

    private Map<String, String> addOrderInfo() {
        Map<String, String> fieldsAndValues = new HashMap<>();
        S order = getItem();
        Customer customer = order.getCustomerReference();
        String customerName = getCustomer();
        Seller seller = order.getSellerReference();
        String sellerName = getSeller();
        //order info
        fieldsAndValues.put("OrderTitle", format("%s %d", bundle.getString("Order"), order.getReference()));
        fieldsAndValues.put("OrderNumberInternal", format("%s: %s", bundle.getString("InternalOrderNum"), order.getID()));
        fieldsAndValues.put("OrderNumberExternal", format("%s: %d", bundle.getString("ExternalOrderNum"), order.getReference()));
        StringBuilder payments = new StringBuilder();
        order.getPaymentInfo().getPaymentTypes().forEach(paymentType -> payments.append(bundle.getString(paymentType.name())).append(" ,"));
        fieldsAndValues.put("OrderPayment", format("%s: %s", bundle.getString("PaymentType"), payments.toString()));
        fieldsAndValues.put("OrderDate", format("%s: %s", bundle.getString("OrderDate"), dateFormat.format(order.getTimestamp())));
        //customer info
        fieldsAndValues.put("CustomerName", format("%s: %s", bundle.getString("Customer"), customerName));
        if (!customer.getCompanyName().isEmpty()) {
            fieldsAndValues.put("CustomerFirm", format("%s", customer.getCompanyName()));
        }
        fieldsAndValues.put("CustomerAddress", format("%s: %s", bundle.getString("Address"), customer.getAddress()));
        fieldsAndValues.put("CustomerPost", format("%s: %s", bundle.getString("Post"), customer.getPost()));
        fieldsAndValues.put("CustomerVATID", format("%s: %s", bundle.getString("VAT_ID"), customer.getVAT_ID()));
        //seller info
        fieldsAndValues.put("SellerName", format("%s: %s", bundle.getString("Seller"), sellerName));
        fieldsAndValues.put("SellerID", format("%s: %s", bundle.getString("SellerID"), seller.getID()));
        return fieldsAndValues;
    }

    private ArrayList<String> createHeaders() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add(bundle.getString("Product"));
        S order = getItem();
        if (order instanceof ConsignmentOrder) {
            headers.add(bundle.getString("Consigned"));
        }
        headers.add(bundle.getString("Quantity"));
        headers.add(bundle.getString("Discount"));
        headers.add(bundle.getString("Tax"));
        headers.add(bundle.getString("Price"));
        return headers;
    }

    private ArrayList<ArrayList<String>> createOrderDetailTable() throws MalformedURLException {
        ArrayList<ArrayList<String>> content = new ArrayList<>();
        S order = getItem();
        Map<Product, Integer> numProducts = order.getProductNumbers();
        Map<Product, Double> discounts = order.getProductDiscount();
        order.getProductList().forEach(product -> {
            ArrayList<String> row = new ArrayList<>();
            row.add(product.getName());
            if (order instanceof ConsignmentOrder) {
                row.add(format("%d", ((ConsignmentOrder) order).getItemsConsigned().get(product)));
            }
            row.add(format("%d", numProducts.get(product)));
            row.add(format("%.2f", discounts.get(product) * 100) + " %");
            row.add(format("%.2f", product.getVAT().doubleValue() * 100) + " %");
            row.add(format("%.2f €", product.getPrice().doubleValue()));
            content.add(row);
        });
        return content;
    }

    private void createOrderSummary(Table table) {
        S order = getItem();
        table.addFooterCell(" ");
        table.addFooterCell(bundle.getString("OrderDiscount"));
        table.addFooterCell(format("%.2f", order.getOrderDiscount().doubleValue() * 100) + " %");
        table.addFooterCell(bundle.getString("OrderTotal"));
        table.addFooterCell(format("%.2f €", order.getTotal()));
    }

    private void addSignatures() throws MalformedURLException {
        //Signature from seller
        S order = getItem();
        Paragraph signatureParagraph = new Paragraph(format("%s: ______________________ %40s", bundle.getString("Signature"), bundle.getString("CustomerSignature")));
        signatureParagraph.setRelativePosition(0, 600, 0, 0);
        signatureParagraph.setHorizontalAlignment(RIGHT);
        getMaker().getDoc().add(signatureParagraph);
        //Signature from customer if there exists any
        if (!order.getSignatureURL().isEmpty()) {
            Image image = new Image(ImageDataFactory.create(new URL(order.getSignatureURL())));
            signatureParagraph.setRelativePosition(0, 800, 0, 0);
            signatureParagraph.setHorizontalAlignment(RIGHT);
            getMaker().getDoc().add(image);
        }
    }

    private String getCustomer() {
        Customer customer = getItem().getCustomerReference();
        return format("%s %s", customer.getName(), customer.getSurname());
    }

    private String getSeller() {
        Seller seller = getItem().getSellerReference();
        return format("%s %s", seller.getName(), seller.getSurname());
    }
}
