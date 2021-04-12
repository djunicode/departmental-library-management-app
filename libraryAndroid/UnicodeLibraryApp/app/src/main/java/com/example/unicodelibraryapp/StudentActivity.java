package com.example.unicodelibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentActivity extends AppCompatActivity
{
    public BookListFragment bookListFragment; //A reference to the book list fragment which is displayed

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //Setting toolbar as action bar
        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        //Initializing the bottom nav
        initializeBottomNav();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflating the toolbar menu
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        /*Handles clicks on the toolbar menu items*/

        Log.e("MITEM", "menu clicked");

        switch(menuItem.getItemId())
        {
            case R.id.sort_booklist : {
                //Checking if the book list fragment is currently being displayed
                if(bookListFragment != null)
                {
                    //Displaying the sorting bottom sheet
                    BookListSortBottomSheetDialogFragment bottomSheet = new BookListSortBottomSheetDialogFragment();
                    bottomSheet.show(getSupportFragmentManager(), "SORT_BSHEET");
                }

                return true;
            }

            case R.id.filter_booklist :
            {
                //Checking if the book list fragment is currently being displayed
                if(bookListFragment != null)
                {
                    //Displaying the Filters bottom sheet
                    BookListCategoryBottomSheetDialogFragment bottomSheet = new BookListCategoryBottomSheetDialogFragment();
                    bottomSheet.show(getSupportFragmentManager(), "SORT_BSHEET");
                }

                return true;
            }
        }

        return false;
    }

    private void initializeBottomNav()
    {
        /*Initializies the bottom navigation view*/

        BottomNavigationView bottomNav = findViewById(R.id.app_bottomnav); //Getting the bottom navigation view

        //Inflating the bottom nav menu
        bottomNav.inflateMenu(R.menu.bottomnav_menu);

        //Adding the nav controller
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment); //Getting the nav host fragment
        NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController()); //Adding the host fragment's nav controller to the bottom nav android
    }

    public void setListSort(boolean isAscending)
    {
        /*Sets the ordering of the books in the book list*/

        if(this.bookListFragment != null)
        {
            this.bookListFragment.displayBookList(isAscending);
        }
    }

}