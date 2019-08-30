package com.erg.appool;

import com.erg.utl.OCRHelper;
import com.erg.utl.PdfConverter;
import com.erg.cpaar.CmdFlag;
import com.erg.cpaar.CmdOption;
import com.erg.cpaar.ParserStarter;
import com.erg.enm.LanguageTypes;
import com.erg.exc.cmdparser.MandatoryArgumentNotProvidedException;
import com.erg.exc.ocrAppTess4j.ImageFileNotSupportedException;
import com.erg.exc.ocrAppTess4j.InputOcrFileIsNotFound;
import com.erg.exc.ocrAppTess4j.OutputOcrDirectoryNotFound;
import net.sourceforge.tess4j.TesseractException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Converts an OCR image or pdf to txt.
 */
public class OcrAppTess4jApp {

    private boolean IS_PDF_TO_TIFF = false;
    private  String FILE_NAME = "";
    private  String OUTPUT_PATH = "";
    private String INPUT_FILE = "";
    private String CONVERTED_INPUT_FILE ="";

    public static void main(String[] args) throws MandatoryArgumentNotProvidedException, ImageFileNotSupportedException, InputOcrFileIsNotFound, OutputOcrDirectoryNotFound, TesseractException, IOException, com.erg.cpaar.MandatoryArgumentNotProvidedException {


        OcrAppTess4jApp app = new OcrAppTess4jApp();
        app.InitializeArguments(args);
        app.ValidateArguments();

        if(app.IS_PDF_TO_TIFF){

            app.CONVERTED_INPUT_FILE =  PdfConverter.ConvertMULTIPdfToTiff(app.INPUT_FILE,app.OUTPUT_PATH);

        }

        app.RunTesseract();
        app.RemoveTempFiles();

    }

    /**
     * Initializes command line arguments
     * @param args
     * @throws MandatoryArgumentNotProvidedException
     */
    private void  InitializeArguments(String [] args) throws MandatoryArgumentNotProvidedException, com.erg.cpaar.MandatoryArgumentNotProvidedException {
        new ParserStarter()
                .AddFlag(new CmdFlag("IsSaveMode","-sv",false))
                .AddOption(new CmdOption("InputFiles","-f",String.class,true))
                .AddOption(new CmdOption("OutDir","-out",String.class,false))
                .AddFlag(new CmdFlag("IsHelp","-help",false))
                .AddFlag(new CmdFlag("IsVerbose","-v",false))
                .AddFlag(new CmdFlag("IsCredentials","-a",false))
                .Parse(args);
    }

    /**
     * Validates files fields etc.
     * Checks input format. If it is pdf, converts it to tiff for Tesseract ocr parsing.
     */
    private void ValidateArguments() throws InputOcrFileIsNotFound, OutputOcrDirectoryNotFound, ImageFileNotSupportedException {

        File inputFile = new File(ParserStarter.ParsedOutputs.CmdOptions.get("InputFiles").get(0).toString());
        File outputDir = null;
        try{

             outputDir = new File(ParserStarter.ParsedOutputs.CmdOptions.get("OutDir").get(0).toString());

        }catch (NullPointerException ex){

             outputDir = new File(System.getenv("TEMP"));
        }

        if(!inputFile.exists()){

            throw new InputOcrFileIsNotFound("Input file for OCR resolving is not existing in where it supposed to be.",
                    true,inputFile.toString(),"Nothing");
        }

        if(ParserStarter.ParsedOutputs.CmdFlags.get("IsSaveMode")){
            if(!outputDir.exists()){
                throw new OutputOcrDirectoryNotFound("Output dir for OCR resolving is not existing in where it supposed to be.",
                        true,inputFile.toString(),"Nothing");
            }

        }

        boolean isValidFile = inputFile.toString().endsWith(".tif") |
                inputFile.toString().endsWith(".tiff") |
                inputFile.toString().endsWith(".jpg") |
                inputFile.toString().endsWith(".jpeg") |
                inputFile.toString().endsWith(".png") |
                inputFile.toString().endsWith(".bmp") |
                inputFile.toString().endsWith(".img") |
                inputFile.toString().endsWith(".pdf");

        if(!isValidFile){

            throw new ImageFileNotSupportedException("Provided image file is not supported either by TesseractOCR engine or ICEPDF pdf converter",
                    true,inputFile.toString(),"Nothing");
        }



        if(inputFile.toString().endsWith(".pdf")){

            this.IS_PDF_TO_TIFF = true;
        }

        this.INPUT_FILE = inputFile.toString();
        this.FILE_NAME =  inputFile.getName().split("\\.")[0].trim();

        //this.OUTPUT_PATH = inputFile.toString().replace(this.FILE_NAME,this.FILE_NAME+"_TESS");
        this.OUTPUT_PATH = outputDir.toString();

    }

    public void RunTesseract() throws TesseractException, IOException {
        String ocrText = "";

        ocrText = OCRHelper.TesseractOCR.GetOCRTex(this.CONVERTED_INPUT_FILE, LanguageTypes.deu);

        if(ParserStarter.ParsedOutputs.CmdFlags.get("IsSaveMode")){

            BufferedWriter writer = new BufferedWriter(new FileWriter(this.OUTPUT_PATH+"\\"+FILE_NAME+".txt"));
            writer.write(ocrText);
            writer.close();

        }

        System.out.println(ocrText);
    }

    public void RemoveTempFiles(){


        File fin = new File(this.INPUT_FILE);
        File cin = new File(this.CONVERTED_INPUT_FILE);

        if(fin.exists())
            fin.delete();

        if(cin.exists())
            cin.delete();

    }

}
