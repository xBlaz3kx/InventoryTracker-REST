package com.bdular.inventorytracker.utils.documents.pdf;

import com.bdular.inventorytracker.data.product.data.Product;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import com.itextpdf.layout.element.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.bdular.inventorytracker.utils.DateHelper.dateFormat;
import static com.bdular.inventorytracker.utils.ResourceUtil.INVENTORY_TEMPLATE_PATH;
import static com.bdular.inventorytracker.utils.ResourceUtil.getInventoryReportFilePath;
import static com.itextpdf.layout.property.HorizontalAlignment.CENTER;
import static com.itextpdf.layout.property.VerticalAlignment.MIDDLE;
import static java.lang.String.format;

public class InventoryReport extends PDFMaker<com.bdular.inventorytracker.data.inventory.data.InventoryReport> {

    static ResourceBundle bundle = ResourceBundle.getBundle("pdf-translations");

    public InventoryReport(com.bdular.inventorytracker.data.inventory.data.InventoryReport item) throws IOException {
        super(item);
        setFilePath(getInventoryReportFilePath(item));
        setTemplatePath(INVENTORY_TEMPLATE_PATH);
        makePDF();
    }

    @Override
    public void makePDF() throws IOException {
        super.makePDF();
        addMetadata(bundle.getString("InventoryReport"), "Reno d.o.o.", bundle.getString("InventoryReport"));
        fillHeader(makeHeader());
        createTable(makeTableHeader(), new float[]{100, 50, 100, 50, 100}, makeTableContent());
        Table table = getTable();
        table.setRelativePosition(0, 300, 0, 0);
        table.setHorizontalAlignment(CENTER);
        table.setVerticalAlignment(MIDDLE);
        addTableToDocument(table);
        close();
    }

    public Map<String, String> makeHeader() {
        Map<String, String> fieldsAndValues = new HashMap<>();
        com.bdular.inventorytracker.data.inventory.data.InventoryReport report = getItem();
        Seller seller = report.getSeller();
        fieldsAndValues.put("InventoryTitle", format("%s %s", bundle.getString("InventoryReport"), report.getId()));
        fieldsAndValues.put("InventoryDate", format("%s: %s", bundle.getString("Date"), dateFormat.format(report.getDate())));
        fieldsAndValues.put("ResponsiblePerson", format("%s: %s", bundle.getString("ResponsiblePerson"), seller.getName() + " " + seller.getSurname()));
        return fieldsAndValues;
    }

    public ArrayList<String> makeTableHeader() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add(bundle.getString("Product"));
        headers.add(bundle.getString("ExpectedStock"));
        headers.add(bundle.getString("CurrentStock"));
        headers.add(bundle.getString("ExpectedValue"));
        headers.add(bundle.getString("CurrentValue"));
        return headers;
    }

    public ArrayList<ArrayList<String>> makeTableContent() {
        ArrayList<ArrayList<String>> content = new ArrayList<>();
        com.bdular.inventorytracker.data.inventory.data.InventoryReport report = getItem();
        Map<Product, Integer> expectedStock = report.getExpectedStock();
        Map<Product, Integer> currentStock = report.getCurrentStock();
        report.getProducts().forEach(product -> {
            ArrayList<String> row = new ArrayList<>();
            row.add(product.getName());
            row.add(expectedStock.get(product).toString());
            row.add(currentStock.get(product).toString());
            content.add(row);
        });
        return content;
    }
}
