package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R


class AgregarConciertoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseOpenHelper
    private lateinit var recintosSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_concierto)

        dbHelper = DatabaseOpenHelper(this)
        recintosSpinner = findViewById(R.id.recintos)
        val edtNombreArtista = findViewById<EditText>(R.id.editText_nombre_artista)
        val edtFecha = findViewById<EditText>(R.id.editText_fecha)
        val btnGuardar = findViewById<Button>(R.id.btn_guardar_concierto)

        cargarRecintos()

        btnGuardar.setOnClickListener {
            val nombreArtista = edtNombreArtista.text.toString()
            val fecha = edtFecha.text.toString()
            val recintoSeleccionado = recintosSpinner.selectedItemPosition

            if (nombreArtista.isBlank() || fecha.isBlank() || recintoSeleccionado == 0) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            guardarConcierto(nombreArtista, fecha, recintoSeleccionado)
        }
    }

    private fun cargarRecintos() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, nombre FROM Recinto", null)
        val recintos = mutableListOf<String>()

        recintos.add("Seleccione un recinto") // Opción por defecto

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(1)
                recintos.add(nombre)
            } while (cursor.moveToNext())
        }

        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, recintos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        recintosSpinner.adapter = adapter
    }

    private fun guardarConcierto(nombreArtista: String, fecha: String, recintoId: Int) {
        val db = dbHelper.writableDatabase
        val idRecinto = recintosSpinner.selectedItemId.toInt() // Obtén el ID del recinto seleccionado

        db.execSQL("INSERT INTO Concierto (recinto_id, artista, fecha_concierto) VALUES ($idRecinto, '$nombreArtista', '$fecha')")
        Toast.makeText(this, "Concierto agregado exitosamente.", Toast.LENGTH_SHORT).show()
        finish() // Cierra la actividad
    }
}
