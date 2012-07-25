package com.florianmski.tracktoid.trakt.tasks.get;

import android.app.Activity;

import com.florianmski.tracktoid.trakt.TraktManager;
import com.florianmski.tracktoid.trakt.tasks.BaseTask;
import com.florianmski.traktoid.TraktoidInterface;
import com.jakewharton.trakt.entities.ActivityItemBase;
import com.jakewharton.trakt.enumerations.ActivityAction;
import com.jakewharton.trakt.enumerations.ActivityType;

public class CheckinGetTask extends BaseTask<TraktoidInterface<?>>
{
	private CheckinListener listener;
	private ActivityItemBase checkin;
	private TraktoidInterface<?> traktItem;
	

	public CheckinGetTask(Activity context, CheckinListener listener) 
	{
		super(context);

		this.listener = listener;
	}

	@Override
	protected TraktoidInterface<?> doTraktStuffInBackground()
	{
		checkin = tm.userService().watching(TraktManager.getUsername()).fire();

		if(checkin != null && checkin.action == ActivityAction.Checkin)
		{
			if(checkin.type == ActivityType.Episode)
				traktItem = checkin.episode;
			else
				traktItem = checkin.movie;
			return traktItem;
		}
		
		return null;
	}
	
	public interface CheckinListener
	{
		public void onCheckin(TraktoidInterface<?> traktItem);
	}

	@Override
	protected void sendEvent(TraktoidInterface<?> result) 
	{
		if(context != null && listener != null)
			listener.onCheckin(result);
	}
}
