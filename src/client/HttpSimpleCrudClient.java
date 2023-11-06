package client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * **Class Description:**
This class allows you to perform CRUD operations (GET, POST, DELETE, PATCH) 
on the specified URL and object. It serves as a client to communicate with the RESTful API. 
The class makes it easy to make and send HTTP requests to a specified URL 
and provides functions for manipulating the data that is transferred between
the client and the server.
**Features and Uses:**
- **GET:** You can use this class to get data from a REST API by calling the appropriate URL. 
This operation returns the data retrieved from the server in JSON format and allows you to
 get up-to-date information.
- **POST:** The class allows you to create new records in the REST API by sending a 
JSON object to a specified URL. This way you can create new records or update existing data.
- **DELETE:** You can use this class to delete records from the REST API based on a 
specified identifier (eg ID). This operation allows you to delete unnecessary or obsolete
 records.
- **PATCH:** The class allows you to make partial updates to existing records in the REST API. 
You can send only changed data, which can improve the efficiency of communication 
between the client and the server.
**Data processing:**
The class contains the logic to convert the object to JSON and back (serialization and deserialization), 
allowing efficient communication between client-side code and the server.
 Data is converted to JSON format and sent to the REST API, and vice versa, 
 responses from the server are deserialized from JSON back to objects.
**Encapsulation and Reusability:**
This class allows encapsulation of data and operations,
 which improves data security and consistency. Thanks to its reusability,
  you can use this class in different projects and get an efficient and easy-to-manage way 
  of working with web services.
  */
  public class HttpSimpleCrudClient {
        private static URL url;
        private static HttpURLConnection connection;        
        public HttpSimpleCrudClient() {
        }
        /**
         * @param url
         * @param connection
       */
      public HttpSimpleCrudClient(URL url, HttpURLConnection connection) {
            HttpSimpleCrudClient.url = url;
            HttpSimpleCrudClient.connection = connection;
      }
      /**
       * @return the url
       */
      public URL getUrl() {
            return url;
      }
      /**
       * @param url the url to set
       */
      public void setUrl(URL url) {
            HttpSimpleCrudClient.url = url;
      }
      /**
       * @return the connection
       */
      public HttpURLConnection getConnection() {
            return connection;
      }
      /**
       * @param connection the connection to set
       */
      public void setConnection(HttpURLConnection connection) {
            HttpSimpleCrudClient.connection = connection;
      }
//____________________________________________________________________________________________ 
      // Returns all Objects and loads to the console 
      public static void getObjects(URL newUrl){
            try {
                  // Create a URL object with the target URL
                  url= new URL(newUrl.toString());
                  // Open a connection to the URL
                  connection = (HttpURLConnection) url.openConnection();
                  connection.setRequestMethod("GET");
                  int responseCode = connection.getResponseCode();
                  System.out.println("Response Code: " + responseCode);

                  if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // Print the response
                System.out.println("Response Body:");
                System.out.println(response.toString());
            } else {
                System.out.println("GET request failed.");
            }
               connection.disconnect();  
            } catch (IOException e) {
                  System.err.println(e+" connection is faul");
            }
      }
//____________________________________________________________________________________________
      // // Returns a List of Objects and loads to the console - suitable for working with a table
      //  through DefaultTableModel 
      public static List<Object> getObjectsList(URL newUrl) {
            URL url;
            List<Object> items = new ArrayList<>();
            try {
                  // Create a URL object with the target URL
                  url= new URL(newUrl.toString());
                  // Open a connection to the URL
                 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                  connection.setRequestMethod("GET");
                  int responseCode = connection.getResponseCode();
                  System.out.println("Response Code: " + responseCode);

                  if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read the response from the server
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuffer response = new StringBuffer();
                        while ((line = reader.readLine()) != null) {
                              response.append(line);
                        }
                        Gson gson = new Gson();
                        items = gson.fromJson(response.toString(), new TypeToken<List<Object>>(){}.getType());
                        reader.close();
                        // Print the response
                        System.out.println("Response Body:");
                        System.out.println(response.toString());
                    } else {
                        System.out.println("GET request failed.");
                    }
                       connection.disconnect();  
            } catch (IOException e) {
                  System.err.println(e+" connection is faul");
            }
            return items;
      }
//____________________________________________________________________________________________
     // Returns one Object according to the specified id
      public static void getObjectWhitId(URL newUrl,int id){
            try {
                  // Create a URL object with the target URL
                  url= new URL(newUrl.toString()+id);
                  // Open a connection to the URL
                  connection = (HttpURLConnection) url.openConnection();
                  // Set the request method to GET
                  connection.setRequestMethod("GET");
                  int responseCode = connection.getResponseCode();
                  System.out.println("Response Code: " + responseCode);

                  if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // Print the response
                System.out.println("Response Body:");
                System.out.println(response.toString());
                } else {
                  System.out.println("GET request failed.");
               }
            // Close the connection
               connection.disconnect();  
            } catch (IOException e) {
                  System.err.println(e+" connection is faul");
            }
      }
//________________________________________________________________________________
/**
 * 
 * @param url
 */
    //This method is if you need to insert a repeated object directly, you write your own Json in it
      public static void addRepeatingObject(URL url){
            try {
                  // Create a URL object with the target URL
                  url= new URL(url.toString());
                  // Open a connection to the URL
                  connection= (HttpURLConnection) url.openConnection();
                  // Set the request method to POST
                  connection.setRequestMethod("POST");
                  connection.setRequestProperty("Content-Type", "application/json");
                  connection.setDoOutput(true);                
                   String jsonPayload = "{\"columnName\": \"value\",\"columnName\": \"value\",\"columnName\":\"value\"}"; //  rewrite as needed
                  try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                              out.writeBytes(jsonPayload);
                  }
                  int responseCode = connection.getResponseCode();
                  System.out.println("Response Code: " + responseCode);  
                  if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read the response from the server
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuffer response = new StringBuffer();
        
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                            System.out.println("Response Body:");
                            System.out.println(response.toString());
                        }
                        reader.close();
                    } else {
                        System.out.println("POST request failed.");
                    }
                    // Close the connection
                    connection.disconnect();
            } catch (Exception e) {
                  System.err.println(e+" post metod is failed");
            }
      }
//_______________________________________________________________________________
/**
 * 
 * @param url
 * @param id
 */
      // Deletes the Object according to the specified id
      public static void deleteObject(URL url, int id){
            try {
                  // Create a URL object with the target URL
                  url = new URL(url.toString()+id); 
                  // Open a connection to the URL
                  connection = (HttpURLConnection) url.openConnection();
                   // Set the request method to DELETE
                  connection.setRequestMethod("DELETE");
                  int responseCode = connection.getResponseCode();
                  System.out.println("Response Code: " + responseCode);
                  if (responseCode == HttpURLConnection.HTTP_OK) {
                      System.out.println("DELETE request was successful.");
                  } else {
                      System.out.println("DELETE request failed.");
                  }
                  // Close the connection
                  connection.disconnect();
              } catch (Exception e) {
                  e.printStackTrace();
              }
      }
//____________________________________________________________________________________
/**
 * 
 * @param url
 * @param obj
 */
      // Create new Object 
      public static void addObject(URL url, Object obj) {
      try {
            // Create a URL object with the target URL
            url = new URL(url.toString());
            // Open a connection to the URL
            connection = (HttpURLConnection) url.openConnection();
             // Set the request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(obj);
            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                  out.writeBytes(jsonPayload);
            }
            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                  // Read the response from the server
                  BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                  String line;
                  StringBuffer response = new StringBuffer();
                  while ((line = reader.readLine()) != null) {
                  response.append(line);
                  }
                  System.out.println("Response Body:");
                  System.out.println(response.toString());
                  reader.close();
            } else {
                  System.out.println("POST request failed.");
            }
            // Close the connection
            connection.disconnect();
            } catch (Exception e) {
                  System.err.println(e + " post method is failed");
            }
      }
      /**
       * 
       * @param url
       * @param id
       * @param obj
       */
//________________________________________________________
     // Modifies the Object according to the specified id
     // Required in REST API when @RequestMapping(value = "/{id}", method = {RequestMethod.PATCH, RequestMethod.PUT})
      public static void updateObject(URL url,int id, Object obj) {
      try {
            url = new URL(url.toString()+id);
            connection = (HttpURLConnection) url.openConnection();
            // Set the request method to PUT
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(obj);

            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                  out.writeBytes(jsonPayload);
            }
            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                  // Read the response from the server
                  BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                  String line;
                  StringBuffer response = new StringBuffer();

                  while ((line = reader.readLine()) != null) {
                  response.append(line);
                  }
                  System.out.println("Response Body:");
                  System.out.println(response.toString());

                  reader.close();
            } else {
                  System.out.println("PUT request failed.");
            }
            // Close the connection
            connection.disconnect();
            } catch (Exception e) {
                  System.err.println(e + " PUT method is failed");
            }
      }
          
 }
