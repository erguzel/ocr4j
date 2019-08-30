package com.erg.utl;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


import org.icepdf.core.exceptions.*;
import org.icepdf.core.pobjects.*;
import org.icepdf.core.util.GraphicsRenderingHints;

public class PdfConverter {


    public static void ConvertPdfToTiff(String pdfFilePath, String outDir){

        String outputName = new File(pdfFilePath).getName().replace(".pdf","").trim();

        Document document = new Document();
        try {
            document.setFile(pdfFilePath);
        } catch (PDFException ex) {
            System.out.println("Error parsing PDF document " + ex);
        } catch (PDFSecurityException ex) {
            System.out.println("Error encryption not supported " + ex);
        } catch (FileNotFoundException ex) {
            System.out.println("Error file not found " + ex);
        } catch (IOException ex) {
            System.out.println("Error IOException " + ex);
        }
        float scale = 1.0f;
        float rotation = 0f;
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage) document.getPageImage(
                    i, GraphicsRenderingHints.PRINT, Page.BOUNDARY_CROPBOX, rotation, scale);
            RenderedImage rendImage = image;
            try {
                System.out.println(" capturing page " + i);
                File file = new File(outDir+"\\"+outputName+"_" + i + ".tiff");
                ImageIO.write(rendImage, "tiff", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.flush();
        }
        document.dispose();
    }

    public static String ConvertMULTIPdfToTiff(String pdfFilePath, String outDir) {

         String outName = new File(pdfFilePath).getName().replace(".pdf","").trim();
         String _OUT_FILE ="";
         double FAX_RESOLUTION = 200.0;
         double PRINTER_RESOLUTION = 300.0;
        // This compression type may be wpecific to JAI ImageIO Tools
         String COMPRESSION_TYPE_GROUP4FAX = "CCITT T.6";

            // Verify that ImageIO can output TIFF
            Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("tiff");
            if (!iterator.hasNext()) {
                System.out.println(
                        "ImageIO missing required plug-in to write TIFF files. " +
                                "You can download the JAI ImageIO Tools from: " +
                                "https://jai-imageio.dev.java.net/");
                return "NA";
            }
            boolean foundCompressionType = false;
            for(String type : iterator.next().getDefaultWriteParam().getCompressionTypes()) {
                if (COMPRESSION_TYPE_GROUP4FAX.equals(type)) {
                    foundCompressionType = true;
                    break;
                }
            }
            if (!foundCompressionType) {
                System.out.println(
                        "TIFF ImageIO plug-in does not support Group 4 Fax " +
                                "compression type ("+COMPRESSION_TYPE_GROUP4FAX+")");
                return "NA";
            }
            // Get a file from the command line to open
            String filePath = pdfFilePath;
            // open the url
            Document document = new Document();
            try {
                document.setFile(filePath);
            } catch (PDFException ex) {
                System.out.println("Error parsing PDF document " + ex);
            } catch (PDFSecurityException ex) {
                System.out.println("Error encryption not supported " + ex);
            } catch (FileNotFoundException ex) {
                System.out.println("Error file not found " + ex);
            } catch (IOException ex) {
                System.out.println("Error handling PDF document " + ex);
            }
            try {
                // save page caputres to file.
                _OUT_FILE = outDir+"\\"+outName+".tif";
                File file = new File(_OUT_FILE);
                ImageOutputStream ios = ImageIO.createImageOutputStream(file);
                ImageWriter writer = ImageIO.getImageWritersByFormatName("tiff").next();
                writer.setOutput(ios);
                // Paint each pages content to an image and write the image to file
                for (int i = 0; i < document.getNumberOfPages(); i++) {
                    final double targetDPI = PRINTER_RESOLUTION;
                    float scale = 1.0f;
                    float rotation = 0f;
                    // Given no initial zooming, calculate our natural DPI when
                    // printed to standard US Letter paper
                    PDimension size = document.getPageDimension(i, rotation, scale);
                    double dpi = Math.sqrt((size.getWidth()*size.getWidth()) +
                            (size.getHeight()*size.getHeight()) ) /
                            Math.sqrt((8.5*8.5)+(11*11));
                    // Calculate scale required to achieve at least our target DPI
                    if (dpi < (targetDPI-0.1)) {
                        scale = (float) (targetDPI / dpi);
                        size = document.getPageDimension(i, rotation, scale);
                    }
                    int pageWidth = (int) size.getWidth();
                    int pageHeight = (int) size.getHeight();
                    int[] cmap = new int[] { 0xFF000000, 0xFFFFFFFF };
                    IndexColorModel cm = new IndexColorModel(
                            1, cmap.length, cmap, 0, false, Transparency.BITMASK,
                            DataBuffer.TYPE_BYTE);
                    BufferedImage image = new BufferedImage(
                            pageWidth, pageHeight, BufferedImage.TYPE_BYTE_BINARY, cm);
                    Graphics g = image.createGraphics();
                    document.paintPage(
                            i, g, GraphicsRenderingHints.PRINT, Page.BOUNDARY_CROPBOX,
                            rotation, scale);
                    g.dispose();
                    // capture the page image to file
                    IIOImage img = new IIOImage(image, null, null);
                    ImageWriteParam param = writer.getDefaultWriteParam();
                    param.setCompressionMode(param.MODE_EXPLICIT);
                    param.setCompressionType(COMPRESSION_TYPE_GROUP4FAX);
                    if (i == 0) {
                        writer.write(null, img, param);
                    }
                    else {
                        writer.writeInsert(-1, img, param);
                    }
                    image.flush();
                }
                ios.flush();
                ios.close();
                writer.dispose();
            }
            catch(IOException e) {
                System.out.println("Error saving file " + e);
                e.printStackTrace();
            }
            // clean up resources
            document.dispose();

            return _OUT_FILE;
        }



}

