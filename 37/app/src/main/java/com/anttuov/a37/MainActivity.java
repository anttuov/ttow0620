package com.anttuov.a37;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int notification_id = 1;
    private int selectedIcon = R.drawable.ic_warning_black_24dp;
    private String icon_str = "Warning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.warning_n:
                Toast.makeText(getBaseContext(), "You selected warning icon", Toast.LENGTH_SHORT).show();
                selectedIcon = R.drawable.ic_warning_black_24dp;
                icon_str = "Warning";
                return true;
            case R.id.thumbs_n:
                Toast.makeText(getBaseContext(), "You selected thumbs up icon", Toast.LENGTH_SHORT).show();
                selectedIcon = R.drawable.ic_thumb_up_black_24dp;
                icon_str = "Thumbs up";
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendNotification(View view) {
        Toast.makeText(getBaseContext(), "Sent notification", Toast.LENGTH_SHORT).show();
        Notification notification  = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle(icon_str+" notification")
                .setSmallIcon(selectedIcon)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setContentText("This is a "+icon_str+" notification!")
                .setAutoCancel(true).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification_id++;
        notificationManager.notify(notification_id, notification);

    }
}
