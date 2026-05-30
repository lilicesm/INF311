package alicemendonca.android.pratica04_b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnDevolver;
    private float lumRecebida = -1;
    private float distRecebida = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDevolver = (Button) findViewById(R.id.btnDevolver);

        Intent itRecebida = getIntent();
        if (itRecebida != null) {
            lumRecebida = itRecebida.getFloatExtra("luminosidade", -1);
            distRecebida = itRecebida.getFloatExtra("distancia", -1);
        }

        btnDevolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultadoLum;
                String resultadoDist;

                if (lumRecebida < 20.0f) {
                    resultadoLum = "baixa";
                } else {
                    resultadoLum = "alta";
                }

                if (distRecebida > 3.0f) {
                    resultadoDist = "distante";
                } else {
                    resultadoDist = "perto";
                }

                Intent itResposta = new Intent();
                itResposta.putExtra("resultado_lum", resultadoLum);
                itResposta.putExtra("resultado_dist", resultadoDist);
                setResult(RESULT_OK, itResposta);
                finish();
            }
        });
    }
}