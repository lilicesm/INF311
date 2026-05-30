package alicemendonca.android.pratica04;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

import alicemendonca.LanternaHelper;
import alicemendonca.MotorHelper;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SwitchMaterial switchLanterna;
    private SwitchMaterial switchVibracao;
    private Button btnClassificar;

    private LanternaHelper lanternaHelper;
    private MotorHelper motorHelper;
    private SensorManager sensorManager;
    private Sensor sensorLuminosidade;
    private Sensor sensorDistancia;
    private float valorLum = -1;
    private float valorDist = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lanternaHelper = new LanternaHelper(this);
        motorHelper = new MotorHelper(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorLuminosidade = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorDistancia = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (sensorLuminosidade == null || sensorDistancia == null) {
            Toast.makeText(this, "Um ou mais sensores inexistentes.", Toast.LENGTH_LONG).show();
        }

        switchLanterna = (SwitchMaterial) findViewById(R.id.switchLanterna);
        switchVibracao = (SwitchMaterial) findViewById(R.id.switchVibracao);
        btnClassificar = (Button) findViewById(R.id.btnClassificar);

        // bloqueia os cliques nos switches
        switchLanterna.setEnabled(false);
        switchVibracao.setEnabled(false);

        btnClassificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("CLASSIFICAR_SENSORES");

                intent.putExtra("luminosidade", valorLum);
                intent.putExtra("distancia", valorDist);

                // envia intent pro appB
                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorLuminosidade != null) {
            sensorManager.registerListener(this, sensorLuminosidade, SensorManager.SENSOR_DELAY_GAME);
        }
        if (sensorDistancia != null) {
            sensorManager.registerListener(this, sensorDistancia, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // atualiza os valores quando o sensor muda
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            valorLum = event.values[0];
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            valorDist = event.values[0];
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // se a resposta do appB deu certo
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            String resultadoLum = data.getStringExtra("resultado_lum");
            String resultadoDist = data.getStringExtra("resultado_dist");

            if ("baixa".equalsIgnoreCase(resultadoLum)) {
                lanternaHelper.ligar();
                switchLanterna.setChecked(true);
            } else {
                lanternaHelper.desligar();
                switchLanterna.setChecked(false);
            }

            if ("distante".equalsIgnoreCase(resultadoDist)) {
                motorHelper.iniciarVibracao();
                switchVibracao.setChecked(true);
            } else {
                motorHelper.pararVibracao();
                switchVibracao.setChecked(false);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}