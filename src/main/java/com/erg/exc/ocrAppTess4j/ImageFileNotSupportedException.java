package com.erg.exc.ocrAppTess4j;

import com.erg.exc.BaseGeneralException;

public class ImageFileNotSupportedException extends BaseGeneralException {
    /**
     * Creates an instance of BaseGeneralException.
     *
     * @param whatHappened    as detailed reason
     * @param endGame         true if App needs to end
     * @param causeReference  data as the exception reason.
     * @param resultReference data as the exception result.
     */
    public ImageFileNotSupportedException(String whatHappened, Boolean endGame, String causeReference, String resultReference) {
        super(whatHappened, endGame, causeReference, resultReference);
    }
}
