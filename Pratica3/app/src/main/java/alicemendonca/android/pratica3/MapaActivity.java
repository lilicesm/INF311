package alicemendonca.android.pratica3;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    // Coordenadas (Exemplos, substitua pelas reais)
    private final LatLng CASA_NATAL = new LatLng(-5.839671961673279, -35.20169096017838);
    private final LatLng CASA_VICOSA = new LatLng(-20.752796239159856, -42.88269714783487);
    private final LatLng DPTO = new LatLng(-20.764917910192104, -42.86840550823318);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.btnCasa).setOnClickListener(v -> centralizar(CASA_NATAL));
        findViewById(R.id.btnVicosa).setOnClickListener(v -> centralizar(CASA_VICOSA));
        findViewById(R.id.btnDpto).setOnClickListener(v -> centralizar(DPTO));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int local = getIntent().getIntExtra("LOCAL_SELECIONADO", 0);

        if (local == 0) centralizar(CASA_NATAL);
        else if (local == 1) centralizar(CASA_VICOSA);
        else if (local == 2) centralizar(DPTO);
    }

    private void centralizar(LatLng local) {
        mMap.clear(); // limpa marcadores anteriores
        mMap.addMarker(new MarkerOptions().position(local).title("Local Selecionado"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 15f));
    }
}