package co.edu.ue.pokenavigation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;
import co.edu.ue.pokenavigation.R;
import co.edu.ue.pokenavigation.data.FavoritesManager;

public class FavoritesFragment extends Fragment {

    private ListView listViewFavorites;

    public FavoritesFragment() {
        super(R.layout.fragment_favorites);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewFavorites = view.findViewById(R.id.listViewFavorites);

        // Obtenemos la lista de favoritos guardados
        List<String> favoritos = FavoritesManager.favoritos;

        if (listViewFavorites != null && favoritos != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    favoritos
            );
            listViewFavorites.setAdapter(adapter);
        }
    }
}