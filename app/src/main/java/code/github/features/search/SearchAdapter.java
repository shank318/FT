package code.github.features.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import code.github.pojo.FilmLocation;
import code.github.pojo.Item;

/**
 * Created by shank on 24/08/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private List<Item> searchItems;
    private Context mContext;


    public SearchAdapter(Context context, List<Item> searchItems){
        this.searchItems = searchItems;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        ImageView imageView;

        @BindView(R.id.overlay)
        View view;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.sub_title)
        TextView subTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_collections,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        Item item = searchItems.get(position);
//        holder.view.setBackgroundColor(Color.parseColor(item.getVibrantColor()));
//        ImageUtil.setImage(mContext,item.getImage(),holder.imageView);
//        holder.title.setText(item.getCollectionName());
//        holder.subTitle.setText(item.getCountText());
    }

    @Override
    public int getItemCount(){
        return searchItems.size();
    }


}
