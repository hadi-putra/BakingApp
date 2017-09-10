package com.udacity.android.bakingapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.model.IngredientModel;
import com.udacity.android.bakingapp.data.model.StepModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hadi on 10/09/17.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter {
    private final static int INGREDIENT_VIEW_TYPE = 1;
    private final static int STEP_VIEW_TYPE = 2;

    private List<StepModel> steps;
    private List<IngredientModel> ingredients;

    private OnStepItemClickListener mListener;

    public RecipeDetailAdapter(OnStepItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void setSteps(List<StepModel> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public void setIngredients(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (ingredients != null && position == 0) return INGREDIENT_VIEW_TYPE;
        return STEP_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == INGREDIENT_VIEW_TYPE){
            viewHolder = new IngredientViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recipe_detail_ingredients, parent, false));
        } else {
            viewHolder = new StepViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_step_list_layout, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == INGREDIENT_VIEW_TYPE){
            ((IngredientViewHolder) holder).bind(ingredients);
        } else {
            int ingredientSize = (ingredients == null || ingredients.isEmpty()) ? 0 : 1;
            ((StepViewHolder) holder).bind(steps.get(position - ingredientSize));
        }
    }

    @Override
    public int getItemCount() {
        int ingredientSize = (ingredients == null || ingredients.isEmpty()) ? 0 : 1;
        int stepSize = (steps == null) ? 0 : steps.size();
        return stepSize + ingredientSize;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients) TextView mTVIngredients;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(List<IngredientModel> ingredients) {
            String text = "INGREDIENTS\n";

            for (IngredientModel ingredient : ingredients){
                text+= String.format(itemView.getContext()
                        .getString(R.string.ingredient_template), ingredient.getQuantity(),
                        ingredient.getMeasure(), ingredient.getIngredient());
            }

            mTVIngredients.setText(text);
        }
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_image_placeholder)
        ImageView mStepPlaceHolder;

        @BindView(R.id.step_title) TextView mTVStepDescr;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(StepModel step) {
            mTVStepDescr.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            if (mListener != null)
                mListener.onStepClick(steps.get(getStepPosition(getAdapterPosition())));
        }

        private int getStepPosition(int adapterPosition) {
            int ingredientSize = (ingredients == null || ingredients.isEmpty()) ? 0 : 1;
            return adapterPosition-ingredientSize;
        }
    }

    public interface OnStepItemClickListener{
        void onStepClick(StepModel step);
    }
}