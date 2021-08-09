package com.bdular.inventorytracker.utils.documents.pdf.supplier;

import com.bdular.inventorytracker.data.supplier.data.SupplierOrder;
import com.bdular.inventorytracker.utils.documents.pdf.PDFMaker;

import java.io.IOException;
import java.util.ResourceBundle;

import static com.bdular.inventorytracker.utils.ResourceUtil.getSupplierOrderFilePath;

public class SupplierOrderDocument extends PDFMaker<SupplierOrder> {

    static ResourceBundle bundle = ResourceBundle.getBundle("pdf-translations");

    public SupplierOrderDocument(SupplierOrder item) throws IOException {
        super(item);
        setFilePath(getSupplierOrderFilePath(item));
        makePDF();
    }

    @Override
    public void makePDF() throws IOException {
        super.makePDF();

        close();
    }
}
