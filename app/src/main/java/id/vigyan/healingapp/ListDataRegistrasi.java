package id.vigyan.healingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ListDataRegistrasi extends AppCompatActivity {
    private RecyclerView recyclerviewregis;
    private DataAdapter dataAdapter;
    private DBmain database;
    private Button btn_plus;
    private LinearLayout linearEditData, linearHapusData;
    private Button btn_batalPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_registrasi);

        database = new DBmain(this);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFormAdd = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentFormAdd);
            }
        });

        recyclerviewregis = findViewById(R.id.recyclerviewregis);

        recyclerviewregis.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new DataAdapter(this,database.getAllData());
        recyclerviewregis.setAdapter(dataAdapter);

        dataAdapter.setOnClickListenerData(new DataAdapter.OnClickListenerData() {
            @Override
            public void OnItemClickData(long id) {
                LayoutInflater inflater = LayoutInflater.from(ListDataRegistrasi.this);
                View view = inflater.inflate(R.layout.popup_data,null);
                linearEditData = view.findViewById(R.id.linearEditData);
                linearHapusData = view.findViewById(R.id.linearHapusData);
                btn_batalPopup = view.findViewById(R.id.btn_batalPopup);
                Dialog popupData = new Dialog(ListDataRegistrasi.this);
                popupData.setContentView(view);
                popupData.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupData.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        linearEditData.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent editData = new Intent(ListDataRegistrasi.this, MainActivity.class);
                                editData.putExtra(DBmain.row_id, id);
                                startActivity(editData);
                                popupData.dismiss();
                            }
                        });

                        linearHapusData.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ListDataRegistrasi.this);
                                builder.setTitle("Konfirmasi");
                                builder.setMessage("Apakah Anda yakin ingin menghapus data ?");
                                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        database.deleteData(id);
                                        popupData.dismiss();
                                        dataAdapter.swapCursor(database.getAllData());
                                    }
                                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        popupData.dismiss();
                                    }
                                });
                                AlertDialog popupKonfirmasi = builder.create();
                                popupKonfirmasi.show();
                            }
                        });

                        btn_batalPopup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupData.dismiss();
                            }
                        });
                    }
                });
                popupData.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataAdapter.swapCursor(database.getAllData());
    }


}