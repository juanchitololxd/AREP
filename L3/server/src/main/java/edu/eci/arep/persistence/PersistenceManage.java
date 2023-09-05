package edu.eci.arep.persistence;


import edu.eci.arep.domain.DomainManage;

public class PersistenceManage {

    public static IMovieDAO getMovieDAO(){
        return new MovieWebDAO(DomainManage.getCache(300000));
    }
}
