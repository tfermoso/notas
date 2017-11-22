package com.spinoffpyme.notas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.spinoffpyme.notas.Adapters.CustomAdapter;
import com.spinoffpyme.notas.DBHELPER.SQLite;
import com.spinoffpyme.notas.Models.Nota;
import com.spinoffpyme.notas.Models.Usuario;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotaFragment.InterfaceDatos {


    private int posTarea;
    private Fragment fragmentAct;
    ArrayList<Nota> notas;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Guardamos los datos del usuario logeado
        usuario=new Usuario(getIntent().getIntExtra("idusuario",0),getIntent().getStringExtra("nombre"),getIntent().getStringExtra("email"));
        //Leemos las notas de este usuario
        notas=new ArrayList<Nota>();
        SQLiteDatabase db=new SQLite(MainActivity.this,"notas",null,1).getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM notas WHERE idusuario="+String.valueOf(usuario.getIdusuario()),null);
        Boolean resultado;
        while(c.moveToNext()){
            Nota nota=new Nota(c.getInt(c.getColumnIndex("idnota")),c.getString(c.getColumnIndex("titulo")),c.getString(c.getColumnIndex("descripcion")),c.getInt(c.getColumnIndex("idusuario")));
            notas.add(nota);

        }

        //notas.add(new Nota(1,"titulo1","descripción1",7));
        //notas.add(new Nota(2,"titulo1","descripción1",7));
        //notas.add(new Nota(3,"titulo1","descripción1",7));
        //notas.add(new Nota(5,"titulo5","descripción5",7));

        //Ponemos los datos en la lista
        ListView lv=(ListView)this.findViewById(R.id.lvtareas);


        CustomAdapter adapter=new CustomAdapter(notas,MainActivity.this,R.layout.detalle_tarea);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         
                posTarea=position; //la posición es el índice del array
                addDetalleTarea(posTarea);
            }
        });






    }

    private void addDetalleTarea(int posTarea) {
        Fragment fragmentOld=fragmentAct;
        //gestionamos el fragmento
        //paso1
        FragmentManager fm=getFragmentManager();
        //paso2
        FragmentTransaction transicion=fm.beginTransaction();
        //paso3
        Fragment fragment=new NotaFragment();
        fragmentAct=fragment; //nos quedamos para evitar la búsqueda para borrar
        //pasamos los parámetros
        Bundle data=new Bundle();
        Nota nota=notas.get(posTarea);
        data.putInt("posicion",posTarea);
        data.putInt("idnota",nota.getIdnota());
        data.putString("titulo",nota.getTitulo());
        data.putString("descripcion",nota.getDescripcion());

        fragment.setArguments(data);
        if(fragmentOld==null){ //si no hay fragmento se añade, sino se reemplaza
            transicion.add(R.id.lyDetalleTarea,fragment);
        }else{
            transicion.replace(R.id.lyDetalleTarea,fragment);
        }
        //paso4
        transicion.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.usuario,menu);

        menu.getItem(0).setTitle(usuario.getNombre());
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itemeditar:

                return true;
            case R.id.itemsalir:
                usuario=null;
                finish();;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onComunicateEditar(int idnota, String titulo, String descripcion) {
        for(int i=0;i<notas.size();i++){
            if(notas.get(i).getIdnota()==idnota){
                notas.get(i).setTitulo(titulo);
                notas.get(i).setDescripcion(descripcion);
                //Editamos el registro de la base de datos
                ContentValues valores=new ContentValues();
                valores.put("titulo",titulo);
                valores.put("descripcion",descripcion);
                SQLiteDatabase db=new SQLite(MainActivity.this,"notas",null,1).getWritableDatabase();
                db.update("notas",valores,"idnota="+idnota,null);
            }
        }
        actulizarListView();
        Toast.makeText(MainActivity.this,"Editar "+titulo+" "+descripcion+" "+idnota,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComunicateBorrar(int idnota) {
       for(int i=0;i<notas.size();i++){
           if(notas.get(i).getIdnota()==idnota){
               notas.remove(i);
               //Borramos de la base de datos
               SQLiteDatabase db=new SQLite(MainActivity.this,"notas",null,1).getWritableDatabase();
               db.delete("notas","idnota="+idnota,null);
           }
       }
        //Ponemos los datos en la lista
        ListView lv=(ListView)this.findViewById(R.id.lvtareas);


        CustomAdapter adapter=new CustomAdapter(notas,MainActivity.this,R.layout.detalle_tarea);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                posTarea=position; //la posición es el índice del array
                addDetalleTarea(posTarea);



            }
        });
        Toast.makeText(MainActivity.this,"Borrar"+idnota,Toast.LENGTH_SHORT).show();
    }

void actulizarListView(){
    //Ponemos los datos en la lista
    ListView lv=(ListView)this.findViewById(R.id.lvtareas);


    CustomAdapter adapter=new CustomAdapter(notas,MainActivity.this,R.layout.detalle_tarea);


    lv.setAdapter(adapter);
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            posTarea=position; //la posición es el índice del array
            addDetalleTarea(posTarea);



        }
    });
}

    public void nuevaTarea(View view) {
        TextView titulo=(TextView) findViewById(R.id.txtTitulo);
        TextView descripcion=(TextView) findViewById(R.id.txtDescripcion);


        //añadimos la nota a la BBDD
        //Creamos el usuario
        SQLite db = new SQLite(this,"notas", null,1);
        if (db != null) {
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues notabd=new ContentValues();
            notabd.put("titulo",titulo.getText().toString());
            notabd.put("descripcion",descripcion.getText().toString());
            notabd.put("idusuario",usuario.getIdusuario());

            long resultado = data.insert("notas", null, notabd);
            if (resultado > 0) {
                //Nota insertada en la bbdd
                Nota nota=new Nota((int)resultado,titulo.getText().toString(),descripcion.getText().toString(),usuario.getIdusuario());
                notas.add(nota);
                actulizarListView();
                titulo.setText("");
                descripcion.setText("");

            } else {
                //Error al insertar la nota en la bbdd
                Toast.makeText(MainActivity.this,"Error al insertar la nueva nota",Toast.LENGTH_SHORT).show();
            }

        }

    }
}

