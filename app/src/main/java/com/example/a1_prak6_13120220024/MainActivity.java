package com.example.a1_prak6_13120220024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private EditText txtSTb, txtNama, txtAngkatan;
    private RestHelper restHelper;
    private Mahasiswa mhs;
    private Intent intentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        restHelper = new RestHelper(this);
        intentEdit = null;

        txtSTb = findViewById(R.id.txt_stb);
        txtNama = findViewById(R.id.txt_nama);
        txtAngkatan = findViewById(R.id.txt_angkatan);
    }

    private void clearData (){
        txtSTb.setText("");
        txtNama.setText("");
        txtAngkatan.setText("");
        intentEdit = null;
        txtSTb.requestFocus();
    }

    public void btnSimpanClick (View view) {
        mhs = new Mahasiswa(
                txtSTb.getText().toString(),
                txtNama.getText().toString(),
                Integer.parseInt(txtAngkatan.getText().toString())
        );
        try {
            if (intentEdit == null)
                restHelper.insertData(mhs.toJSON());
            else
                restHelper.editData(intentEdit.getStringExtra("stb"), new Mahasiswa(
                        txtSTb.getText().toString(),
                        txtNama.getText().toString(),
                        Integer.parseInt(txtAngkatan.getText().toString())
                ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        clearData();
    }

    public void btnTampilDataClick(View view) {
        intentEdit = null;
        Intent intent = new Intent(this, TampilDataActivity.class);
        startActivityForResult (intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            intentEdit = data;
            txtSTb.setText(data.getStringExtra("stb"));
            txtNama.setText(data.getStringExtra("nama"));
            txtAngkatan.setText(data.getStringExtra("angkatan"));
        }
    }
}