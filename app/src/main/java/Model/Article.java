package Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Beraki on 12/19/2016.
 */

public class Article {

    private String article_title;
    private String article_text;
    private String date_created;
    private JSONArray data=new JSONArray();

    public Article(){

    }

    public JSONObject getArticle(int position) throws JSONException {
        JSONObject article=data.getJSONObject(position);
        return article;
    }

    public int getArticleCount(){
        return data.length();
    }
}
