package pl.whiter.realmio.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import pl.whiter.realmio.R;
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
        handleIntent(getIntent());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.search(realm, null);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    adapter.search(realm, newText);
                }
                return false;
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    @OnClick(R.id.fab)
    void onAddClick() {
        CreateShoppingListActivity.start(this);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            adapter.search(realm, query);
        }
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
