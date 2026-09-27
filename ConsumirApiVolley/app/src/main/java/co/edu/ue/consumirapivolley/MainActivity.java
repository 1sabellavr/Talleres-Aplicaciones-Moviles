package co.edu.ue.consumirapivolley; //Define la ubicación o dirección del archivo dentro de la estructura del proyecto
/*Se comunica con JAVA para decirle que voy a usar ciertas herramientas que están en
 librerias externas o del sistema, entonces que las traiga para no tener que escribir
 la ruta completa nuevamente*/

//Componentes nativos de Android e Interfaz de Usuario
import android.os.Bundle; //Objeto para pasar datos entre pantallas y guardar el estado de la aplicación
import android.view.View; //clase base de la cual heredan todos los componentes visuales (botones, textos, etc.)
import android.widget.ArrayAdapter; //adaptador que toma una lista de datos en Java y la transforma en elementos visuales para la lista
import android.widget.ListView; //Componente visual para mostrar una lista desplazable de elementos
import android.widget.ProgressBar; //El indicador visual de carga (el círculo giratorio)
import android.widget.TextView;//Componente para mostrar textos en pantalla
import android.widget.Toast; //Las pequeñas notificaciones flotantes temporales que aparecen abajo en la pantalla


//Librerías de Compatibilidad (AndroidX)
import androidx.activity.EdgeToEdge; //*
import androidx.appcompat.app.AppCompatActivity;//Proporciona compatibilidad para que las características modernas de Android funcionen igual en celulares viejos
import androidx.core.graphics.Insets;//*
import androidx.core.view.ViewCompat;//*
import androidx.core.view.WindowInsetsCompat; //* --> Clases modernas que sirven para ajustar la pantalla de borde a borde (haciendo que el diseño respete la barra de estado y la barra de navegación del celular)


//Librería Volley (Peticiones HTTP a internet)
import com.android.volley.Request; //Define los métodos HTTP (GET, POST, PUT, DELETE
import com.android.volley.RequestQueue; //Cola de peticiones, donde se administra y ejecutan las consultas a internet en segundo plano
import com.android.volley.VolleyError; //Clase que captura cualquier error, ej: Falta de conexión
import com.android.volley.toolbox.JsonArrayRequest; //Petición especializada en recibir arreglos/listas JSON, desde un servidor
import com.android.volley.toolbox.Volley; //Clase principal con utilidades para iniciar la librería como tal

//Manejo de JSON y Colecciones de Java
import org.json.JSONException; //Captura errores si la estructura o construcción del JSON está mal hecha
import org.json.JSONObject; //Permite leer objetos en formato JSON

import java.util.ArrayList; //Estructura de datos estandar para almacenar colecciones dinámicas de elementos
import java.util.List;//

public class MainActivity extends AppCompatActivity {

    //Constantes
    private static final String URL_API = "https://jsonplaceholder.typicode.com/posts";
    private static final String REQUEST_TAG = "GET_POSTS";

    /* PRIVATE ---> Solo se puede acceder a esta variable en el MainActivity
       STATIC ---> LA variable pertenece a la clase MAin en si y solo se guarda una vez en la memoria
       FINAL ---> Es inmutable (constante). Su valor no puede cambiar en el momento de ejecución
       STRING ---> Tipo de dato de texto
       URL_API && REQUEST_TAG --> Direccion web a consultar y la etiqueta para identificar la peticion HTTP en VOlley
    */


    /*Atributos / Variables de la Vista: Declarar las variables que representan los componentes
    visuales del diseño en activity_main.
    Por el momento se encuentran null, más adelante con el findViewById se vincularán con los elementos reales de la pantalla*/

    private ListView lvTodos;
    private ProgressBar progressBar;
    private TextView tvEstado;

    private RequestQueue requestQueue;//Declara variable de la cola de peticiones

    //Declaración e Instanciación de las Listas
    private final List<Post> listaPosts = new ArrayList<>();//Clase Post --> molde que almacena los datos que fueron extraidos (parseados) por main. Guarda obj tipo Post
    private final List<String> listaFormateada = new ArrayList<>(); //Guarda la info que se presenta. Es la lista que se entrega ArrayAdapter para que el listview sepa que tiene que dibujar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicializarVistas();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        consumirApi();
    }

    private void inicializarVistas() {
        lvTodos = findViewById(R.id.lvTodos);
        progressBar = findViewById(R.id.progressBar);
        tvEstado = findViewById(R.id.tvEstado);
    }

    private void mostrarCargando(boolean cargando) {
        progressBar.setVisibility(cargando ? View.VISIBLE : View.GONE);
        lvTodos.setVisibility(cargando ? View.GONE : View.VISIBLE);
    }

    private void mostrarError(String mensaje) {
        mostrarCargando(false);
        tvEstado.setText(mensaje);
        tvEstado.setVisibility(View.VISIBLE);
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void procesarError(VolleyError error) {
        String mensaje = error.getMessage();

        if (mensaje == null || mensaje.trim().isEmpty()) {
            mensaje = "Verifique la conexión a internet.";
        }
        mostrarError("Error en la solicitud: " + mensaje);
    }

    private void consumirApi() {
        mostrarCargando(true);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL_API,
                null,
                response -> {
                    listaPosts.clear();
                    listaFormateada.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject item = response.getJSONObject(i);

                            Post post = new Post(
                                    item.getInt("userId"),
                                    item.getInt("id"),
                                    item.getString("title"),
                                    item.getString("body")
                            );

                            listaPosts.add(post); // Guarda el objeto Post estructurado
                            listaFormateada.add(post.toString()); // O la cadena de texto que deseas mostrar
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_list_item_1,
                                listaFormateada
                        );

                        lvTodos.setAdapter(adapter);
                        mostrarCargando(false);
                        tvEstado.setVisibility(View.GONE);

                        Toast.makeText(this, "Se recibieron " + listaPosts.size() + " registros.", Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        mostrarError("No fue posible procesar la respuesta.");
                    }
                },
                this::procesarError
        );

        request.setTag(REQUEST_TAG);
        requestQueue.add(request);
    }
}