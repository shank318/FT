package code.github.utils;


        import android.content.Context;
        import android.support.v4.content.ContextCompat;
        import android.widget.ImageView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by shank on 9/21/17.
 */

public class ImageUtil {

    public static void setImage(Context context, String imageUrl, ImageView imageView) {
        if (context == null) {
            return;
        }

        if (isImageUrlValid(imageUrl)) {
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .placeholder(  R.drawable.ic_profile)
                    .crossFade()
                    .into(imageView);
        } else {
            Glide.clear(imageView);
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile));
        }
    }

    public static boolean isImageUrlValid(String url) {
        return !url.isEmpty();
    }
}