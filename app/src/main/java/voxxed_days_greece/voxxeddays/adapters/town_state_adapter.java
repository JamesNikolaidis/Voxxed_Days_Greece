package voxxed_days_greece.voxxeddays.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import voxxed_days_greece.voxxeddays.R;

/**
 * Created by James Nikolaidis on 7/8/2017.
 */

public class town_state_adapter extends ArrayAdapter<String> {
    private Context mContext = null;

    public town_state_adapter(@NonNull Context context, @LayoutRes int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.town_state_adapter,parent,false);
        TextView  mTown_State_TextView = (TextView) mView.findViewById(R.id.ggggg);
        mTown_State_TextView.setText(getItem(position));
        return mView;

    }
}
