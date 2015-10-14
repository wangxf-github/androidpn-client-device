package org.androidpn.mydevice.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saber on 2015/10/14.
 */
public class ConcreteWatched implements Watched {

    // 存放观察者
    private List<Watcher> list = new ArrayList<Watcher>();

    @Override
    public void addWatcher(Watcher watcher) {
        list.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        list.remove(watcher);
    }

    @Override
    public void notifyWatchers(String str) {
        // 自动调用实际上是主题进行调用的
        for (Watcher watcher : list)
        {
            watcher.update(str);
        }
    }
}
