package br.com.etechoracio.atividade;

import android.nfc.Tag;
import android.nfc.TagLostException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CustomDialog.ItemListener,AdapterView.OnItemLongClickListener,PopupMenu.OnMenuItemClickListener{

    private boolean insertMode;
    private ItemAdapter adapter;
    private ListView listView;

    private String selectedItemName;
    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ItemAdapter(this);

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.idmenu:
                CustomDialog menud =new CustomDialog(this);
                menud.show(getFragmentManager(),"Dialogo");
                insertMode = true;
                return true;

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItem(String name) {
        if (insertMode) {
            adapter.insertItem(name);
        } else {
            adapter.updateItem(selectedItem, name);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        PopupMenu popup = new PopupMenu(this,view);
        popup.inflate(R.menu.popup);
        popup.setOnMenuItemClickListener(this);

        popup.show();

        selectedItem = i;

        selectedItemName=adapter.getItem(i).toString();
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

     switch (menuItem.getItemId())
     {
         case R.id.excluir:
            adapter.removeItem(selectedItem);
            insertMode = false;

            return true;

         case R.id.editar:
             CustomDialog dialog = new CustomDialog(this);
             dialog.setItem(selectedItemName);
             dialog.show(getFragmentManager(),"editar");

             insertMode = false;

             return true;

         default:
             return super.onOptionsItemSelected(menuItem);

     }

    }
}
