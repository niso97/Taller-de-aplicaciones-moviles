package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar los botones
        val btnAgregarRecinto = findViewById<Button>(R.id.btn_recinto)
        val btnAgregarConcierto = findViewById<Button>(R.id.btn_concierto)
        val btnAsignarConcierto = findViewById<Button>(R.id.btn_asignar)

        // Configurar listeners para los botones
        btnAgregarRecinto.setOnClickListener {
            val intent = Intent(this, AgregarRecintoActivity::class.java)
            startActivity(intent)
        }

        btnAgregarConcierto.setOnClickListener {
            val intent = Intent(this, AgregarConciertoActivity::class.java)
            startActivity(intent)
        }

        btnAsignarConcierto.setOnClickListener {
            val intent = Intent(this, VerConciertoActivity::class.java)
            startActivity(intent)
        }
    }
}
