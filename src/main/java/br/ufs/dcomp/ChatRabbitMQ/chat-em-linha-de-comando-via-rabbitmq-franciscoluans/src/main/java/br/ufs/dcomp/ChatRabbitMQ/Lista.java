package br.ufs.dcomp.ChatRabbitMQ;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import java.net.URL;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import java.io.*;
import java.util.*;


public class Lista {



    public static String pegarUrl(String user, String test) {
        String json = "";
        String path = "";
        if(test.equals("grupo")){
             path = ("/api/queues/%2F/" + user + "/bindings"); 
        }
        if(test.equals("user")){
             path =  ("/api/exchanges/%2F/" + user+"/bindings/source");
        }
        if( path.equals("") ) return "";
        try {
            // JAVA 8 como prÃ©-requisito (ver README.md)
            //login
            //String username = "franciscols";
            //String password = "01020304";
            String username = "simonerss";
            String password = "123456";
               //String username = "ogcdkvmo";
             // String password = "wMjPUvqk71N7ZwaiVMF-RJew5U06aV1m";  
               
            String usernameAndPassword = username + ":" + password;
            String authorizationHeaderName = "Authorization";
            String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString( usernameAndPassword.getBytes() );
     
            // Perform a request 
            //aws
            //factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            //String restResource = "http://rabbit802-304f7797e4d394cd.elb.us-east-1.amazonaws.com";
                String restResource = "http://rabbitmq80-b5ebb0c0247a915d.elb.us-east-1.amazonaws.com";
           
          
            
            Client client = ClientBuilder.newClient();
            Response resposta = client.target( restResource )
            	//.path("/api/exchanges/iagffzqu/ufs/bindings/source") // lista todos os binds que tem "ufs" como source	
                .path(path)
            	.request(MediaType.APPLICATION_JSON)
                .header( authorizationHeaderName, authorizationHeaderValue ) // The basic authentication header goes here
                .get();     // Perform a post with the form values
           
            if (resposta.getStatus() == 200) {
            	json = resposta.readEntity(String.class);
            } else{	json = resposta.readEntity(String.class);}   
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    return json;
		}
    }
    
    
  
    //pegar  grupo do usuario
    private static ArrayList<String> getGrupo(String str) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArr = (JsonArray)jsonParser.parse(str);
        Gson googleJson = new Gson();
        ArrayList<LinkedTreeMap> jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
        ArrayList<String> groups = new ArrayList<>();
        jsonObjList.forEach(json -> groups.add((String)json.get("source")));
        return groups;
    }
    
    //pegar usuarios do grupo
    private static ArrayList<String> getUsuarios(String str) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArr = (JsonArray)jsonParser.parse(str);
        Gson googleJson = new Gson();
        ArrayList<LinkedTreeMap> jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
        ArrayList<String> users = new ArrayList<>();
        jsonObjList.forEach(json -> users.add((String)json.get("destination")));
        return users;
    }



    public static void listaDoGrupo(String user) {
        
        try {
            String str = pegarUrl(user, "grupo");
            ArrayList<String> groups = getGrupo(str);
            for(int i = 0; i < groups.size(); i++) {
                if(!groups.get(i).isEmpty() && !groups.get(i).startsWith("?") ) {
                    System.out.print(groups.get(i));
                    if(i < (groups.size() - 1)) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static void listaDosUsuarios(String user) {
        try {
            String str = pegarUrl(user, "user");
            ArrayList<String> groups = getUsuarios(str);
            for(int i = 0; i < groups.size(); i++) {
                if(!groups.get(i).isEmpty() ) {
                    System.out.print(groups.get(i));
                    if(i < (groups.size() - 1)) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println();
        } catch (Exception e) {
         e.printStackTrace();
        }
    }
    /*public static void main( String[] args )
    {
        try {
            //Lista.listaDosUsuarios("amizade");
           System.out.println("teste");
           Lista.listaDoGrupo("Francisco");
           
        } catch(Exception e) {
             e.printStackTrace();
        }
    }*/
}