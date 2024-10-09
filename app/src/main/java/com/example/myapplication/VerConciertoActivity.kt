package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class VerConciertoActivity : AppCompatActivity() {
    private lateinit var textViewMostrar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView( R.layout.activity_ver_concierto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewMostrar = findViewById(R.id.textView_mostrar)
        val btnVolver = findViewById<Button>(R.id.btn_volver)

        // Cargar y mostrar los conciertos
        mostrarConciertos()

        // Configurar el botón volver
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Opcional: Cierra esta actividad
        }
    }

    private fun mostrarConciertos() {
        val dbHelper = DatabaseOpenHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT c.artista, c.fecha_concierto, r.nombre FROM Concierto c JOIN Recinto r ON c.recinto_id = r.id", null)

        val stringBuilder = StringBuilder()
        if (cursor.moveToFirst()) {
            do {
                val artista = cursor.getString(0)
                val fecha = cursor.getString(1)
                val recinto = cursor.getString(2)
                stringBuilder.append("Artista: $artista\nFecha: $fecha\nRecinto: $recinto\n\n")
            } while (cursor.moveToNext())
        } else {
            stringBuilder.append("No hay conciertos registrados.")
        }

        cursor.close()
        textViewMostrar.text = stringBuilder.toString()
    }
}
