package com.bdular.inventorytracker.utils.documents.pdf;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import org.springframework.core.io.FileSystemResource;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.itextpdf.layout.property.HorizontalAlignment.LEFT;

public abstract class PDFMaker<S> {

    S item;
    private String filePath;
    private String templatePath;
    private PDFUtil maker;
    private Table table;

    public PDFMaker(@NotNull S item) {
        this.item = item;
    }

    public void makePDF() throws IOException {
        this.maker = new PDFUtil(getTemplatePath(), getFilePath());
    }

    public void addMetadata(@NotNull String title, @NotNull String author, @NotNull String keywords) {
        maker.addMetadata(title, author, keywords);
    }

    public void addQRCode(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            Image pdfImage = new Image(ImageDataFactory.create(baos.toByteArray()));
            pdfImage.setRelativePosition(5, 20, 0, 0);
            pdfImage.setHorizontalAlignment(LEFT);
            maker.getDoc().add(pdfImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillHeader(@NotEmpty Map<String, String> fieldsValues) {
        maker.insertIntoForm(fieldsValues);
    }

    public void addTableToDocument(@NotEmpty ArrayList<String> headers, float[] spacing, @NotEmpty ArrayList<ArrayList<String>> content) {
        maker.getDoc().add(PDFUtil.createTable(headers, spacing, content));
    }

    public void addTableToDocument(@NotNull Table table) {
        maker.getDoc().add(table);
    }

    public void createTable(@NotEmpty ArrayList<String> headers, @NotEmpty float[] spacing, @NotEmpty ArrayList<ArrayList<String>> content) {
        this.table = PDFUtil.createTable(headers, spacing, content);
    }

    public void close() {
        maker.getDoc().close();
        maker.getDocument().close();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(@NotNull String filePath) {
        this.filePath = filePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(@NotNull String templatePath) {
        this.templatePath = templatePath;
    }

    public S getItem() {
        return item;
    }

    public void setItem(S item) {
        this.item = item;
    }

    public PDFUtil getMaker() {
        return maker;
    }

    public Table getTable() {
        return table;
    }

    public FileSystemResource getPDF() {
        return new FileSystemResource(getFilePath());
    }
}
