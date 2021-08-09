package com.bdular.inventorytracker.utils.documents.pdf;

import com.bdular.inventorytracker.data.user.seller.data.Expenses;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import com.itextpdf.layout.element.Table;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.bdular.inventorytracker.data.user.seller.data.Expense.TRAVELLING;
import static com.bdular.inventorytracker.utils.DateHelper.dateFormat;
import static com.bdular.inventorytracker.utils.DateHelper.yearFormat;
import static com.bdular.inventorytracker.utils.ResourceUtil.EXPENSES_TEMPLATE_PATH;
import static com.bdular.inventorytracker.utils.ResourceUtil.getExpenseFilePath;
import static com.itextpdf.layout.property.HorizontalAlignment.CENTER;
import static com.itextpdf.layout.property.VerticalAlignment.MIDDLE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

public class ExpensesDocument extends PDFMaker<Seller> {

    static ResourceBundle bundle = ResourceBundle.getBundle("pdf-translations");
    AtomicReference<BigDecimal> distance = new AtomicReference<>(ZERO.setScale(3, HALF_EVEN));
    AtomicReference<BigDecimal> totalCost = new AtomicReference<>(ZERO.setScale(3, HALF_EVEN));

    public ExpensesDocument(Seller item) throws IOException {
        super(item);
        setTemplatePath(EXPENSES_TEMPLATE_PATH);
        setFilePath(getExpenseFilePath(item));
        makePDF();
    }

    @Override
    public void makePDF() throws IOException {
        super.makePDF();
        addMetadata(bundle.getString("ExpenseReport"), "Auto-generated", "expense report");
        fillHeader(createHeaderContent());
        createTable(makeTableHeaders(), new float[]{100, 100, 100, 100}, createContent());
        Table table = getTable();
        addTableFooter(table);
        table.setRelativePosition(0, 300, 0, 0);
        table.setHorizontalAlignment(CENTER);
        table.setVerticalAlignment(MIDDLE);
        addTableToDocument(table);
        close();
    }

    private Map<String, String> createHeaderContent() {
        Map<String, String> fieldsAndValues = new HashMap<>();
        Seller seller = getItem();
        fieldsAndValues.put("ExpenseTitle", bundle.getString("ExpenseTitle"));
        fieldsAndValues.put("SellerID", seller.getID());
        fieldsAndValues.put("SellerName", format("%s %s", seller.getName(), seller.getSurname()));
        fieldsAndValues.put("Year", yearFormat.format(LocalDateTime.now()));
        fieldsAndValues.put("DocumentDate", dateFormat.format(LocalDateTime.now()));
        return fieldsAndValues;
    }

    private ArrayList<String> makeTableHeaders() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add(bundle.getString("ExpenseType"));
        headers.add(bundle.getString("ExpenseDate"));
        headers.add(bundle.getString("ExpenseTag"));
        headers.add(bundle.getString("ExpenseCost"));
        return headers;
    }

    private ArrayList<ArrayList<String>> createContent() {
        List<Expenses> expenses = getItem().getYearlyExpenses();
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        expenses.forEach(expense -> {
            HashMap<String, BigDecimal> hashMap = expense.getCost();
            hashMap.forEach((key, value) -> {
                ArrayList<String> row = new ArrayList<>();
                row.add(format("%s", bundle.getString(expense.getType().name())));
                LocalDateTime date = expense.getDate();
                if (expense.getType().equals(TRAVELLING)) {
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    int week = date.get(weekFields.weekOfWeekBasedYear());
                    row.add(format("%d", week)); //week number
                    distance.set(distance.get().add(value));
                } else {
                    row.add(format("%s", dateFormat.format(date)));
                    totalCost.set(totalCost.get().add(value));
                }
                row.add(format("%s", key)); //expense item name/tag
                row.add(format("%.2f", value.doubleValue())); //expense cost
                rows.add(row);
            });
        });
        return rows;
    }

    private void addTableFooter(Table table) {
        table.addFooterCell(bundle.getString("DistanceTotal"));
        table.addFooterCell(format("%.2f", distance.get().doubleValue()));
        table.addFooterCell(bundle.getString("ExpensesTotal"));
        table.addFooterCell(format("%.2f", totalCost.get().doubleValue()));
    }
}
