package code.github.realmmodels;

import io.realm.RealmObject;

/**
 * Created by shank on 21/07/17.
 */

public class RealmFilmLocation extends RealmObject {

    private String actor_1;
    private String actor_2;
    private String actor_3;
    private String director;

    public String getActor_1() {
        return actor_1;
    }

    public void setActor_1(String actor_1) {
        this.actor_1 = actor_1;
    }

    public String getActor_2() {
        return actor_2;
    }

    public void setActor_2(String actor_2) {
        this.actor_2 = actor_2;
    }

    public String getActor_3() {
        return actor_3;
    }

    public void setActor_3(String actor_3) {
        this.actor_3 = actor_3;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getProduction_company() {
        return production_company;
    }

    public void setProduction_company(String production_company) {
        this.production_company = production_company;
    }

    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }



    private String locations;
    private String production_company;
    private String release_year;
    private String title;
    private String writer;






}
