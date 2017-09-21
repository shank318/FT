package code.github.pojo;

import com.google.gson.annotations.SerializedName;

import code.github.realmmodels.RealmFilmLocation;


/**
 * Created by shank on 05/09/17.
 */
public class FilmLocation {

    @SerializedName("actor_1")
    private String actor1;
    @SerializedName("actor_2")
    private String actor2;
    @SerializedName("actor_3")
    private String actor3;
    @SerializedName("director")
    private String director;
    @SerializedName("locations")
    private String locations;
    @SerializedName("production_company")
    private String productionCompany;

    public String getActor1() {
        return actor1;
    }

    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    public String getActor2() {
        return actor2;
    }

    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    public String getActor3() {
        return actor3;
    }

    public void setActor3(String actor3) {
        this.actor3 = actor3;
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

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
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

    @SerializedName("release_year")
    private String releaseYear;
    @SerializedName("title")
    private String title;
    @SerializedName("writer")
    private String writer;
    private double lng;
    private double lat;

    public FilmLocation(RealmFilmLocation filmLocation) {
        title = filmLocation.getTitle();
        locations = filmLocation.getLocations();
        actor1 = filmLocation.getActor_1();
        actor2 = filmLocation.getActor_2();
        actor3 = filmLocation.getActor_3();
        director = filmLocation.getDirector();
        locations = filmLocation.getLocations();
        productionCompany = filmLocation.getProduction_company();
        writer = filmLocation.getWriter();
        releaseYear = filmLocation.getRelease_year();
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


}
