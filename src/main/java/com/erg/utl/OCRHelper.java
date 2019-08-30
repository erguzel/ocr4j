package com.erg.utl;

import com.erg.enm.LanguageTypes;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRHelper {

    public static class TesseractOCR {

        private static Tesseract TesseractEngine = new Tesseract();


        public static String GetOCRTex(String inputImagePath, LanguageTypes language) throws TesseractException {

            switch (language){
                case deu:
                    TesseractEngine.setLanguage("deu");
                    break;
                case eng:
                    TesseractEngine.setLanguage("eng");
                    break;
                    default:
                        break;
            }

            String ocrText = TesseractEngine.doOCR(new File(inputImagePath));

            return ocrText;
        }
    }
}
