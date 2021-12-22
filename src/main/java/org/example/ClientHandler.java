package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Info.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientHandler {
    private final int SERVER_PORT = 8888;

    private String urlServer = "http://";

    public void setUpServer(String[] args){
        argumentsHandler(args);
        ObjectMapper mapper = new ObjectMapper();
        try {
            switch(args[4]){
                case "get-buildings":
                    System.out.print(getBuildings());
                    break;
                case "get-civs":
                    System.out.println(getCivs());
                    break;
                case "get-unit":
                    System.out.println(getUnit(args[5]));
                    break;
                case "get-units":
                    System.out.println(getUnits());
                    break;
                case "get-buildings-by-age":
                    System.out.println(getBuildingsByAges(args[5]));
                    break;
                case "get-units-by-age":
                    System.out.println(getUnitsByAges(args[5]));
                    break;
                case "delete-unit":
                    deleteUnit(args[5]);
                    break;
                case "new-unit":
                    Unit unit = newUnit(args[5]);
                    newUnit(unit.getName(), mapper.writeValueAsString(unit));
                    break;
                case "update-unit":
                    unit = newUnit(args[5]);
                    updateUnit(unit.getName(), mapper.writeValueAsString(unit));
                    break;
            }
        } catch (Exception e) {}
    }

    private Unit newUnit(String info){
        String[] u = info.split(",");
        return new Unit(u[0], u[1].split(" "), u[2], u[3].split(" "), u[4], Integer.parseInt(u[5]), Integer.parseInt(u[6]), u[7].split(" "));
    }
    private void newUnit(String name, String body) throws Exception{getInfo(urlServer, "POST", "/units/:unit="+body, body);}
    private void updateUnit(String name, String body) throws Exception{getInfo(urlServer, "POST", "/units/:unit="+body, body);}

    private void deleteUnit(String name) throws Exception{
        getInfo(urlServer, "DELETE", "/units/unit_name=" + name);
    }

    private BuildingsSection getBuildings() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String info = getInfo(urlServer, "GET", "/buildings");
        return mapper.readValue(info, BuildingsSection.class);
    }

    private CivsSection getCivs() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String info = getInfo(urlServer, "GET", "/civs");
        return mapper.readValue(info, CivsSection.class);
    }

    private UnitsSection getUnits() throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        String info = getInfo(urlServer, "GET", "/units");
        return mapper.readValue(info, UnitsSection.class);
    }

    private Unit getUnit(String name)throws Exception{
        UnitsSection all = getUnits();
        for (Unit unit: all.getResults()) {
            if(unit.getName().equals(name))
                return unit;
        }
        return null;
    }

    private AgesSection getAgesSection(String data) throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        AgesSection ages = mapper.readValue(data, AgesSection.class);
        return ages;
    }

    private String getBuildingsByAges(String target) throws  Exception{
        String data =  getInfo(urlServer, "GET", "/ages/:age/buildings");
        return getAgesSection(data).toString(target);
    }

    private String getUnitsByAges(String target) throws  Exception{
        String data = getInfo(urlServer, "GET", "/ages/:age/units");
        return getAgesSection(data).toString(target);
    }

    private String getInfo(String urlLink, String method, String extention, String body) throws Exception{
        String content = "";
        URL url = new URL(urlLink + extention);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty(body, "application/json; charset=UTF-8");

        try(BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
            String line;
            while((line = r.readLine()) != null){
                content += line;
            }
        }
        conn.disconnect();
        return content;
    }

    private String getInfo(String urlLink, String method, String extention) throws Exception{
        String content = "";
        URL url = new URL(urlLink + extention);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);

        try(BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
            String line;
            while((line = r.readLine()) != null){
                content += line;
            }
        }
        conn.disconnect();
        return content;
    }

    private void argumentsHandler(String[] arguments){
        urlServer += arguments[3];
    }
}
