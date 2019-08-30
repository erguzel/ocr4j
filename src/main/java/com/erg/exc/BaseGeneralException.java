package com.erg.exc;

/**
 * Represents base exception of CmdParser module.
 */
public class BaseGeneralException extends Exception{

    /**
     * Detailed description of the exception.
     */
    private String _whatHappened;
    /**
     * True if exception forces an application stop.
     */
    private Boolean _endGame;

    /**
     * Creates an instance of BaseGeneralException.
     * @param whatHappened as detailed reason
     * @param endGame true if App needs to end
     * @param causeReference data as the exception reason.
     * @param resultReference data as the exception result.
     */
    public BaseGeneralException(String whatHappened, Boolean endGame, String causeReference, String resultReference){

        _whatHappened = whatHappened;
        _endGame = endGame;


        System.out.println("............... GeneralException.....................");
        System.out.println("                ");
        System.out.println(String.format("Exception: %s",this.getClass().getSimpleName()));
        System.out.println(String.format("Because: %s",this._whatHappened));
        System.out.println(String.format("CausedBy %s",causeReference));
        System.out.println(String.format("ResultedAs %s",resultReference));
        System.out.println("                ");
        System.out.println("............... GeneralException.....................");

        if(endGame){

            System.exit(-1);

        }

    }

}