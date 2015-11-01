package pl.whiter.realmio;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import pl.whiter.realmio.model.Migration;

/**
 * Created by whiter
 */
public class RealmApp extends Application {

    private static RealmConfiguration configuration;

    @Override
    public void onCreate() {
        super.onCreate();

        configuration = new RealmConfiguration.Builder(this)
                .schemaVersion(1)
                .migration(new Migration())
                .build();


    }

    public static Realm getRealm() {
        return Realm.getInstance(configuration);
    }
}
