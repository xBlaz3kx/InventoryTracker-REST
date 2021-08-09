package com.bdular.inventorytracker.utils;

import com.bdular.inventorytracker.data.inventory.data.InventoryReport;
import com.bdular.inventorytracker.data.order.data.Order;
import com.bdular.inventorytracker.data.supplier.data.DeliveryReport;
import com.bdular.inventorytracker.data.supplier.data.SupplierOrder;
import com.bdular.inventorytracker.data.user.seller.data.Seller;

import java.util.Date;
import java.util.ResourceBundle;

import static com.bdular.inventorytracker.utils.DateHelper.format;
import static com.bdular.inventorytracker.utils.DateHelper.yearFormat;
import static java.lang.String.format;

public class ResourceUtil {

    static ResourceBundle bundle = ResourceBundle.getBundle("pdf-translations");

    public static final String INVOICE_TEMPLATE_PATH = "Invoices\\InvoiceTemplate.pdf";
    public static final String DELIVERY_REPORT_TEMPLATE_PATH = "Reports\\DeliveryReportTemplate.pdf";
    public static final String EXPENSES_TEMPLATE_PATH = "Reports\\ExpenseTemplate.pdf";
    public static final String INVENTORY_TEMPLATE_PATH = "Reports\\InventoryReportTemplate.pdf";

    public static String getExpenseFilePath(Seller seller) {
        String fileName = format("%s %s %s.pdf", bundle.getString("Expenses"), seller.getName() + " " + seller.getSurname(), yearFormat.format(new Date()));
        return format("Reports\\Expenses\\%s", fileName);
    }

    public static String getOrderFilePath(Order order) {
        String fileName = format("%s %d %s.pdf", bundle.getString("Order"), order.getReference(), format.format(order.getTimestamp()));
        return format("Invoices\\%s", fileName);
    }

    public static String getSupplierOrderFilePath(SupplierOrder order) {
        String fileName = format("%s %s %s.pdf", bundle.getString("SupplierOrder"), order.getID(), format.format(order.getOrderDate()));
        return format("Reports\\Expenses\\%s", fileName);
    }

    public static String getDeliveryReportFilePath(DeliveryReport report) {
        String fileName = format("%s %s %s.pdf", bundle.getString("DeliveryReport"), report.getID(), format.format(report.getDate()));
        return format("Reports\\Delivery\\%s", fileName);
    }

    public static String getInventoryReportFilePath(InventoryReport report) {
        String fileName = format("%s %s %s.pdf", bundle.getString("Inventory"), report.getId(), format.format(report.getDate()));
        return format("Reports\\Inventory\\%s", fileName);
    }
}
