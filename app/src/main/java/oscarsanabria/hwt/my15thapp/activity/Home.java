package oscarsanabria.hwt.my15thapp.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import oscarsanabria.hwt.my15thapp.R;
import oscarsanabria.hwt.my15thapp.fragment.CheckListFragment;
import oscarsanabria.hwt.my15thapp.fragment.GuestFragment;
import oscarsanabria.hwt.my15thapp.fragment.HomeFragment;
import oscarsanabria.hwt.my15thapp.fragment.SuppliersFragment;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_GUESTS = "guests";
    private static final String TAG_SUPPLIERS = "suppliers";
    private static final String TAG_CHECKLISTS   = "checklists";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    //Declarations variables for use Camera
    final private int CAPTURE_IMAGE = 2;
    ImageView imagenNueva;
    Bitmap bmp;
    EditText editname,editmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);//activity_home.xml
        navigationView = (NavigationView) findViewById(R.id.nav_view);//activity_home.xml
        fab = (FloatingActionButton) findViewById(R.id.fab);//app_bar_home.xml

        // Navigation view header
        /*
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        */

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            Fragment fragment;
            fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();
        }

        imagenNueva = (ImageView) findViewById(R.id.profilePicture);
        editname = (EditText) findViewById(R.id.editTextName);
        editmail = (EditText) findViewById(R.id.editTextMail);

        editname.setText(R.string.name_of_user);
        editmail.setText(R.string.mail_of_user);
       // btn = (Button)findViewById(R.id.btnCamera);
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        CharSequence text="";

        if (id == R.id.nav_camera) {
            // Handle the camera action
            text="Home";

            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            imagenNueva.setVisibility(View.VISIBLE);
            editname.setVisibility(View.VISIBLE);
            editmail.setVisibility(View.VISIBLE);
            //btn.setVisibility(View.VISIBLE);

            Fragment fragment;
            fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();

        } else if (id == R.id.nav_gallery) {
            text="Guests";
            navItemIndex = 1;
            CURRENT_TAG = TAG_GUESTS;

            imagenNueva.setVisibility(View.INVISIBLE);
            editname.setVisibility(View.INVISIBLE);
            editmail.setVisibility(View.INVISIBLE);
           // btn.setVisibility(View.INVISIBLE);

            Fragment fragment;
            fragment = new GuestFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();

        } else if (id == R.id.nav_slideshow) {
            text="Check List";
            navItemIndex = 2;
            CURRENT_TAG = TAG_CHECKLISTS;

            imagenNueva.setVisibility(View.INVISIBLE);
            editname.setVisibility(View.INVISIBLE);
            editmail.setVisibility(View.INVISIBLE);
           // btn.setVisibility(View.INVISIBLE);

            Fragment fragment;
            fragment = new CheckListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();

        } else if (id == R.id.nav_manage) {
            text="Suppliers";
            navItemIndex = 3;
            CURRENT_TAG = TAG_SUPPLIERS;

            imagenNueva.setVisibility(View.INVISIBLE);
            editname.setVisibility(View.INVISIBLE);
            editmail.setVisibility(View.INVISIBLE);
            //btn.setVisibility(View.INVISIBLE);

            Fragment fragment;
            fragment = new SuppliersFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();

        } else if (id == R.id.nav_share) {
            text="About Us";
            Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
            drawer.closeDrawers();
            return true;

        } else if (id == R.id.nav_send) {
            text="Privacy Policy";
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
            drawer.closeDrawers();
            return true;

        }

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void takePicture(View v){
        //Toast.makeText(getApplicationContext(),"Take a picture!", Toast.LENGTH_SHORT).show();

        //HomeFragment home = new HomeFragment();
        Toast.makeText(getApplicationContext(),"Take a picture Function!", Toast.LENGTH_SHORT).show();
        //home.takePicture();


        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE);


    }

    public  void NotificacionSimple(int id, int iconId, String titulo, String contenido) {

        //Constructor de la Notificacion
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        //Aqui se selecciona el icono que se va a mostrar
                        .setSmallIcon(R.drawable.ic_stat_logo_noti)
                        //Aqui pones el titulo de la notificacion
                        .setContentTitle(titulo)
                        //Aqui pones el  contenido del titulo
                        .setContentText(contenido)
                        //Seleccionas un color para la notificacion
                        .setColor(getResources().getColor(R.color.colorPrimaryDark))
                        //Selecciones el nivel  de seguridad que va a tener la notificacione
                        .setVisibility(NotificationCompat.VISIBILITY_SECRET);


        // Construir la notificación y emitirla

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(id, builder.build());

    }

    @Override
    protected  void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK && data != null)
        {
            Bundle ext = data.getExtras();
            bmp  = (Bitmap)ext.get("data");
            imagenNueva.setImageBitmap(bmp);

            NotificacionSimple(1,1,"Congratulations!","Your photo is loading correctly");
        }

    }

    @Override
    public void onDestroy(){
        System.out.println("Im destroying");
        super.onDestroy();
    }

    @Override
    public void onPause(){
        System.out.println("Im pausing");
        super.onPause();

    }

    @Override
    public void onResume(){
        System.out.println("On Resume");
        super.onResume();
    }
    @Override
    public void onStop(){
        System.out.println("On stop");
        super.onStop();
    }

    @Override
    public void onRestart(){
        System.out.println("on Restart");
        super.onRestart();
    }
    @Override
    public void onStart(){
        System.out.println("on Start");
        super.onStart();
    }
}
