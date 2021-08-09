package com.bdular.inventorytracker.utils.documents.pdf.supplier;


import com.bdular.inventorytracker.data.product.pgk.Package;
import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.supplier.data.DeliveryReport;
import com.bdular.inventorytracker.data.supplier.data.Supplier;
import com.bdular.inventorytracker.data.supplier.data.SupplierOrder;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import com.bdular.inventorytracker.utils.documents.pdf.PDFMaker;
import com.bdular.inventorytracker.utils.documents.pdf.PDFUtil;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.bdular.inventorytracker.utils.DateHelper.dateFormat;
import static com.bdular.inventorytracker.utils.ResourceUtil.DELIVERY_REPORT_TEMPLATE_PATH;
import static com.bdular.inventorytracker.utils.ResourceUtil.getDeliveryReportFilePath;
import static com.itextpdf.layout.property.HorizontalAlignment.CENTER;
import static com.itextpdf.layout.property.HorizontalAlignment.RIGHT;
import static com.itextpdf.layout.property.VerticalAlignment.MIDDLE;
import static java.lang.String.format;


public class DeliveryDocument extends PDFMaker<DeliveryReport> {

    static ResourceBundle bundle = ResourceBundle.getBundle("pdf-translations");

    public DeliveryDocument(DeliveryReport report) throws IOException {
        super(report);
        setFilePath(getDeliveryReportFilePath(report));
        setTemplatePath(DELIVERY_REPORT_TEMPLATE_PATH);
        makePDF();
    }

    @Override
    public void makePDF() throws IOException {
        super.makePDF();
        addMetadata(bundle.getString("DeliveryReport"), "Reno d.o.o.", bundle.getString("DeliveryReport"));
        fillHeader(addDeliveryInfo());
        createTable(createHeaders(), new float[]{150, 80, 80, 80, 150}, addTable());
        Table table = getTable();
        table.setRelativePosition(0, 300, 0, 0);
        table.setHorizontalAlignment(CENTER);
        table.setVerticalAlignment(MIDDLE);
        addTableToDocument(table);
        addInfo();
        close();
    }

    private HashMap<String, String> addDeliveryInfo() {
        DeliveryReport report = getItem();
        HashMap<String, String> fieldsAndValues = new HashMap<>();
        SupplierOrder order = getItem().getSupplierOrder();
        Supplier supplier = order.getSupplier();
        fieldsAndValues.put("ReportTitle", bundle.getString("DeliveryReport"));
        fieldsAndValues.put("ReportID", format("%s: %s", bundle.getString("ReportID"), report.getID()));
        fieldsAndValues.put("ReportDate", format("%s: %s", bundle.getString("ReportDate"), dateFormat.format(report.getDate())));
        fieldsAndValues.put("SupplierOrderID", format("%s: %s", bundle.getString("SupplierOrderID"), order.getID()));
        fieldsAndValues.put("SupplierOrderDate", format("%s: %s", bundle.getString("OrderDate"), dateFormat.format(order.getOrderDate())));
        fieldsAndValues.put("SupplierID", format("%s: %s", bundle.getString("SupplierID"), supplier.getID()));
        fieldsAndValues.put("SupplierFirmName", format("%s: %s", bundle.getString("CompanyName"), supplier.getCompanyName()));
        fieldsAndValues.put("SupplierAddress", format("%s: %s", bundle.getString("Address"), supplier.getAddress()));
        fieldsAndValues.put("SupplierPost", format("%s: %s", bundle.getString("Post"), supplier.getPost()));
        fieldsAndValues.put("SupplierCountry", format("%s: %s", bundle.getString("Country"), supplier.getCountry()));
        fieldsAndValues.put("SupplierVAT", format("%s: %s", bundle.getString("VAT_ID"), supplier.getVAT_ID()));
        return fieldsAndValues;
    }

    private ArrayList<String> createHeaders() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add(bundle.getString("Product"));
        headers.add(bundle.getString("NumPackages"));
        headers.add(bundle.getString("NumProducts"));
        headers.add(bundle.getString("ProductsExpected"));
        headers.add(bundle.getString("Comments"));
        return headers;
    }

    private ArrayList<ArrayList<String>> addTable() {
        ArrayList<ArrayList<String>> content = new ArrayList<>();
        DeliveryReport report = getItem();
        Map<Package, Integer> numPackages = report.getPackagesTotal();
        Map<Package, Integer> numProducts = report.getProductsTotal();
        SupplierOrder order = report.getSupplierOrder();
        HashMap<Product, Integer> productsOrdered = order.getProductsOrdered();
        report.getPackages().forEach(aPackage -> {
            ArrayList<String> columns = new ArrayList<>();
            columns.add(aPackage.getProductName()); //name
            columns.add(numPackages.get(aPackage).toString()); //packages received
            columns.add(numProducts.get(aPackage).toString()); //number of products
            columns.add(productsOrdered.get(aPackage.getProductReference()).toString()); //products expected
            columns.add(" "); //actual products
            content.add(columns);
        });
        productsOrdered.keySet().forEach(key -> { //find if any packages are missing
            if (report.getPackages().stream().noneMatch(aPackage -> aPackage.getProductReference() == key)) {
                ArrayList<String> columns = new ArrayList<>();
                columns.add(key.getName()); //name
                columns.add("0"); //packages received
                columns.add("0"); //number of products
                columns.add(report.getSupplierOrder().getProductsOrdered().get(key).toString()); //products expected
                columns.add(" "); //comments
                content.add(columns);
            }
        });
        return content;
    }

    private void addInfo() {
        Paragraph paragraph = new Paragraph();
        Seller seller = getItem().getSeller();
        PDFUtil.addEmptyLine(paragraph, 3);
        paragraph.add(format("%s: %s", bundle.getString("Signature"), seller.getName() + " " + seller.getSurname())).setBold();
        paragraph.setHorizontalAlignment(RIGHT);
        paragraph.setRelativePosition(400, 700, 50, 20);
        getMaker().getDoc().add(paragraph);
    }
}
