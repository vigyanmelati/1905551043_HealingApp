package id.vigyan.healingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HasilForm extends AppCompatActivity {
    private TextView hasilForm;
    private Button btn_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_form);
        hasilForm = (TextView) findViewById(R.id.hasil);
        btn_other = (Button) findViewById(R.id.btn_other);

        btn_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ListDataRegistrasi.class);
                startActivity(intent1);
            }
        });

        Intent intent1 = getIntent();
        String nik = intent1.getStringExtra("NIK");
        String nama = intent1.getStringExtra("NAMA");
        String jk = intent1.getStringExtra("JK");
        String keluhan = intent1.getStringExtra("KELUHAN");
        int persen = intent1.getIntExtra("PERSEN",0);

        hasilForm.setText("NIK\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " +nik+"\nNama\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " +nama+"\nJenis Kelamin\t\t\t\t\t\t\t\t\t: " +jk+"\nKeluhan\t\t\t\t\t\t\t\t\t\t\t\t\t\t :"+keluhan+"\nPersentase Kecemasan :"+persen+"%");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Activity: onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity: onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Activity: onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Activity: onStop()", Toast.LENGTH_SHORT).show();
    }
}