package pl.whiter.realmio.ui.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by whiter
 */
public abstract class AbstractRealmAdapter<T extends RealmObject, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected RealmResults<T> results;

    public AbstractRealmAdapter(Realm realm) {
        results = loadData(realm, null);
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        return results.get(position);
    }

    @Override
    public final int getItemCount() {
        return results.size();
    }

    protected abstract RealmResults<T> loadData(Realm realm, @Nullable String query);
}
