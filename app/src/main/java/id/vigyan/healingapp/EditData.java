package id.vigyan.healingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditData extends AppCompatActivity {
    private EditText NIK, Nama;
    private RadioGroup listOpsiJK;
    private RadioButton radioButton;
    private CheckBox checkBoxHalu, checkBoxStress, checkBoxMakan, checkBoxTidur;
    private SeekBar seekBarCemas;
    private TextView cemas, AlNIK, AlNama, AlJK, AlKeluhan, AlPresentase;
    private Button btnReset, btnSubmit;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    String nik,nama,jk,keluhan;
    int persentase;
    DBmain dBmain;
    private long id;
    private Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        intent2 = getIntent();
        id = intent2.getLongExtra(DBmain.row_id,0);

        if(intent2.hasExtra(DBmain.row_id)){
            setTitle("Edit Biodata");
        }else{

        }
        NIK = (EditText) findViewById(R.id.NIK);
        Nama = (EditText) findViewById(R.id.Nama);
        checkBoxHalu = (CheckBox) findViewById(R.id.checkBoxHalu);
        checkBoxStress = (CheckBox) findViewById(R.id.checkBoxStress);
        checkBoxMakan = (CheckBox) findViewById(R.id.checkBoxMakan);
        checkBoxTidur = (CheckBox) findViewById(R.id.checkBoxTidur);
        listOpsiJK = findViewById(R.id.opsiJK);
        btnReset= (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(clickListener);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(clickListener);
        dBmain = new DBmain(this);
        seekBarCemas = (SeekBar) findViewById(R.id.seekBarCemas);
        cemas = (TextView) findViewById(R.id.cemas);
        cemas.setText(seekBarCemas.getProgress() + "/" + seekBarCemas.getMax());
        seekBarCemas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                cemas.setText(pval + "/" + seekBar.getMax());
                persentase = seekBarCemas.getProgress();
            }
        });
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSubmit:
                    nik = NIK.getText().toString();
                    nama = Nama.getText().toString();
                    if (!checkBoxHalu.isChecked() && !checkBoxStress.isChecked() && !checkBoxMakan.isChecked() && !checkBoxTidur.isChecked()){
                        Toast.makeText(getApplicationContext(), "Tidak ada keluhan yang dipilih", Toast.LENGTH_SHORT).show();
                    } else if (nik.matches("")||nama.matches("")){
                        Toast.makeText(getApplicationContext(), "Kolom NIK dan Nama Lengkap Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    } else if(listOpsiJK.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getApplicationContext(), "Jenis Kelamin Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        int selectedId = listOpsiJK.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        jk = radioButton.getText().toString();
                        keluhan = "";
                        if(checkBoxHalu.isChecked()){
                            if(keluhan == ""){
                                keluhan = "" + checkBoxHalu.getText();
                            }else{
                                keluhan = keluhan + "," + checkBoxHalu.getText();
                            }
                        }if(checkBoxStress.isChecked()){
                            if(keluhan == ""){
                                keluhan = "" + checkBoxStress.getText();
                            }else{
                                keluhan = keluhan + "," + checkBoxStress.getText();
                            }
                        }if(checkBoxMakan.isChecked()){
                            if(keluhan == ""){
                                keluhan = "" + checkBoxMakan.getText();
                            }else{
                                keluhan = keluhan + "," + checkBoxMakan.getText();
                            }
                        }if(checkBoxTidur.isChecked()){
                            if(keluhan == ""){
                                keluhan = "" + checkBoxTidur.getText();
                            }else{
                                keluhan = keluhan + "," + checkBoxTidur.getText();
                            }
                        }

                        DialogForm();
                    }
                    break;
                case R.id.btnReset:
                    NIK.setText(null);
                    Nama.setText(null);
                    listOpsiJK.clearCheck();
                    checkBoxHalu.setChecked(false);
                    checkBoxStress.setChecked(false);
                    checkBoxMakan.setChecked(false);
                    checkBoxTidur.setChecked(false);
                    seekBarCemas.setProgress(0);
                    cemas.setText(seekBarCemas.getProgress() + "/" + seekBarCemas.getMax());
                    break;
            }
        }
    };

    private void DialogForm(){
        dialog = new AlertDialog.Builder(EditData.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.alert_dialogs, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
//        dialog.setIcon(R.mipmap.ic_launcher);
//        dialog.setTitle("Berhasil");

        AlNIK = (TextView) dialogView.findViewById(R.id.AlNIK);
        AlNama = (TextView) dialogView.findViewById(R.id.AlNama);
        AlJK = (TextView) dialogView.findViewById(R.id.AlJK);
        AlKeluhan = (TextView) dialogView.findViewById(R.id.AlKeluhan);
        AlPresentase = (TextView) dialogView.findViewById(R.id.AlPresentase);

        AlNIK.setText("NIK\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " + nik);
        AlNama.setText("Nama\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " + nama);
        AlJK.setText("Jenis Kelamin\t\t\t\t\t\t\t\t: " + jk);
        AlKeluhan.setText("Keluhan\t\t\t\t\t\t\t\t\t\t\t\t\t:" + keluhan);
        AlPresentase.setText("Persentase Kecemasan : " + persentase +"%");

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ContentValues values =new ContentValues();
                values.put(DBmain.row_nik,nik);
                values.put(DBmain.row_nama,nama);
                values.put(DBmain.row_jk,jk);
                values.put(DBmain.row_keluhan,keluhan);
                values.put(DBmain.row_persentase,persentase);
                dBmain.insertData(values);

                Intent intent1 = new Intent(EditData.this, HasilForm.class);
                intent1.putExtra("NIK", nik);
                intent1.putExtra("NAMA", nama);
                intent1.putExtra("JK", jk);
                intent1.putExtra("KELUHAN", keluhan);
                intent1.putExtra("PERSEN", persentase);
                startActivity(intent1);
            }
        });

        dialog.show();

    }
}