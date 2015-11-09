package turismo.com.br.turismo.principal;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import turismo.com.br.turismo.R;
import turismo.com.br.turismo.maps.MapsActivity;
import turismo.com.br.turismo.preferencias.PreferenciasActivity;
import turismo.com.br.turismo.profile.ProfileActivity;
import turismo.com.br.turismo.utils.VarConUtils;

public class PrincipalActivity  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private MenuItem selectedMenuItem;
    private String mTitle;

    private DatePickerDialog dateDialog;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView editTextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datePickerDialog();

        setAdapterSpinner((Spinner) findViewById(R.id.spinnerHospedagemTipos));
        setAdapterSpinner((Spinner) findViewById(R.id.spinnerAlimentacaoTipos));
        setAdapterSpinner((Spinner) findViewById(R.id.spinnerEventosTipos));

        ImageButton mAccountImageButton = (ImageButton) findViewById(R.id.imageButton);
        mAccountImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, ProfileActivity.class);
                startActivityForResult(intent, VarConUtils.PREFERENCIAS);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Processando sua solicitação...", Snackbar.LENGTH_SHORT)
                        .setAction("Cancelar", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //on action click code
                            }
                        }).show();
                setFirstItemNavigationView();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        selectedMenuItem = navigationView.getMenu().getItem(0);
    }

    private void setFirstItemNavigationView() {
        if (selectedMenuItem.getItemId() == R.id.nav_percuso) {
            navigationView.setCheckedItem(R.id.nav_trecho);
            navigationView.getMenu().performIdentifierAction(R.id.nav_trecho, 0);
        } else if (selectedMenuItem.getItemId() == R.id.nav_trecho) {
            navigationView.setCheckedItem(R.id.nav_favorito);
            navigationView.getMenu().performIdentifierAction(R.id.nav_favorito, 0);
        } else if (selectedMenuItem.getItemId() == R.id.nav_favorito) {
            navigationView.setCheckedItem(R.id.nav_hospedagem);
            navigationView.getMenu().performIdentifierAction(R.id.nav_hospedagem, 0);
        } else if (selectedMenuItem.getItemId() == R.id.nav_hospedagem) {
            navigationView.setCheckedItem(R.id.nav_alimentacao);
            navigationView.getMenu().performIdentifierAction(R.id.nav_alimentacao, 0);
        } else if (selectedMenuItem.getItemId() == R.id.nav_alimentacao) {
            navigationView.setCheckedItem(R.id.nav_passeio);
            navigationView.getMenu().performIdentifierAction(R.id.nav_passeio, 0);
        } else if (selectedMenuItem.getItemId() == R.id.nav_passeio) {
            navigationView.setCheckedItem(R.id.nav_negocios);
            navigationView.getMenu().performIdentifierAction(R.id.nav_negocios, 0);
        } else if (selectedMenuItem.getItemId() == R.id.nav_negocios) {
            navigationView.setCheckedItem(R.id.nav_eventos);
            navigationView.getMenu().performIdentifierAction(R.id.nav_eventos, 0);
        } else if (selectedMenuItem.getItemId() == R.id.nav_eventos) {
            Intent intent = new Intent(PrincipalActivity.this, MapsActivity.class);
            startActivityForResult(intent, VarConUtils.MAPAS);
        } else {
            navigationView.setCheckedItem(R.id.nav_agenda);
            navigationView.getMenu().performIdentifierAction(R.id.nav_agenda, 0);
        }
    }

    private void hideKeyBoard(View view)
    {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setAdapterSpinner(Spinner spinner) {
        List<String> opcoes = new ArrayList<String>();
        opcoes.add("Opção 1");
        opcoes.add("Opção 2");
        opcoes.add("Opção 3");
        opcoes.add("Opção 4");
        opcoes.add("Opção 5");
        opcoes.add("Opção 6");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, opcoes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void datePickerDialog() {
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        dateDialog = new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                if (editTextData != null)
                    editTextData.setText(dateFormatter.format(newDate.getTime()));

                dateDialog.hide();
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void onDateClickListener(View view) {
        editTextData = getDataEditText(view.getId());
        hideKeyBoard(editTextData);
        Log.i(PrincipalActivity.class.getName(), "setOnClickListener>> " + editTextData.getId() + " : " + editTextData.getText());
        dateDialog.show();
    }

    private AutoCompleteTextView getDataEditText(int id) {
        switch (id) {
            case R.id.imgBtnAgendaDataInicial:
                return ((AutoCompleteTextView) findViewById(R.id.editTextAgendaDataInicial));
            case R.id.imgBtnAgendaDataFinal:
                return ((AutoCompleteTextView) findViewById(R.id.editTextAgendaDataFinal));
            case R.id.imgBtnPercusoDataInicial:
                return ((AutoCompleteTextView) findViewById(R.id.editTextPercusoDataInicial));
            case R.id.imgBtnPercusoDataFinal:
                return ((AutoCompleteTextView) findViewById(R.id.editTextPercusoDataFinal));
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_minha_conta) {
            Intent intent = new Intent(PrincipalActivity.this, ProfileActivity.class);
            startActivityForResult(intent, VarConUtils.PREFERENCIAS);
            return true;
        }
        else if (id == R.id.action_preferencias) {
            Intent intent = new Intent(PrincipalActivity.this, PreferenciasActivity.class);
            startActivityForResult(intent, VarConUtils.PREFERENCIAS);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        selectedMenuItem = item;
        int id = item.getItemId();

        ((LinearLayout) findViewById(R.id.llAgenda)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.llPercuso)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.llTrecho)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.llHospedagem)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.llAlimentacao)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.llPasseio)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.llEventos)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.llNegocios)).setVisibility(View.GONE);

        if (id == R.id.nav_percuso) {
            mTitle = "Percuso";
            ((LinearLayout) findViewById(R.id.llPercuso)).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_trecho) {
            mTitle = "Trecho";
            ((LinearLayout) findViewById(R.id.llTrecho)).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_favorito) {
            mTitle = "Favorito";
        } else if (id == R.id.nav_hospedagem) {
            mTitle = "Hospedagem";
            ((LinearLayout) findViewById(R.id.llHospedagem)).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_alimentacao) {
            mTitle = "Alimentação";
            ((LinearLayout) findViewById(R.id.llAlimentacao)).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_passeio) {
            mTitle = "Passeio";
            ((LinearLayout) findViewById(R.id.llPasseio)).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_negocios) {
            mTitle = "Negocios";
            ((LinearLayout) findViewById(R.id.llNegocios)).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_eventos) {
            mTitle = "Eventos";
            ((LinearLayout) findViewById(R.id.llEventos)).setVisibility(View.VISIBLE);
        } else {
            mTitle = "Agenda";
            ((LinearLayout) findViewById(R.id.llAgenda)).setVisibility(View.VISIBLE);
        }

        getSupportActionBar().setTitle(mTitle);

//        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//
//        Marker davao = map.addMarker(new MarkerOptions().position(new LatLng(7.0722, 125.6131)).title("Davao City").snippet("Ateneo de Davao University"));
//
//        // zoom in the camera to Davao city
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.0722, 125.6131), 15));
//
//        // animate the zoom process
//        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}