package pl.whiter.realmio.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.ShoppingList;

/**
 * Created by whiter
 */
public class ShoppingListAdapter extends AbstractRealmAdapter<ShoppingList, ShoppingListViewHolder> {

    private LayoutInflater inflater;

    public ShoppingListAdapter(Context context, Realm realm) {
        super(realm);
        this.inflater = LayoutInflater.from(context);
    }

    public void search(Realm realm, String query) {
        results = loadData(realm, query);
        notifyDataSetChanged();
    }

    @Override
    protected RealmResults<ShoppingList> loadData(Realm realm, @Nullable String query) {
        RealmQuery<ShoppingList> realmQuery = realm.where(ShoppingList.class);
        if (!TextUtils.isEmpty(query)) {
            realmQuery.contains("details.name", query)
                    .or()
                    .contains("items.name", query);
        }
        return realmQuery.findAll();
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        holder.attachShoppingList(getItem(position));
    }
}
