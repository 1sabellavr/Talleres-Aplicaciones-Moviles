package co.edu.ue.pokenavigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import co.edu.ue.pokenavigation.ui.FavoritesFragment;
import co.edu.ue.pokenavigation.ui.HomeFragment;
import co.edu.ue.pokenavigation.ui.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
        configurarBottomNavigation();

        if (savedInstanceState == null) {
            cargarFragment(new HomeFragment());
        }
    }

    private void initObjects() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private Fragment obtenerFragment(int itemId) {
        if (itemId == R.id.navigation_home) {
            return new HomeFragment();
        }
        if (itemId == R.id.navigation_favorites) {
            return new FavoritesFragment();
        }
        if (itemId == R.id.navigation_info) {
            return new InfoFragment();
        }
        return null;
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void configurarBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = obtenerFragment(item.getItemId());
            if (fragment == null) {
                return false;
            }
            cargarFragment(fragment);
            return true;
        });
    }
}