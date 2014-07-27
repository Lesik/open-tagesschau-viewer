package org.androidfromfrankfurt.tagesschau;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {
	 
	private ArrayList<TagesschauItem> list;
	private Context mContext;
	private Button downloadButton;
	private ImageButton threeDotButton;
	public int lastPosition = -1;
	
	
	public static class ViewHolder {
		TextView title;
		TextView date;
		TextView description;
	}

	public MyListAdapter(ArrayList<TagesschauItem> itemArray, Context context) {
		list = itemArray;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		
        if(convertView == null) {
    		convertView = View.inflate(mContext, R.layout.card, null);
        	
        	holder = new ViewHolder();
            holder.title 			= (TextView)	convertView.findViewById(R.id.card_title);
            holder.date 			= (TextView)	convertView.findViewById(R.id.card_date);
            holder.description		= (TextView)	convertView.findViewById(R.id.card_description);
        	
        	downloadButton 			= (Button)		convertView.findViewById(R.id.card_button_download);
            threeDotButton			= (ImageButton) convertView.findViewById(R.id.card_button_threedot);
            
            convertView.setTag(holder);
//            setListeners(holder, index);
        }
        else {
        	holder = (ViewHolder)convertView.getTag();
        }
        
        // Put the values in the holder
        holder.title		.setText(list.get(index).title);
        holder.date			.setText(list.get(index).date);
        holder.description	.setText(list.get(index).description);
        
        // Animation
        Animation animation = AnimationUtils.loadAnimation(mContext, (index > lastPosition) ? R.anim.anim_scroll_up : R.anim.anim_scroll_down);
        convertView.startAnimation(animation);
        lastPosition = index;
        
        return convertView;
	}
}