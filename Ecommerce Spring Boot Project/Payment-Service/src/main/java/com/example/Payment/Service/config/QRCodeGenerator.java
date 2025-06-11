package com.example.Payment.Service.config;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;

public class QRCodeGenerator {

    public static byte[] generateQRCodeImage(String url, int width, int height) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(url, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();  // bạn có thể trả về base64 nếu frontend cần
    }
}

