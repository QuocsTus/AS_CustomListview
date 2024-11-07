package com.example.customlistview;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Contact> contactlist;
    private Adapter adapterlist;
    private EditText etSearch;
    private ListView lstCotact;
    private Button btnAdd;
    private int SelectedItemId;
    private MyDB db;
    private static final int REQUEST_CALL_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.etSearch);
        lstCotact = findViewById(R.id.lstContact);
        btnAdd = findViewById(R.id.btnAdd);

        contactlist = new ArrayList<>();
        db = new MyDB(this, "ContactDB", null, 1);
//        db.addContact(new Contact(1, "img1", "nvn", "0367258479"));
//        db.addContact(new Contact(2, "img2", "nqt", "0367258478"));
//        db.addContact(new Contact(3, "img3", "nvc", "0367258477"));
        contactlist = db.getAllContact();
//        contactlist.add(new Contact(1, "img1", "nvn", "0367258479"));
//        contactlist.add(new Contact(2, "img3", "nvn", "0367258478"));
//        contactlist.add(new Contact(3, "img3", "nvn", "0367258477"));

        adapterlist = new Adapter(contactlist, this);
        lstCotact.setAdapter(adapterlist);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        registerForContextMenu(lstCotact);
        lstCotact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedItemId = position;
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null) {
            Bundle b = data.getExtras();
            int id = b.getInt("Id", -1);  // -1 là giá trị mặc định nếu không có Id
            String name = b.getString("Name", "");
            String phone = b.getString("Phone", "");

            Contact newContact = new Contact(id, "Image", name, phone);
            Contact updatedContact = new Contact(id, "Image", name, phone);

            if (requestCode == 100 && resultCode == 150) {
                db.addContact(newContact);
                contactlist.clear();
                contactlist.addAll(db.getAllContact());
                adapterlist.notifyDataSetChanged();
            } else if (requestCode == 200 && resultCode == 150) {
                db.updateContact(SelectedItemId, updatedContact);
                contactlist.set(SelectedItemId, updatedContact);
                adapterlist.notifyDataSetChanged();
            } else if (resultCode == 100) {
                Toast.makeText(this, "Action was cancelled.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (resultCode == 100) {
                Toast.makeText(this, "No data received or action was cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.actionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Contact c = contactlist.get(SelectedItemId);
        if (item.getItemId() == R.id.mnuEdit) {
            Intent intent = new Intent(MainActivity.this, SubActivity.class);

            // Passing the selected contact data to SubActivity for editing
            Bundle b = new Bundle();
            b.putInt("Id", c.getId());
            b.putString("Image", c.getImages());
            b.putString("Name", c.getName());
            b.putString("Phone", c.getPhone());
            intent.putExtras(b);

            // Start SubActivity for result with request code 200
            startActivityForResult(intent, 200);
        }else if(item.getItemId()==R.id.mnuDelete){
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn chắc chắn muốn xóa dữ liệu này chứ?")
                    .setPositiveButton("Đồng ý", (dialog, which) -> {
                        // Delete contact from database
                        db.deleteContact(c.getId());

                        // Remove contact from the list
                        contactlist.remove(SelectedItemId);

                        // Notify the adapter of the data change
                        adapterlist.notifyDataSetChanged();
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .show();
        }else if(item.getItemId() == R.id.mnuCall){
            String phoneNumber = c.getPhone();
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                try {
                    startActivity(callIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "No application found to handle call", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Phone number is invalid", Toast.LENGTH_SHORT).show();
            }
        }else if(item.getItemId() == R.id.mnuSms) {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + c.getPhone()));
            startActivity(smsIntent);
        }
        return super.onContextItemSelected(item);
    }


}