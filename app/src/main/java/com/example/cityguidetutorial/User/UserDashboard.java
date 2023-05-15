package com.example.cityguidetutorial.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cityguidetutorial.Common.LoginSignup.RetailerStartUpScreen;
import com.example.cityguidetutorial.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.example.cityguidetutorial.HelperClasses.HomeAdapter.CategoriesHelper;
import com.example.cityguidetutorial.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.cityguidetutorial.HelperClasses.HomeAdapter.FeaturedHelper;
import com.example.cityguidetutorial.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.example.cityguidetutorial.HelperClasses.HomeAdapter.MostViewedHelper;
import com.example.cityguidetutorial.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static final float END_SCALE = 0.7f;

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    ImageView menuIcon, retailerBtn;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        menuIcon = findViewById(R.id.menu_icon);
        retailerBtn = findViewById(R.id.login_signup);
        contentView = findViewById(R.id.content);

        //Menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //Calls
        navigationDrawer();
        callRetailerScreens();

        //Recycler Views Function Calls
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();

    }

    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelper> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelper(R.drawable.mcdonalds, "Mcdonald's", "McDonald's is a fast-food chain known for its burgers. It has a global presence and serves millions of customers every day."));
        featuredLocations.add(new FeaturedHelper(R.drawable.shopping_image, "Shopping Praça nova", "Praça Nova Shopping is a popular mall located in Santa Maria, Rio Grande do Sul, Brazil. It offers a variety of shops, restaurants, and entertainment options for visitors to enjoy."));
        featuredLocations.add(new FeaturedHelper(R.drawable.lojasamericanas, "Lojas Americanas", "Lojas Americanas is a Brazilian retail chain that sells a wide range of products, including electronics, home goods, food, and clothing. It has over 1,700 stores across the country and is known for its competitive prices and frequent promotions."));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);
    }

    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelper> mostViewedLocations = new ArrayList<>();

        mostViewedLocations.add(new MostViewedHelper(R.drawable.mcdonalds, "Mcdonald's", "McDonald's is a fast-food chain known for its burgers. It has a global presence and serves millions of customers every day."));
        mostViewedLocations.add(new MostViewedHelper(R.drawable.burguer_king_image, "Burguer King", "Burger King is a global fast-food chain known for its flame-grilled burgers, crispy fries, and iconic Whopper sandwich. With thousands of locations worldwide, it offers a convenient and affordable dining experience for millions of customers."));
        mostViewedLocations.add(new MostViewedHelper(R.drawable.majestic_hotel_image, "Majestic Palace Hotel", "Majestic Palace Hotel is a luxurious five-star hotel located in Florianópolis, Brazil. It offers stunning views of the city and the sea, top-notch amenities, and exceptional service to its guests, making it a popular choice for business and leisure travelers alike."));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void categoriesRecycler() {
        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoriesHelper> categories = new ArrayList<>();

        categories.add(new CategoriesHelper(R.drawable.hotel_image, "Hotels"));
        categories.add(new CategoriesHelper(R.drawable.shopping_drawing, "Malls"));
        categories.add(new CategoriesHelper(R.drawable.restaurant_image, "Hotels"));

        adapter = new CategoriesAdapter(categories);
        categoriesRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_categories:
                startActivity(new Intent(getApplicationContext(), AllCategories.class));

        }

        return true;
    }

    @Override
    public void onBackPressed(){

        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void callRetailerScreens(){
        retailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
            }
        });
    }
}