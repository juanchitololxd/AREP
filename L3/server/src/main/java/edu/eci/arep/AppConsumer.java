package edu.eci.arep;

import edu.eci.arep.persistence.PersistenceManage;
import edu.eci.arep.persistence.file.FileManageTarget;
import edu.eci.arep.persistence.file.IFileManage;
import edu.eci.arep.services.MovieService;
import edu.eci.arep.spark.SparkClone;

import java.io.IOException;

public class AppConsumer {

    static final IFileManage fileManager = new FileManageTarget("public/img", "public");
    static final SparkClone sparkClone = new SparkClone();
    static final MovieService movieService = new MovieService(PersistenceManage.getMovieDAO());

    public static void main(String[] args) throws IOException {
        sparkClone.addService("GET", "/movies", (arg) -> movieService.getMovie(arg[0]).getBytes());
        sparkClone.addService("POST", "/posting", (arg) -> "POSTEADO".getBytes());
        sparkClone.addService("GET", "/hello", (arg) -> "ejecutado".getBytes());
        sparkClone.addService("GET", "/files", (arg) -> loadFile(arg, "FILE"));
        sparkClone.addService("GET", "/images", (arg) -> loadFile(arg, "IMG"));
        sparkClone.start();
    }

    public static byte[] loadFile(String[] args, String tipo) throws Exception {
        byte[] rta;
        if (tipo.equals("FILE")) rta = fileManager.getFile(args[0]).getBytes();
        else rta = fileManager.writeImage(args[0]);
        return rta;

    }
}
