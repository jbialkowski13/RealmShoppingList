package pl.whiter.realmio.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.ShoppingItem;
import pl.whiter.realmio.model.ShoppingList;
import pl.whiter.realmio.ui.adapter.ShoppingItemAdapter;

/**
 * Created by whiter
 */
public class ShoppingDetailsActivity extends AppCompatActivity {

    private static final String TAG = ShoppingDetailsActivity.class.getSimpleName();

    private static final String SHOPPING_LIST_ID_EXTRA = "id_extra";
    private ShoppingList shoppingList;

    public static void start(Context context, ShoppingList shoppingList) {
        Intent intent = new Intent(context, ShoppingDetailsActivity.class);
        intent.putExtra(SHOPPING_LIST_ID_EXTRA, shoppingList.getId());
        context.startActivity(intent);
    }

    @Bind(R.id.item_list)
    RecyclerView itemList;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Realm realm;

    private ShoppingItemAdapter shoppingItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_details_activity);
        ButterKnife.bind(this);
        String itemUUID = getIntent().getExtras().getString(SHOPPING_LIST_ID_EXTRA);
        realm = Realm.getDefaultInstance();
        realm.addChangeListener(realmChangeListener);
        shoppingList = realm.where(ShoppingList.class).equalTo("id", itemUUID).findFirst();
        toolbar.setTitle(shoppingList.getDetails().getName());
        setSupportActionBar(toolbar);
        shoppingItemAdapter = new ShoppingItemAdapter(this, shoppingList.getItems());
        itemList.setLayoutManager(new GridLayoutManager(this, 1));
        itemList.addOnItemTouchListener(new RecyclerItemClickListener(this, onItemClickListener));
        itemList.setAdapter(shoppingItemAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.fab)
    void onAddClicked() {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_item)
                .setView(editText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addItem(editText.getText().toString());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.show();
    }

    private void onItemClicked(final ShoppingItem shoppingItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_item)
                .setMessage(getString(R.string.delete_item_message, shoppingItem.getName()))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(shoppingItem);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            onItemClicked(shoppingItemAdapter.getItem(position));
        }
    };

    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            shoppingItemAdapter.notifyDataSetChanged();
        }
    };

    private void addItem(String item) {
        realm.beginTransaction();
        ShoppingItem shoppingItem = new ShoppingItem(item, UUID.randomUUID().toString());
        ShoppingItem addedItem = realm.copyToRealm(shoppingItem);
        shoppingList.getItems().add(addedItem);
        realm.commitTransaction();
    }

    private void removeItem(final ShoppingItem shoppingItem) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                shoppingItem.removeFromRealm();
            }
        });
    }

    private void deleteList() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                shoppingList.getDetails().removeFromRealm();
                RealmList<ShoppingItem> items = shoppingList.getItems();
                for (int i = 0; i < items.size(); i++) {
                    items.get(i).removeFromRealm();
                }
                shoppingList.removeFromRealm();
                finish();
            }
        });
    }
}
