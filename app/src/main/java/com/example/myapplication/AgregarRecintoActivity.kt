package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R


class AgregarRecintoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseOpenHelper
    private lateinit var edtNombreRecinto: EditText
    private lateinit var edtCapacidad: EditText
    private lateinit var edtDireccion: EditText
    private lateinit var btnGuardarRecinto: Button
    private lateinit var listViewRecintos: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val recintosList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_recinto)

        dbHelper = DatabaseOpenHelper(this)

        edtNombreRecinto = findViewById(R.id.editText_nombre_recinto)
        edtCapacidad = findViewById(R.id.editText_capacidad)
        edtDireccion = findViewById(R.id.editText_direccion)
        btnGuardarRecinto = findViewById(R.id.btn_guardar_recinto)
        listViewRecintos = findViewById(R.id.mostrar_recintos)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recintosList)
        listViewRecintos.adapter = adapter

        cargarRecintos()

        btnGuardarRecinto.setOnClickListener {
            val nombre = edtNombreRecinto.text.toString()
            val capacidadStr = edtCapacidad.text.toString()
            val direccion = edtDireccion.text.toString()

            if (nombre.isBlank() || capacidadStr.isBlank() || direccion.isBlank()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val capacidad = capacidadStr.toIntOrNull()
            if (capacidad == null || capacidad <= 0) {
                Toast.makeText(this, "Capacidad debe ser un número positivo.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            guardarRecinto(nombre, capacidad, direccion)
        }
    }

    private fun cargarRecintos() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, nombre FROM Recinto", null)

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(1)
                recintosList.add(nombre)
            } while (cursor.moveToNext())
        }

        cursor.close()
        adapter.notifyDataSetChanged()
    }

    private fun guardarRecinto(nombre: String, capacidad: Int, direccion: String) {
        val db = dbHelper.writableDatabase
        db.execSQL("INSERT INTO Recinto (nombre, capacidad, direccion) VALUES ('$nombre', $capacidad, '$direccion')")
        Toast.makeText(this, "Recinto agregado exitosamente.", Toast.LENGTH_SHORT).show()
        recintosList.add(nombre) // Agrega el nuevo recinto a la lista
        adapter.notifyDataSetChanged() // Actualiza el ListView
        limpiarCampos() // Limpia los campos de entrada
    }

    private fun limpiarCampos() {
        edtNombreRecinto.text.clear()
        edtCapacidad.text.clear()
        edtDireccion.text.clear()
    }
}
