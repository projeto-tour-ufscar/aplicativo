package turismo.com.br.turismo.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import turismo.com.br.turismo.R;
import turismo.com.br.turismo.profile.ProfileActivity;
import turismo.com.br.turismo.utils.VarConUtils;

public class PrincipalActivity  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Snackbar.make(view, "Processando sua solicitação...", Snackbar.LENGTH_LONG)
                        .setAction("Cancelar", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //on action click code
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_agenda) {
            mTitle = "Agenda";
        } else if (id == R.id.nav_percuso) {
            mTitle = "Percuso";
        } else if (id == R.id.nav_trecho) {
            mTitle = "Trecho";
        } else if (id == R.id.nav_favorito) {
            mTitle = "Favorito";
        } else if (id == R.id.nav_hospedagem) {
            mTitle = "Hospedagem";
        } else if (id == R.id.nav_alimentacao) {
            mTitle = "Alimentação";
        } else if (id == R.id.nav_passeio) {
            mTitle = "Passeio";
        } else if (id == R.id.nav_negocios) {
            mTitle = "Negocios";
        } else if (id == R.id.nav_eventos) {
            mTitle = "Eventos";
        }

        getSupportActionBar().setTitle(mTitle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}