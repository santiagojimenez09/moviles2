package com.example.paseo_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioButton jrblujo,jrbnormal,jrbeconomica;
    EditText jetcantidad,jetcodigo;
    TextView jtvtipo,jtvtransporte,jtvtotal;
    CheckBox jcbtransporte;
    Button jbtguardar,jbtconsultar,jbteliminar,jbtcancelar,jbtpasar;
    int sw=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        jrblujo=findViewById(R.id.rblujo);
        jrbnormal=findViewById(R.id.rbnormal);
        jrbeconomica=findViewById(R.id.rbeconomica);
        jetcantidad=findViewById(R.id.etcantidad);
        jtvtipo=findViewById(R.id.tvtipo);
        jtvtransporte=findViewById(R.id.tvtransporte);
        jtvtotal=findViewById(R.id.tvtotal);
        jcbtransporte=findViewById(R.id.cbtransporte);
        jbtguardar=findViewById(R.id.btguardar);
        jbtconsultar=findViewById(R.id.btconsultar);
        jbteliminar=findViewById(R.id.bteliminar);
        jbtcancelar=findViewById(R.id.btcancelar);
        jbtpasar=findViewById(R.id.btpasar);
        jetcodigo=findViewById(R.id.etcodigo);
    }

    private void Calcular_Total() {
        String cantidad;
        cantidad = jetcantidad.getText().toString();
        if (cantidad.isEmpty()) {
            Toast.makeText(this, "La cantidad de personas es requerida", Toast.LENGTH_LONG).show();
            jetcantidad.requestFocus();
        } else {
            int val_finca, val_transporte, cantidad_personas, val_total;
            if (jrblujo.isChecked())
                val_finca = 4000000;
            else if (jrbnormal.isChecked())
                val_finca = 2000000;
            else
                val_finca = 1500000;
            if (jcbtransporte.isChecked()) {
                cantidad_personas = Integer.parseInt(cantidad);
                val_transporte = cantidad_personas * 60000;
            } else
                val_transporte = 0;
            val_total = val_finca + val_transporte;

            //Mostrar datos
            jtvtipo.setText(String.valueOf(val_finca));
            jtvtransporte.setText(String.valueOf(val_transporte));
            jtvtotal.setText(String.valueOf(val_total));
        }
    }

    public void Guardar(View view) {
        Calcular_Total();
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "paseo2.db", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo, tipo, cantidad, transporte;
        codigo = jetcodigo.getText().toString();
        if(codigo.isEmpty()) {
            Toast.makeText(this, "Codigo es requerido", Toast.LENGTH_LONG).show();
            jetcodigo.requestFocus();
        }
        else {
        if (jrblujo.isChecked())
            tipo = "Lujo";
        else if (jrbnormal.isChecked())
            tipo = "Normal";
        else
            tipo = "Economica";
            cantidad = jetcantidad.getText().toString();
            if (jcbtransporte.isChecked())
                transporte = "Si";
            else
                transporte = "No";
            ContentValues dato = new ContentValues();
            dato.put("Codigo", codigo);
            dato.put("Tipo", tipo);
            dato.put("Cantidad", cantidad);
            dato.put("Transporte", transporte);
            long resp;
            if(sw == 0)
             resp = db.insert("TblFinca", null, dato);
            else {
                resp=db.update("TblFinca",dato,"codigo='" + codigo + "'",null);
                sw=0;
            }
            if (resp > 0) {
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_LONG).show();
                limpiar_campos();
            } else {
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_LONG).show();
            }

        }
        db.close();
    }

    public void Consultar(View view){
        String codigo;
        codigo=jetcodigo.getText().toString();
        if(codigo.isEmpty()){
            Toast.makeText(this,"Codigo requerido para consultar",Toast.LENGTH_LONG).show();
            jetcantidad.requestFocus();
        }
        else {
            AdminSqliteOpenHelper admin=new AdminSqliteOpenHelper(this,"paseo2.db",null,1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from Tblfinca where codigo='" + codigo + "'",null);
            if(fila.moveToFirst()){
                sw=1;
                if(fila.getString(1).equals("Lujo"))
                    jrblujo.setChecked(true);
                else
                if(fila.getString(1).equals("Normal"))
                    jrbnormal.setChecked(true);
                else
                    jrbeconomica.setChecked(true);
                jetcantidad.setText(fila.getString(2));
                if (fila.getString(3).equals("Si"))
                    jcbtransporte.setChecked(true);
                else
                    jcbtransporte.setChecked(false);
                Calcular_Total();
            }
            else{
                Toast.makeText(this,"Codigo no registrado",Toast.LENGTH_LONG).show();
                jetcodigo.requestFocus();
            }
            db.close();
        }

    }

    public void Eliminar(View view){
        String codigo;
        codigo=jetcodigo.getText().toString();
        if(codigo.isEmpty()){
            Toast.makeText(this,"Codigo requerido para eliminar",Toast.LENGTH_LONG).show();
            jetcodigo.requestFocus();
        }
        else {
            AdminSqliteOpenHelper admin=new AdminSqliteOpenHelper(this,"paseo2.db",null,1);
            SQLiteDatabase db=admin.getWritableDatabase();
            int resp=db.delete("TblFinca","codigo='" + codigo + "'",null);
            if(resp > 0){
                Toast.makeText(this,"Registro eliminado",Toast.LENGTH_LONG).show();
                limpiar_campos();
            }
            else{
                Toast.makeText(this,"Error eliminando registro",Toast.LENGTH_LONG).show();
                jetcodigo.requestFocus();
            }db.close();
        }
    }

    private void limpiar_campos(){
        jetcodigo.setText("");
        jrbnormal.setChecked(true);
        jtvtipo.setText("2000000");
        jetcantidad.setText("");
        jcbtransporte.setChecked(false);
        jtvtransporte.setText("0");
        jtvtotal.setText("0");
        jetcodigo.requestFocus();
    }

    public void Cancelar(View view){
        limpiar_campos();
    }

    public void Calcular(View view){
           Calcular_Total();
    }

    public void Pasar(View view){
        String nombre="Santiago JC";
        Intent intvehiculo=new Intent(this,AutomovilActivity.class);
        intvehiculo.putExtra("Dato",nombre);
        startActivity(intvehiculo);
    }
}
