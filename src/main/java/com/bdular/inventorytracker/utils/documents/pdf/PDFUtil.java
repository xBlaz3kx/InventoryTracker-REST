package com.bdular.inventorytracker.utils.documents.pdf;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.itextpdf.layout.property.TextAlignment.CENTER;

public class PDFUtil {

    private PdfDocument document;
    private String templatePath, writePath;

    public PDFUtil(String templatePath, String writePath) throws IOException {
        this.templatePath = templatePath;
        this.writePath = writePath;
        if (!new File(templatePath).exists()) {
            throw new FileNotFoundException("Template file doesnt exist");
        }
        this.document = new PdfDocument(new PdfReader(templatePath), new PdfWriter(writePath));
    }

    public PDFUtil(PdfDocument document) {
        this.document = document;
    }

    public void addMetadata(@NotNull String title, @NotNull String author, @NotNull String keywords) {
        PdfDocumentInfo documentInfo = document.getDocumentInfo();
        documentInfo.addCreationDate();
        documentInfo.setTitle(title);
        documentInfo.setKeywords(keywords);
        documentInfo.setAuthor(author);
        documentInfo.setCreator("Auto-generated PDF");
    }

    public void insertIntoForm(@NotNull Map<String, String> fieldsAndValues) {
        PdfAcroForm form = PdfAcroForm.getAcroForm(document, false);
        Map<String, PdfFormField> fields = form.getFormFields();
        fieldsAndValues.keySet().forEach(key -> fields.get(key).setValue(fieldsAndValues.get(key)));
        form.flattenFields();
    }

    public static Table createTable(@NotNull ArrayList<String> headerNames, @NotNull float[] columnWidths, @NotNull ArrayList<ArrayList<String>> rows) {
        Table table = new Table(columnWidths);
        makeHeader(table, headerNames);
        rows.forEach(row -> insertNewRow(table, row));
        return table;
    }

    private static void makeHeader(@NotNull Table table, @NotNull ArrayList<String> content) {
        content.forEach(line -> {
            Cell c1 = new Cell().add(new Paragraph(line)).setTextAlignment(CENTER);
            c1.setBold();
            table.addHeaderCell(c1);
        });
    }

    private static void insertNewRow(@NotNull Table table, @NotNull ArrayList<String> content) {
        content.forEach(line -> {
            Cell c1 = new Cell().add(new Paragraph(line)).setTextAlignment(CENTER);
            table.addCell(c1);
        });
    }

    public static void addEmptyLine(@NotNull Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(" ");
        }
    }

    public PdfDocument getDocument() {
        return document;
    }

    public Document getDoc() {
        return new Document(document);
    }
}
