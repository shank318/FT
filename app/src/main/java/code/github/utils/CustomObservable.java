package code.github.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

import code.github.pojo.FilmLocation;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by shank on 06/09/17.
 */

 public class CustomObservable implements Observable.OnSubscribe<FilmLocation> {
    private final Context ctx;
    private final FilmLocation locationName;
    private final int maxResults;


    public static Observable<FilmLocation> createObservable(Context ctx, FilmLocation locationName, int maxResults) {
        return Observable.create(new CustomObservable(ctx, locationName, maxResults));
    }

    private CustomObservable(Context ctx, FilmLocation locationName, int maxResults) {
        this.ctx = ctx;
        this.locationName = locationName;
        this.maxResults = maxResults;
    }

    @Override
    public void call(Subscriber<? super FilmLocation> subscriber) {
        Geocoder geocoder = new Geocoder(ctx);
        List<Address> result;
        try {

            result = geocoder.getFromLocationName(locationName.getLocations(), maxResults);
            for(Address address: result){
                Logger.debug(locationName.getLocations()+" "+address.getLatitude()+" "+address.getLongitude());
            }
            if(result.size()>0){
                locationName.setLat(result.get(0).getLatitude());
                locationName.setLng(result.get(0).getLongitude());
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(locationName);
                subscriber.onCompleted();
            }
        } catch (IOException e) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onError(e);
            }
        }
    }
}
