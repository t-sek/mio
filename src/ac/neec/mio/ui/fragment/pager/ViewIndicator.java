package ac.neec.mio.ui.fragment.pager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class ViewIndicator extends View {

	private static final float RADIUS = 5.0f;
	private static final float DISTANCE = 30.0f;

	private int numOfViews;
	private static int position;
	private ViewPager viewPager;

	public ViewIndicator(Context context) {
		super(context);
	}

	public ViewIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ViewIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public static int getPosition() {
		return position;
	}

	public void setPosition(final int position) {
		if (position < numOfViews) {
			ViewIndicator.position = position;
			if (viewPager != null) {
				viewPager.setCurrentItem(position);
			}
			invalidate();
		}
	}

	public void setViewPager(final ViewPager viewPager) {
		this.viewPager = viewPager;
		updateNumOfViews();
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int state) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffest,
					int positionOffestPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				updateNumOfViews();
				setPosition(position);
			}
		});
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setStrokeWidth(1);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);

		for (int i = 0; i < numOfViews; i++) {
			float cx = (getWidth() - (numOfViews - 1) * DISTANCE) / 2 + i
					* DISTANCE;
			float cy = getHeight() / 2.0f;
			if (position == i) {
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
			} else {
				paint.setStyle(Paint.Style.STROKE);
			}
			canvas.drawCircle(cx, cy, RADIUS, paint);
		}
	}

	private void updateNumOfViews() {
		if (viewPager.getAdapter() == null) {
			numOfViews = 0;
		} else {
			numOfViews = viewPager.getAdapter().getCount();
		}
	}
}
