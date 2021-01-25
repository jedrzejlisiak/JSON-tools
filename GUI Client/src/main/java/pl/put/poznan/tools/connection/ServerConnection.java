package pl.put.poznan.tools.connection;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class ServerConnection {
    private URI addr;
    private HttpRequest http;
    private int[] files;

    public ServerConnection(String address) {
        try {
            addr = new URI(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Random r = new Random();

        files = new int[2];
        files[0] = r.nextInt();
        files[1] = r.nextInt();
    }

    public void uploadFile(String file, int slot) {
        URI requestAddr;
        try {
            requestAddr = new URI(addr.toString() + "?no="+files[slot%2]);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestAddr)
                .POST(HttpRequest.BodyPublishers.ofString(file))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response;

        try {
            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(response.statusCode());
        System.out.println(response.body());

    }

    public HttpResponse<String> sendRequest(String params, int slot, boolean compare) {
        URI requestAddr;
        try {
            requestAddr = new URI(addr.toString() +
                    "?no="+files[slot%2] + (compare ? "," + files[1] : "") +
                    (params.length() > 0 ? '&' + params : ""));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestAddr)
                .GET()
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response;

        try {
            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }
}
