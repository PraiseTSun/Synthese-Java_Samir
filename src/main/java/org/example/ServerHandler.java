package org.example;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.io.FilenameUtils;
import org.example.Info.*;

public class ServerHandler {
    private final String TEST_UNITS = "{\"name\":\"Test\",\"allAges\":[\"1\"],\"unitBatiment\":\"Stable\",\"unitCost\":[\"75G\",\"60F\"],\"buildingTime\":\"0:30\",\"visibility\":4,\"civilisations\":[\"Chinese\",\"Franks\",\"Japanese\",\"Mongols\",\"Persians\"],\"livingPoint\":120}";
    private final String EXT = "tab";

    private File unitPath = null;
    private File buildingPath = null;

    private int serverPort;
    private Path dataFolder;
    private int nbThr = 1;

    private Map<String, Unit> unitsMap = new HashMap<>();
    private Map<String, Civilisation> civilisationMap = new HashMap<>();
    private Map<String, Building> buildingsMap = new HashMap<>();

    public void setUpServer(String[] arguments){
        argumentsHandler(arguments);
        readFiles();
        try {initServer();} catch (Exception e) {}
    }

    private void initServer() throws Exception {
        //ThreadPool tp = new ThreadPool(nbThr);
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 4);

        server.setExecutor(new Executor() {
            @Override
            public void execute(Runnable command) {
                (new Thread(command)).start();;
                //tp.addTask(comand);
            }
        });

        server.createContext("/test", exchange -> { exchangeTest(exchange);});
        server.createContext("/ages", exchange -> { exchangeAges(exchange);});
        server.createContext("/buildings", exchange -> { exchangeBuildings(exchange);});
        server.createContext("/civs", exchange -> { exchangeCivilisations(exchange);});
        server.createContext("/units", exchange -> { exchangeUnits(exchange);});

        server.start();
    }

    private void exchangeUnits(HttpExchange exchange){
        String[] elements = exchange.getRequestURI().getPath().substring(1).split("/");
        String method = exchange.getRequestMethod();
        try{
            if(method.equals("GET"))
                handleGetUnits(elements, exchange);
            else if(method.equals("POST"))
                handlePostUnits(elements, exchange);
            else if(method.equals("PUT"))
                handlePutUnits(elements, exchange);
            else if(method.equals("DELETE"))
                handleDeleteUnits(elements, exchange);
            else
                exchange.sendResponseHeaders(404,-1);
        } catch (Exception e) {}
        finally{ exchange.close(); }
    }

    private void handlePostUnits(String[] elements, HttpExchange exchange) throws Exception{
        // curl -i -X POST localhost:12345/units -H 'Content-Type: application/json' -d '{"test" : "test"}'
        if(elements.length == 1){
            Unit unit = readArgumentUnit(exchange);

            if(!unitsMap.containsKey(unit.getName())){
                unitsMap.put(unit.getName(), unit);
                writeUnit();
                exchange.sendResponseHeaders(201,0);
            }else exchange.sendResponseHeaders(203,0);
        }else exchange.sendResponseHeaders(404, 0);
    }

    private void handlePutUnits(String[] elements, HttpExchange exchange) throws Exception{
        // curl -i -X POST localhost:12345/units -H 'Content-Type: application/json' -d '{"test" : "test"}'
        if(elements.length == 2){
            String[] target = elements[1].split("=");
            if(target[0].equals(":unit_name") && unitsMap.containsKey(target[1])){
                unitsMap.remove(target[1]);
                Unit unit = readArgumentUnit(exchange);
                unitsMap.put(unit.getName(), unit);
                writeUnit();
                exchange.sendResponseHeaders(201, -1);
            }else exchange.sendResponseHeaders(404, -1);
        }else exchange.sendResponseHeaders(404, -1);

    }

    private void handleDeleteUnits(String[] elements, HttpExchange exchange) throws Exception{
        // curl -i -X POST localhost:12345/units -H 'Content-Type: application/json' -d '{"test" : "test"}'
        if(elements.length == 2){
            String[] target = elements[1].split("=");
            if(target[0].equals("unit_name") && unitsMap.containsKey(target[1])){
                unitsMap.remove(target[1]);
                writeUnit();
                exchange.sendResponseHeaders(201, -1);
            }else exchange.sendResponseHeaders(404, -1);
        }else exchange.sendResponseHeaders(404, -1);
    }

    private void handleGetUnits(String[] elements, HttpExchange exchange) throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        if(elements.length == 1){
            String info = mapper.writeValueAsString(new UnitsSection("Units", unitsMap.values().toArray(new Unit[0])));

            exchange.sendResponseHeaders(200, 0);
            writeBuffer(info,exchange);
        }
        else if(elements.length ==2){
            String[] target = elements[1].split("=");

            if(target.length == 2 && target[0].equals(":unit_name") && unitsMap.containsKey(target[1])){
                exchange.sendResponseHeaders(200, 0);
                writeBuffer(mapper.writeValueAsString(unitsMap.get(target[1])),exchange);
            }else exchange.sendResponseHeaders(404, -1);
        }else exchange.sendResponseHeaders(404, -1);
    }

    private Unit readArgumentUnit (HttpExchange exchange) throws  Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String unitJson = reader.readLine().trim();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(unitJson, Unit.class);
    }

    private void exchangeCivilisations(HttpExchange exchange){
        String[] elements = exchange.getRequestURI().getPath().substring(1).split("/");
        String method = exchange.getRequestMethod();
        try{
            if(method.equals("GET"))
                handleGetCivilisations(elements, exchange);
            else
                exchange.sendResponseHeaders(404,-1);
        } catch (Exception e) {}
        finally{ exchange.close(); }
    }

    private void handleGetCivilisations(String[] elements, HttpExchange exchange) throws  Exception{
        if(elements.length == 1){

            ObjectMapper mapper = new ObjectMapper();
            String info = mapper.writeValueAsString(new CivsSection("civilisation", civilisationMap.values().toArray(new Civilisation[0])));

            exchange.sendResponseHeaders(200, 0);
            writeBuffer(info,exchange);
        }else exchange.sendResponseHeaders(404, -1);
    }

    private void exchangeBuildings(HttpExchange exchange){
        String[] elements = exchange.getRequestURI().getPath().substring(1).split("/");
        String method = exchange.getRequestMethod();
        try{
            if(method.equals("GET"))
                handleGetBuildings(elements, exchange);
            else
                exchange.sendResponseHeaders(404,-1);
        } catch (Exception e) {}
        finally{ exchange.close(); }
    }

    private void handleGetBuildings(String[] elements, HttpExchange exchange) throws  Exception{
        if(elements.length == 1){
            ObjectMapper mapper = new ObjectMapper();
            String info = mapper.writeValueAsString(new BuildingsSection("Buildings", buildingsMap.values().toArray(new Building[0])));

            exchange.sendResponseHeaders(200, 0);
            writeBuffer(info,exchange);
        }else exchange.sendResponseHeaders(404, -1);
    }

    private String removeLastCharacter(String str){
        return str.substring(0, str.length() - 1);
    }

    private void exchangeAges(HttpExchange exchange){
        String[] elements = exchange.getRequestURI().getPath().substring(1).split("/");
        String method = exchange.getRequestMethod();
        try{
            if(method.equals("GET"))
                handleGetAges(elements, exchange);
            else
                exchange.sendResponseHeaders(404,-1);
        } catch (Exception e) {}
        finally{ exchange.close(); }
    }

    private void handleGetAges(String[] elements, HttpExchange exchange) throws Exception{
        String info = "";
        if(elements.length == 1){
            exchange.sendResponseHeaders(202,0);
            info += "{ \"ages\": [{\"units\":[" + getAgesUnits() + "]},{\"buildings\":[" + getAgesBuildings() + "]}]}";
            writeBuffer(info,exchange);
        }
        else if(elements.length >= 3){
            if(elements[1].equals(":age")){
                if(elements[2].equals("units")){
                    if(elements.length >= 4){
                        String[] target = elements[3].split("=");
                        if(target.length == 2 && target[0].equals("civ")){
                            exchange.sendResponseHeaders(202, 0);
                            writeBuffer(info, exchange);
                        } else exchange.sendResponseHeaders(404, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(202, 0);
                        info += "{\"name\":\"units\",\"results\":[" + getAgesUnits() + "]}";
                        writeBuffer(info, exchange);
                    }
                }
                else if(elements[2].equals("buildings")){
                    if(elements.length >= 4){
                        String[] target = elements[3].split("=");
                        if(target.length == 2 && target[0].equals("civ")){
                            exchange.sendResponseHeaders(202, 0);
                            writeBuffer(info, exchange);
                        } else exchange.sendResponseHeaders(404, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(202, 0);
                        info += "{\"name\":\"buildings\",\"results\":[" + getAgesBuildings() + "]}";
                        writeBuffer(info, exchange);
                    }
                } else exchange.sendResponseHeaders(404, 0);
            } else exchange.sendResponseHeaders(404, 0);;
        } else exchange.sendResponseHeaders(404, 0);
    }

    private String getAgesUnits() throws Exception{
        String info = "";
        for(Map.Entry<String, Unit> unit : unitsMap.entrySet()){
            info += getAgesUnit(unit.getValue()) + ",";
        }
        info = removeLastCharacter(info);
        return info;
    }

    private String getAgesUnit(Unit unit) throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        String info = mapper.writeValueAsString(new Ages(unit.getName(), unit.getAllAges()));
        return info;
    }

    private String getAgesBuildings() throws Exception{
        String info = "";
        for(Map.Entry<String, Building> build : buildingsMap.entrySet()){
            info += getAgesBuilding(build.getValue()) + ",";
        }
        info = removeLastCharacter(info);
        return info;
    }

    private String getAgesBuilding(Building build) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String info = mapper.writeValueAsString(new Ages(build.getName(), build.getAges()));
        return info;
    }

    private void exchangeTest(HttpExchange exchange){
        try {
            exchange.sendResponseHeaders(200, 0);
            writeBuffer("test\ntest", exchange);
        } catch (IOException e) {}
        finally {
            exchange.close();
        }
    }

    private void writeBuffer(String content, HttpExchange exchange){
        try{
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
            w.write(content);
            w.flush();
        } catch (IOException e) {}
    }

    private void argumentsHandler(String[] arguments){
        if(arguments.length < 7)
            throw new RuntimeException("There are some missing arguments");

        serverPort = Integer.parseInt(arguments[2]);

        if(!Files.isDirectory(Paths.get(arguments[4])))
            throw new RuntimeException(arguments[4] + " is not the data folder path.");
        dataFolder = Paths.get(arguments[4]);

        nbThr = Integer.parseInt(arguments[6]);
    }

    private void readFiles(){
        File folder = new File(dataFolder.toString());
        File[] listOfFiles = folder.listFiles();

        for ( File file : listOfFiles) {
            if(acceptedFile(file))
                collectData(file);
        }
    }

    private void collectData(File file){
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath()))){
            String line;
            String fileName = file.getName();
            String[] lineElements;
            while((line = reader.readLine()) != null){
                lineElements = line.split("\t");
                switch (fileName){
                    case "civs.tab":
                        civilisationMap.put(lineElements[0], new Civilisation(lineElements[0], lineElements[1], lineElements[2], lineElements[3], lineElements[4]));
                        break;
                    case "buildings.tab":
                        buildingsMap.put(lineElements[0], new Building(lineElements[0], lineElements[1].split(" "), lineElements[2], lineElements[3].split(" "), lineElements[4], Integer.parseInt(lineElements[5]), Integer.parseInt(lineElements[6]), lineElements[7].split(" ")));
                        break;
                    case "units.tab":
                        unitsMap.put(lineElements[0], new Unit(lineElements[0], lineElements[1].split(" "), lineElements[2], lineElements[3].split(" "), lineElements[4], Integer.parseInt(lineElements[5]), Integer.parseInt(lineElements[6]), lineElements[7].split(" ")));
                        break;
                }
            }
        } catch (IOException e) {}
    }

    private void writeUnit(){
        try(OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(dataFolder + "units.tab"))) ){
            for(Map.Entry<String, Unit> unit : unitsMap.entrySet()){
                os.write(unit.getValue().toData().getBytes(StandardCharsets.UTF_8));
            }
            os.flush();
        } catch (IOException e) {}
    }

    private boolean acceptedFile(File file){
        return file.isFile() && FilenameUtils.getExtension(file.getName()).equals(EXT);
    }

}
