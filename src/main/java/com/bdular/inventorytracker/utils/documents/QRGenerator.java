package com.bdular.inventorytracker.utils.documents;

import com.bdular.inventorytracker.data.order.data.Order;
import com.bdular.inventorytracker.data.supplier.data.DeliveryReport;
import com.bdular.inventorytracker.data.supplier.data.SupplierOrder;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import static com.google.zxing.BarcodeFormat.QR_CODE;
import static com.google.zxing.EncodeHintType.ERROR_CORRECTION;
import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.L;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static java.lang.String.format;

@Service
public class QRGenerator {

    @Autowired
    @Value("${server.address}")
    private String address;
    private static final int size = 55;

    public <S extends Order> BufferedImage createOrderQRCode(S order) throws WriterException {
        String URL = format("%s\\api\\orders\\order\\%s", address, order.getID());
        return createQR(URL);
    }

    public BufferedImage createDeliveryReportQRCode(DeliveryReport report) throws WriterException {
        String URL = format("%s\\api\\suppliers\\delivery-reports\\%s", address, report.getID());
        return createQR(URL);
    }

    public BufferedImage createSupplierOrderQRCode(SupplierOrder order) throws WriterException {
        String URL = format("%s\\api\\suppliers\\order\\%s", address, order.getID());
        return createQR(URL);
    }

    private static BufferedImage createQR(String qrCodeText) throws WriterException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(ERROR_CORRECTION, L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, QR_CODE, size, size, hintMap);
        int matrixWidth = byteMatrix.getWidth();

        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        return image;
    }
}
