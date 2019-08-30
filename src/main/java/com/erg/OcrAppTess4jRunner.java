package com.erg;

import com.erg.appool.OcrAppTess4jApp;
import com.erg.cpaar.MandatoryArgumentNotProvidedException;
import com.erg.exc.ocrAppTess4j.ImageFileNotSupportedException;
import com.erg.exc.ocrAppTess4j.InputOcrFileIsNotFound;
import com.erg.exc.ocrAppTess4j.OutputOcrDirectoryNotFound;
import net.sourceforge.tess4j.TesseractException;


import java.io.IOException;

public class OcrAppTess4jRunner {

    public static void main(String[] args) throws ImageFileNotSupportedException, OutputOcrDirectoryNotFound, IOException, MandatoryArgumentNotProvidedException, InputOcrFileIsNotFound, TesseractException {

        System.out.println(System.getProperty("java.vm.name"));
        System.out.println(System.getProperty("java.vm.info"));

        String [] _args = {"-f", "/Users/olgunerguzel/Documents/new-job-usa/olgun-erguzel-cv.pdf", "-out", "/Users/olgunerguzel/Documents"};




        try {

            OcrAppTess4jApp.main(_args);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getStackTrace());
            System.exit(-1);
        }

    }
}
