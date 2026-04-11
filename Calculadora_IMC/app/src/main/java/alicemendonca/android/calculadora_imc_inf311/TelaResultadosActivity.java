package alicemendonca.android.calculadora_imc_inf311;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaResultadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tela_resultados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resultados), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvNome = findViewById(R.id.tvNomeResultado);
        TextView tvIdade = findViewById(R.id.tvIdadeResultado);
        TextView tvPeso = findViewById(R.id.tvPesoResultado);
        TextView tvAltura = findViewById(R.id.tvAlturaResultado);
        TextView tvIMC = findViewById(R.id.tvIMCResultado);
        TextView tvClassificacao = findViewById(R.id.tvClassificacaoResultado);

        String nome = getIntent().getStringExtra("NOME");
        String idade = getIntent().getStringExtra("IDADE");
        String pesoStr = getIntent().getStringExtra("PESO");
        String alturaStr = getIntent().getStringExtra("ALTURA");

        tvNome.setText(nome);
        tvIdade.setText(idade + " anos");
        tvPeso.setText(pesoStr + " kg");
        tvAltura.setText(alturaStr + " m");

        if (pesoStr != null && alturaStr != null && !pesoStr.isEmpty() && !alturaStr.isEmpty()) {
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            double imc = peso / (altura * altura);

            tvIMC.setText(String.format("%.2f", imc) + " kg/m²");

            String classificacao;
            if (imc < 18.5) {
                classificacao = "Abaixo do peso";
            } else if (imc < 25) {
                classificacao = "Saudável";
            } else if (imc < 30) {
                classificacao = "Sobrepeso";
            } else if (imc < 35){
                classificacao = "Obesidade Grau I";
            } else if (imc < 40){
                classificacao = "Obesidade Grau II (severa)";
            } else {
                classificacao = "Obesidade Grau III (mórbida)";
            }

            tvClassificacao.setText(classificacao);
        }
    }
}