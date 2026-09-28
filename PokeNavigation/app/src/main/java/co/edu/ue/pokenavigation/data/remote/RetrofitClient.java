package co.edu.ue.pokenavigation.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // URL principal de la API[cite: 25]
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    // Instancia única de Retrofit[cite: 25]
    private static Retrofit retrofit;

    // Constructor privado para evitar crear objetos de esta clase[cite: 25]
    private RetrofitClient() {
    }

    // Método para obtener el servicio de la API[cite: 25]
    public static PokeApiService getService() {
        // Retrofit se crea solamente una vez[cite: 25]
        if (retrofit == null) {
            // Permite visualizar en Logcat las peticiones HTTP[cite: 25]
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            // Muestra información básica: método, URL y código de respuesta[cite: 25]
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            // Cliente HTTP encargado de realizar las peticiones[cite: 25]
            OkHttpClient client = new OkHttpClient.Builder()
                    // Se agrega el interceptor de logs[cite: 25]
                    .addInterceptor(logging)
                    .build();

            // Configuración de Retrofit[cite: 26]
            retrofit = new Retrofit.Builder()
                    // URL base de la API[cite: 26]
                    .baseUrl(BASE_URL)
                    // Se utiliza el cliente HTTP configurado[cite: 26]
                    .client(client)
                    // Convierte el JSON de la API en objetos Java[cite: 26]
                    .addConverterFactory(GsonConverterFactory.create())
                    // Construye Retrofit[cite: 26]
                    .build();
        }

        // Retrofit implementa automáticamente PokeApiService[cite: 26]
        return retrofit.create(PokeApiService.class);
    }
}