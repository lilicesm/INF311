package alicemendonca.android.calculadora_imc_inf311;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText txtNome, txtIdade, txtPeso, txtAltura;

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

        txtNome = findViewById(R.id.txtNome);
        txtIdade = findViewById(R.id.txtIdade);
        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);

        Log.d("Ciclo", getLocalClassName() + ": onCreate()");
    }

    public void clickRelatorio(View view) {
        Intent intent = new Intent(this, TelaResultadosActivity.class);

        intent.putExtra("NOME", txtNome.getText().toString());
        intent.putExtra("IDADE", txtIdade.getText().toString());
        intent.putExtra("PESO", txtPeso.getText().toString());
        intent.putExtra("ALTURA", txtAltura.getText().toString());

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Ciclo", getLocalClassName() + ": onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Ciclo", getLocalClassName() + ": onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Ciclo", getLocalClassName() + ": onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Ciclo", getLocalClassName() + ": onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Ciclo", getLocalClassName() + ": onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Ciclo", getLocalClassName() + ": onRestart()");
    }
}