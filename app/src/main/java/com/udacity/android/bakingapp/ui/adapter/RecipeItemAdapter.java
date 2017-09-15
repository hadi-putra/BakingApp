package com.udacity.android.bakingapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.model.RecipeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hadi on 10/09/17.
 */

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.RecipeItemViewHolder> {
    private List<RecipeModel> mList;
    private final OnItemClickListener mCallable;

    public RecipeItemAdapter(OnItemClickListener mCallable) {
        this.mCallable = mCallable;
    }

    public interface OnItemClickListener {
        void onItemClick(RecipeModel recipe);
    }

    public void setRecipes(List<RecipeModel> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecipeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeItemViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.size();
    }

    class RecipeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_title)
        TextView mTVRecipeTitle;

        @BindView(R.id.recipe_serving)
        TextView mTVRecipeServing;

        public RecipeItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(RecipeModel recipe) {
            mTVRecipeTitle.setText(recipe.getName());
            String serving = String.format(itemView.getContext()
                    .getResources().getQuantityString(R.plurals.serving_format, recipe.getServings())
                    , recipe.getServings());
            mTVRecipeServing.setText(serving);
        }

        @Override
        public void onClick(View view) {
            if (mCallable == null) return;
            mCallable.onItemClick(mList.get(getAdapterPosition()));
        }
    }
}
