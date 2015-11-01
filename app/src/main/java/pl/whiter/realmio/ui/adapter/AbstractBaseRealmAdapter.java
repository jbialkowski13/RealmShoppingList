package pl.whiter.realmio.ui.adapter;

import android.support.v7.widget.RecyclerView;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by whiter
 */
public abstract class AbstractBaseRealmAdapter<T extends RealmObject, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected RealmList<T> realmList;

    public AbstractBaseRealmAdapter(RealmList<T> realmList) {
        this.realmList = realmList;
    }

    public T getItem(int position) {
        return realmList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return realmList.isValid() ? realmList.size() : 0;
    }
}
