package mo.ed.prof.yusor.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import mo.ed.prof.yusor.R;

import static mo.ed.prof.yusor.Adapter.BooksGalleryAdapter.BookOwnerID_KEY;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        String BookOwnerID = intent.getExtras().getString(BookOwnerID_KEY);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.chat_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Get the ID of the clicked item */
        int id = item.getItemId();
        /* Settings menu item clicked */
        if (id == R.id.call_icon) {
            //implement call function
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
