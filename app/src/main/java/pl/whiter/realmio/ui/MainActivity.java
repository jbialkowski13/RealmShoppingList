package pl.whiter.realmio.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.JsonProvider;
import pl.whiter.realmio.model.ShoppingList;
import pl.whiter.realmio.ui.adapter.ShoppingListAdapter;

/**
 * Created by whiter
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Bind(R.id.shopping_list)
    RecyclerView shoppingList;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Realm realm;
    private ShoppingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        realm.addChangeListener(realmChangeListener);
        setSupportActionBar(toolbar);
        adapter = new ShoppingListAdapter(this, realm);
        shoppingList.setLayoutManager(new GridLayoutManager(this, 1));
        shoppingList.addOnItemTouchListener(new RecyclerItemClickListener(this, onItemClickListener));
        shoppingList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_import:
                importList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    void onAddClick() {
        CreateShoppingListActivity.start(this);
    }

    private void importList() {
        realm.beginTransaction();
        realm.createObjectFromJson(ShoppingList.class, JsonProvider.getJson());
        realm.commitTransaction();
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            ShoppingDetailsActivity.start(MainActivity.this, adapter.getItem(position));
        }
    };

    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            adapter.notifyDataSetChanged();
        }
    };
}
