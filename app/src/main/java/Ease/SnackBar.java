package Ease;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Beraki on 1/16/2017.
 */

public class SnackBar{

    Snackbar snackbar;
    View snackView;
    int textColor= Color.WHITE;



    public void showLong(View view, CharSequence text){
        snackbar= Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackView= snackbar.getView();
        TextView tv = (TextView)snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(textColor);
        snackbar.show();
    }
    public void showShort(View view, CharSequence text){
        snackbar= Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackView= snackbar.getView();
        TextView tv = (TextView)snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(textColor);
        snackbar.show();
    }
    public void showForever(View view, CharSequence text){
        snackbar= Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackView= snackbar.getView();
        TextView tv = (TextView)snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(textColor);
        snackbar.show();
    }

    public void hide(){
        if(snackbar.isShown()){
            snackbar.dismiss();
        }
    }
}
