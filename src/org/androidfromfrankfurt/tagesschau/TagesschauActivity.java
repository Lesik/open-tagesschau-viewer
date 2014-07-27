package org.androidfromfrankfurt.tagesschau;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

public class TagesschauActivity extends Activity {
    private MyListAdapter tagesschauAdapter;
    private ArrayList<TagesschauItem> tagesschauItemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FadingActionBarHelper helper = new FadingActionBarHelper()
            .actionBarBackground(R.drawable.ab_background)
            .headerLayout(R.layout.activity_tagesschau_header)
            .contentLayout(R.layout.activity_tagesschau_content);
        setContentView(helper.createView(this));
        helper.initActionBar(this);
        
        tagesschauItemArray = new ArrayList<TagesschauItem>();
        tagesschauAdapter = new MyListAdapter(tagesschauItemArray, getApplication());
        ListView list = (ListView)findViewById(android.R.id.list);
    	list.setDivider(null);
    	list.setDividerHeight(20);
    	list.setPadding(20, 20, 20, 20);
    	list.setClipToPadding(false);	// !important
    	list.setVerticalScrollBarEnabled(true);
    	list.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
    	list.setBackgroundColor(getResources().getColor(R.color.activity_background));
        list.setAdapter(tagesschauAdapter);
        
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
        		try {
        			URL url = new URL("http://www.tagesschau.de/export/video-podcast/webl/tagesschau/");
        	        RssFeed feed = RssReader.read(url);
        	        ArrayList<RssItem> rssItems = feed.getRssItems();
        	        for(RssItem rssItem : rssItems) {
        	            TagesschauItem tagesschauItem = new TagesschauItem();
        	            tagesschauItem.title = rssItem.getTitle();
        	            tagesschauItem.date = rssItem.getPubDate().toString();
        	            tagesschauItem.description = rssItem.getDescription();
        	            tagesschauItemArray.add(tagesschauItem);
        	            runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
						        tagesschauAdapter.notifyDataSetChanged();
							}
						});
        	        }
        		}
        		catch (SAXException e) {
        			e.printStackTrace();
        		}
        		catch (IOException e) {
        			e.printStackTrace();
        		}
            }
        });

        thread.start();
        
//		for (int i = 0; i < tagesschauItemList.size(); i++) {
//			Log.e(STORAGE_SERVICE, tagesschauItemList.get(i).getTitle());
//			TagesschauItem tagesschauItem = new TagesschauItem();
//			tagesschauItem.title = tagesschauItemList.get(i).getTitle();
//			tagesschauItem.date = tagesschauItemList.get(i).getDate();
//			tagesschauItem.description = tagesschauItemList.get(i).getDescription();
//			
//			tagesschauItemArray.add(tagesschauItem);
//		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tagesschau, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
//            changeDataset();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
