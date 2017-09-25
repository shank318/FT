package code.github.features.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import code.github.R;
import code.github.pojo.Repository;

/**
 * Created by shank on 24/08/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private List<Repository> searchRepositories;
    private Context mContext;


    public SearchAdapter(Context context, List<Repository> searchRepositories){
        this.searchRepositories = searchRepositories;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.stars)
        TextView stars;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_search,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Repository repository = searchRepositories.get(position);
        holder.title.setText(repository.getName());
        holder.description.setText(repository.getDescription());
        holder.stars.setText("Stars: "+ repository.getStargazersCount());
    }

    @Override
    public int getItemCount(){
        return searchRepositories.size();
    }


}
