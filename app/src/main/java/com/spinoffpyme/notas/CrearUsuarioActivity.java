package com.spinoffpyme.notas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.spinoffpyme.notas.DBHELPER.SQLite;

public class CrearUsuarioActivity extends AppCompatActivity {

    TextView txtNombre;
    TextView txtEmail;
    TextView txtPassword;
    TextView txtRePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        txtRePassword = (TextView) findViewById(R.id.txtRePassword);
    }

    public void guardarUsuario(View view) {
        if (txtNombre.getText().toString().isEmpty() || txtEmail.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty() || txtRePassword.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.error_campos_sin_cubrir, Toast.LENGTH_SHORT).show();
        } else if (!txtPassword.getText().toString().equals(txtRePassword.getText().toString())) {
            Toast.makeText(this, R.string.error_pass_no_iguales, Toast.LENGTH_SHORT).show();
        } else {
            String nombre = txtNombre.getText().toString();
            String email = txtEmail.getText().toString();
            String pass = txtPassword.getText().toString();

            //Creamos el usuario
            SQLite db = new SQLite(this,"usuarios", null,1);
            SQLite db2=new SQLite(this,"notas",null,1);
            ContentValues nota=new ContentValues();
            nota.put("titulo","titulo1");
            nota.put("mensaje","mensaje1");
            nota.put("idusuario",2);

            db2.getWritableDatabase().insert("notas",null,nota);
            if (db != null) {
                SQLiteDatabase data = db.getWritableDatabase();
                ContentValues con = new ContentValues();
                con.put("nombre", nombre);
                con.put("email", email);
                con.put("password", pass);
                long resultado = data.insert("usuarios", null, con);
                if (resultado > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("email",email);
                    intent.putExtra("password",pass);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);
                    finish();

                }

            }
        }
    }
}
