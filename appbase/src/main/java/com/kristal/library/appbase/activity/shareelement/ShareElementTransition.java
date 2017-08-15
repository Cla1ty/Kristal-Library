package com.kristal.library.appbase.activity.shareelement;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * Created by Kristal on 6/13/2017.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ShareElementTransition
		extends TransitionSet
{
	{
		setOrdering(ORDERING_TOGETHER);
		addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).
				addTransition(new ChangeImageTransform());
	}
	
	public ShareElementTransition() { }
	
	public ShareElementTransition(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
}