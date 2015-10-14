package org.androidpn.mydevice.observer;

/**
 * Created by Saber on 2015/10/14.
 */
public interface Watched {
    public void addWatcher(Watcher watcher);

    public void removeWatcher(Watcher watcher);

    public void notifyWatchers(String str);
}
