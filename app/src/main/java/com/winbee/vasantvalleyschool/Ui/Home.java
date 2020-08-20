package com.winbee.vasantvalleyschool.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.winbee.vasantvalleyschool.AboutUsActivity;
import com.winbee.vasantvalleyschool.BuildConfig;
import com.winbee.vasantvalleyschool.Login;
import com.winbee.vasantvalleyschool.R;
import com.winbee.vasantvalleyschool.Utils.AssignmentData;
import com.winbee.vasantvalleyschool.Utils.SharedPrefManager;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Objects;

public class Home extends AppCompatActivity implements bottomDialogForAttachment.BottomSheetListener
, DialogAttachment.OnConfirmClicked, NavigationView.OnNavigationItemSelectedListener {

    ImageView sideBar, notification , home , website, aboutUs , contactUs,logOut;
   @SuppressLint("StaticFieldLeak")
   static TextView titleHome;

   HomeworkDiscriptionFragment homeworkDiscriptionFragment;
    String sCurrentVersion,sLastestVersion,Userid,Username,UserPassword;
    private FirebaseAuth auth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new GetLastesVersion().execute();
        //firebase Common notification
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notification","Notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        FirebaseMessaging.getInstance().subscribeToTopic("vasantvalleyschool")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }

                        //Toast.makeText(GecHomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        sideBar=findViewById(R.id.sideBarHome);
        notification=findViewById(R.id.notificationHome);
        home=findViewById(R.id.homeHome);
        website=findViewById(R.id.WebsiteHome);
        aboutUs=findViewById(R.id.aboutUsHome);
        contactUs=findViewById(R.id.contactUsHome);
        logOut=findViewById(R.id.logOutHome);
        titleHome=findViewById(R.id.titleHome);
        Userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        Username=SharedPrefManager.getInstance(this).refCode().getEmail();
        UserPassword=SharedPrefManager.getInstance(this).refCode().getPassword();

        titleHome.setText("HOME");

        sideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSideBarPressed();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNotificationPressed();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHomePressed();
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWebsitePressed();
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAboutPressed();
            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContactUsPressed();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogOutPressed();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AddingFragmentToView();

        Uservalidation();

    }

    private void onLogOutPressed() {
        logout();
    }

    private void onContactUsPressed() {
        Uri u = Uri.parse("tel:" + "+91 8769564224");
        Intent i = new Intent(Intent.ACTION_DIAL, u);
        try
        {
            startActivity(i);
        }
        catch (SecurityException s)
        {
        }
    }

    private void onAboutPressed() {
        Intent intent = new Intent(Home.this, AboutUsActivity.class);
        startActivity(intent);

    }

    private void onWebsitePressed() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vasantvalleyjaipur.org/"));
        startActivity(intent);
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("vasantvalleyjaipur.org"));
//        startActivity(browserIntent);
    }

    private void onHomePressed() {
        HomeFragment homeFragment = (HomeFragment)
                getSupportFragmentManager()
                        .findFragmentByTag("HomeFragment");
        if (homeFragment!=null && homeFragment.isVisible()){

        }else {
            AddingFragmentToView();
        }
    }

    private void onNotificationPressed() {
        NotificationFragment notificationFragment= new NotificationFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.homeFrame,notificationFragment,"NotificationFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void onSideBarPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    private void AddingFragmentToView() {
        HomeFragment homeFragment= new HomeFragment();
        FragmentTransaction fragmentTransaction= getSupportFragmentManager()
                .beginTransaction().replace(R.id.homeFrame,homeFragment,"HomeFragment");
        fragmentTransaction.commit();}

    @Override
    public void onBackPressed() {
        titleHome.setText("Home");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home=new Intent(Home.this,Home.class);
            startActivity(home);
        } else if (id == R.id.nav_profile) {
            MyProfileFragment myProfileFragment=new MyProfileFragment();
            FragmentTransaction fragmentTransaction =getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrame,myProfileFragment,"MyProfileFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(Home.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));
        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sekhawati Defence Academy");
                String shareMessage= "\nSekhawati Defence Academy download the application.\n ";
                shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
            }

        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, Login.class));
        Objects.requireNonNull(this).finish();
    }

    @Override
    public void onPhotoAttachmentClicked() {

        homeworkDiscriptionFragment= (HomeworkDiscriptionFragment) getSupportFragmentManager()
                .findFragmentByTag("HomeworkDiscriptionFragment");
        if (homeworkDiscriptionFragment != null) {
            homeworkDiscriptionFragment.onPhotoAttachmentClicked();
        }
    }

    @Override
    public void onPdfAttachmentClicked() {

        homeworkDiscriptionFragment= (HomeworkDiscriptionFragment) getSupportFragmentManager()
                .findFragmentByTag("HomeworkDiscriptionFragment");
        if (homeworkDiscriptionFragment != null) {
            homeworkDiscriptionFragment.onPdfAttachmentClicked();
        }
    }

    @Override
    public void onConfirmClickedPhoto(Bitmap bitmap) {
        homeworkDiscriptionFragment= (HomeworkDiscriptionFragment) getSupportFragmentManager()
                .findFragmentByTag("HomeworkDiscriptionFragment");
        if (homeworkDiscriptionFragment != null) {
            homeworkDiscriptionFragment.onImageClicked(bitmap);
        }
    }

    // showing the update pop up to user
    private class GetLastesVersion extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                sLastestVersion = Jsoup
                        .connect("https://play.google.com/store/apps/details?id="+getPackageName())
                        .timeout(3000)
                        .get()
                        .select("div.hAyfc:nth-child(4)>"+"span:nth-child(2)> div:nth-child(1)"+"> span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sLastestVersion;
        }
        @Override
        protected void onPostExecute(String s) {
            sCurrentVersion = BuildConfig.VERSION_NAME;
            if (sLastestVersion !=null){
                float cVersion = Float.parseFloat(sCurrentVersion);
                float lVersion = Float.parseFloat(sLastestVersion);
                // check the condition whether the lastet version is greater than current version
                if (lVersion>cVersion){
                    // create update alert dilog box
                    updateAlertDialog();
                }
            }
        }
    }
    private void updateAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Set title
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("update available");
        builder.setCancelable(false);
        builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="+getPackageName())));

                //dismiss dialog
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        builder.show();
    }
    public void Uservalidation() {
        String Email = AssignmentData.Email;
        String Password=AssignmentData.Password;
        if (!Email.equals("")&& !Password.equals("")){
            auth.signInWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                            }else{
                                Toast.makeText(Home.this, "NetWork Issue Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}