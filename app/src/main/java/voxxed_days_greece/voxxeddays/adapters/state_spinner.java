package voxxed_days_greece.voxxeddays.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import voxxed_days_greece.voxxeddays.R;

/**
 * Created by James Nikolaidis on 5/28/2017.
 */

public class state_spinner extends ArrayAdapter<String> {

    private Context mApplicationContext=null;
    private int ArrayLength = 0;
    private LinearLayout linearLayout;
    private TextView divider;
    private ArrayList<Drawable> mImageId;
    private ImageView mImageView;

    public state_spinner(@NonNull Context context , @NonNull String[] objects , Activity mActivity, ArrayList<Drawable> imageId) {
        super(context,R.layout.list_view_items,objects);
        ButterKnife.bind(mActivity);
        mApplicationContext = context;
        ArrayLength = objects.length;
        mImageId = imageId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View spinner_item = LayoutInflater.from(getContext()).inflate(R.layout.list_view_items,parent,false);
        CheckedTextView checkedTextView =(CheckedTextView) spinner_item.findViewById(R.id.checkedTextView1);
        mImageView = (ImageView)spinner_item.findViewById(R.id.choose_state_image);
        mImageView.setImageDrawable(mImageId.get(position));
        checkedTextView.setText(getItem(position));
            if(position==0){
                checkedTextView.setChecked(true);
            }
            if(position==ArrayLength-1){
                linearLayout = (LinearLayout) spinner_item.findViewById(R.id.list_view_item_layout);
                divider = (TextView) spinner_item.findViewById(R.id.divider) ;
                linearLayout.removeView(divider);
            }
        return spinner_item;
    }





}
