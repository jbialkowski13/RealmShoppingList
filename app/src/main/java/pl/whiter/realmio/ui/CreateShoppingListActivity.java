package pl.whiter.realmio.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import pl.whiter.realmio.R;
import pl.whiter.realmio.model.ShoppingList;
import pl.whiter.realmio.model.ShoppingListDetails;

/**
 * Created by whiter
 */
public class CreateShoppingListActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, CreateShoppingListActivity.class);
        context.startActivity(intent);
    }

    @Bind(R.id.input_name)
    EditText nameEdit;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_shopping_list_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @OnClick(R.id.create)
    void onCreateClicked() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ShoppingListDetails shoppingListDetails = realm.createObject(ShoppingListDetails.class);
                shoppingListDetails.setId(UUID.randomUUID().toString());
                shoppingListDetails.setDate(new Date());
                shoppingListDetails.setName(nameEdit.getText().toString());

                ShoppingList shoppingList = realm.createObject(ShoppingList.class);
                shoppingList.setId(UUID.randomUUID().toString());
                shoppingList.setDetails(shoppingListDetails);
                finish();
            }
        });
    }


}
