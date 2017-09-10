package com.udacity.android.bakingapp.data;

import com.udacity.android.bakingapp.data.model.RecipeModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by hadi on 09/08/17.
 */

public interface BakingApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<List<RecipeModel>> getRecipeList();
}
