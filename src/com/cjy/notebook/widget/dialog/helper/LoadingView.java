package com.cjy.notebook.widget.dialog.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class LoadingView extends ImageView {
	Animation rotateAnimation;

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LoadingView(Context context) {
		super(context);
	}

	public void initAmin() {
		rotateAnimation = new RotateAnimation(0f, 359f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		LinearInterpolator lin = new LinearInterpolator();
		rotateAnimation.setInterpolator(lin);
		rotateAnimation.setRepeatCount(-1);
		setAnimation(rotateAnimation);
		setAnimation(rotateAnimation);
	}

	public void startAnimation() {
		// TODO Auto-generated method stub
		rotateAnimation.setDuration(1000);
		rotateAnimation.startNow();
	}

	public void EndAnimation() {
		rotateAnimation.cancel();
	}
}
