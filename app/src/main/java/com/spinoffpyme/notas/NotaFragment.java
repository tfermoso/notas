package com.spinoffpyme.notas;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotaFragment extends Fragment {
    InterfaceDatos myListener;
    TextView titulo;
    TextView descripcion;
    public NotaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_nota, container, false);
        titulo=(TextView) view.findViewById(R.id.txtTitulo);
        descripcion=(TextView) view.findViewById(R.id.txtDescripcion);
        titulo.setText(getArguments().getString("titulo"));
        descripcion.setText(getArguments().getString("descripcion"));

        Button btnEditar=(Button) view.findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onComunicateEditar(getArguments().getInt("idnota"),titulo.getText().toString(),descripcion.getText().toString());
            }
        });
        Button btnBorrar=(Button) view.findViewById(R.id.btnBorrar);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onComunicateBorrar(getArguments().getInt("idnota"));
            }
        });

        return view;
    }

    //Interfaz de comunicaciones con la actividad
    public interface InterfaceDatos{
        public void onComunicateEditar(int idnota, String val1,String val2);
        public void onComunicateBorrar(int idnota);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            myListener = (InterfaceDatos) context; //Nos aseguramos que la actividad ha implementado la interfaz del fragment
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" No se puede comunicar con la actividad");
        }
    }
}
