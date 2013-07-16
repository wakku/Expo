package com.appstar.expo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Tab viewPager animation
 * 
 * @author M.Z
 * 
 */
public class Kiosk extends Activity {
	// ViewPager is a build-in class in the google SDK package, used for transition between screens.
	// android-support-v4.jar
	private ViewPager mPager;
	private List<View> listViews; // Tab listView
	private ImageView cursor;// image for the animation
	private TextView t1, t2, t3;// tab title
	private int offset = 0;// the image offset value
	private int currIndex = 0;// current tab index
	private int bmpW;// width of the cursor image

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kiosk_tab);
		InitImageView();
		InitTextView();
		InitViewPager();
	}

	/**
	 * Initialize the tab title textview.
	 */
	private void InitTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * Initialize ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		listViews.add(mInflater.inflate(R.layout.lay1, null));
		listViews.add(mInflater.inflate(R.layout.lay2, null));
		listViews.add(mInflater.inflate(R.layout.event_item, null));
		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * Initialize Image View
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 
		offset = (screenW / 3 - bmpW) / 2;// 
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 
	}

	/**
	 * ViewPager Adapter
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
            if (arg1 < 3) {
                ((ViewPager) arg0).addView(mListViews.get(arg1 % 3), 0);
            }
            // Tab 1 button
            if (arg1 == 1) {
                Button btn = (Button) arg0.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(Kiosk.this)
                                .setTitle("Sorry")
                                .setMessage("You don't have access to this tool! :(")
                                .setNegativeButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                            }
                                        }).show();
                    }
                });
            }else if (arg1 == 2) {
            	Intent intent = new Intent();
            	intent.setClass(Kiosk.this, EventList.class);
    			startActivity(intent);
            }
            return mListViews.get(arg1 % 3);
        }

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * tab listener
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * Change listener
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// Page 1 to Page2
		int two = one * 2;// Page 1 to Page 3

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:Image kept at where animation stopped.
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
}