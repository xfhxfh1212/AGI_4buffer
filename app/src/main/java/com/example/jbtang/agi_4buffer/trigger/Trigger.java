package com.example.jbtang.agi_4buffer.trigger;

import android.app.Activity;

import com.example.jbtang.agi_4buffer.core.Status;

/**
 * Created by jbtang on 12/6/2015.
 */
public interface Trigger {

    void start(Activity activity, Status.Service service);

    void stop();
}
