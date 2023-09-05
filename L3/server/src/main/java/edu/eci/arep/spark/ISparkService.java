package edu.eci.arep.spark;

public interface ISparkService {

    /**
     * Execute the code for their endpoint
     */
    byte[] execute(String[] params) throws Exception;
}
