package com.erg.appool;

import java.awt.Graphics2D;
import net.sourceforge.tess4j.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

public class TestTesseract {

    public static void
    processImg(BufferedImage ipimage,
               float scaleFactor,
               float offset)
            throws IOException, TesseractException
    {
        // Making an empty image buffer
        // to store image later
        // ipimage is an image buffer
        // of input image
        BufferedImage opimage
                = new BufferedImage(1050,
                1024,
                ipimage.getType());

        // creating a 2D platform
        // on the buffer image
        // for drawing the new image
        Graphics2D graphic
                = opimage.createGraphics();

        // drawing new image starting from 0 0
        // of size 1050 x 1024 (zoomed images)
        // null is the ImageObserver class object
        graphic.drawImage(ipimage, 0, 0,
                1050, 1024, null);
        graphic.dispose();

        // rescale OP object
        // for gray scaling images
        RescaleOp rescale
                = new RescaleOp(scaleFactor, offset, null);

        // performing scaling
        // and writing on a .png file
        BufferedImage fopimage
                = rescale.filter(opimage, null);
        ImageIO
                .write(fopimage,
                        "jpg",
                        new File("C:\\Workspaces\\testData\\out1.png"));

        // Instantiating the Tesseract class
        // which is used to perform OCR
        Tesseract it = new Tesseract();

        //it.setDatapath("D:\\Program Files\\Workspace\\Tess4J");

        // doing OCR on the image
        // and storing result in string str
        String str = it.doOCR(fopimage);
        System.out.println(str);
    }

    public static void main(String args[]) throws Exception
    {
        File f
                = new File(
                "C:\\Workspaces\\testData\\HPScan_0804093601_JPG.jpg");

        BufferedImage ipimage = ImageIO.read(f);

        // getting RGB content of the whole image file
        double d
                = ipimage
                .getRGB(ipimage.getTileWidth() / 2,
                        ipimage.getTileHeight() / 2);

        // comparing the values
        // and setting new scaling values
        // that are later on used by RescaleOP
        if (d >= -1.4211511E7 && d < -7254228) {
            processImg(ipimage, 3f, -10f);
        }
        else if (d >= -7254228 && d < -2171170) {
            processImg(ipimage, 1.455f, -47f);
        }
        else if (d >= -2171170 && d < -1907998) {
            processImg(ipimage, 1.35f, -10f);
        }
        else if (d >= -1907998 && d < -257) {
            processImg(ipimage, 1.19f, 0.5f);
        }
        else if (d >= -257 && d < -1) {
            processImg(ipimage, 1f, 0.5f);
        }
        else if (d >= -1 && d < 2) {
            processImg(ipimage, 1f, 0.35f);
        }
    }
}

