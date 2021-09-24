package ermmmm.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class API {

	static final String single = "https://api.waifu.pics/type/category";    // GET
	static final String many = "https://api.waifu.pics/many/type/category";  // POST

	static String getSingleUrl(Type type, Category category) {
		return single.replace("type", type.toString()).replace("category", category.toString());
	}

	static String getManyUrl(Type type, Category category) {
		return many.replace("type", type.toString()).replace("category", category.toString());
	}

	public static String single(Type type, Category cat) throws IOException, InterruptedException {
		String url = getSingleUrl(type, cat);

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return JsonParser.parseString(response.body()).getAsJsonObject().get("url").getAsString();
	}

	static String single() throws IOException, InterruptedException {
		Type type = Type.sfw;
		Category cat = SfwCategory.waifu;
		return single(type, cat);
	}

	public static void singleAsync(Type type, Category cat, Consumer<String> onCompletion) throws IOException, InterruptedException {
		String url = getSingleUrl(type, cat);

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenAccept(response -> {
					String responseUrl = JsonParser.parseString(response.body()).getAsJsonObject().get("url").getAsString();
					onCompletion.accept(responseUrl);
				});
	}

	public static String[] thirty(Type type, Category cat) throws IOException, InterruptedException {
		String url = getManyUrl(type, cat);

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("content-type", "application/x-www-form-urlencoded")
				.POST(HttpRequest.BodyPublishers.ofString("exclude=[]"))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		String responseBody = response.body();

		JsonArray urls = JsonParser.parseString(responseBody).getAsJsonObject().getAsJsonArray("files");
		return new Gson().fromJson(urls, String[].class);
	}

	static String[] thirty() throws IOException, InterruptedException {
		Type type = Type.sfw;
		Category cat = SfwCategory.waifu;
		return thirty(type, cat);
	}

}
